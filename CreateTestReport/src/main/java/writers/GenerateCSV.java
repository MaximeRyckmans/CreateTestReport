package writers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import beans.Bugs;
import beans.GeneratedCSV;
import beans.GeneratedTestCSV;
import beans.Issue;
import beans.IssueWithLinks;
import beans.Stories;
import beans.TestResults;
import beans.Tests;
import comparators.TestResultComparator;
import enums.IssueType;
import logging.MyLogger;

public class GenerateCSV {

	private List<Issue> issues;
	private List<TestResults> testResults;
	private Set<GeneratedCSV> allRecords;
	private Set<GeneratedTestCSV> allTestRecords;
	private String linkDelimiter, defectDelimiter;

	public GenerateCSV(List<Issue> issues, List<TestResults> testResults, String linkDelimiter,
			String defectDelimiter) {
		this.issues = issues;
		this.testResults = testResults;
		this.allRecords = new HashSet<GeneratedCSV>();
		this.allTestRecords = new HashSet<GeneratedTestCSV>();

		if (linkDelimiter != null) {
			this.linkDelimiter = linkDelimiter;
		} else {
			this.linkDelimiter = "\\|";
		}

		if (defectDelimiter != null) {
			this.defectDelimiter = defectDelimiter;
		} else {
			this.defectDelimiter = "\\|";
		}

	}

	private <T extends Issue> List<T> getIssueOfType(List<Issue> issues, Class<T> clazz) {
		List<T> issue = issues.stream().filter(e -> clazz.isInstance(e)).map(f -> clazz.cast(f))
				.collect(Collectors.toList());
		return issue;
	}

	private CsvWriterSettings setCSVWriterSettings(char delimiter, String nullValue,String typeOfReport, String... headers) {
		CsvWriterSettings settings = new CsvWriterSettings();
		CsvFormat format = new CsvFormat();
		if (delimiter != 0) {
			// format.setDelimiter(';');
			format.setDelimiter(delimiter);
		}
		settings.setFormat(format);
		switch (typeOfReport) {
		case "story":
			settings.setRowWriterProcessor(getBeanWriterProcessorStory());
			break;
		case "test":
			settings.setRowWriterProcessor(getBeanWriterProcessorTest());
			break;
		
		}
		
		settings.setHeaders(headers);
		settings.setNullValue(nullValue);
		return settings;
	}

	//refactor this later on, messy
	private BeanWriterProcessor<GeneratedCSV> getBeanWriterProcessorStory() {
		return new BeanWriterProcessor<GeneratedCSV>(GeneratedCSV.class);
	}
	private BeanWriterProcessor<GeneratedTestCSV> getBeanWriterProcessorTest() {
		return new BeanWriterProcessor<GeneratedTestCSV>(GeneratedTestCSV.class);
	}

	public void writeStoryInformation(String pathToWriteTo, char delimiter, String nullValue, String... headers)
			throws IOException {
		FileWriter fileWriter = null;
		try {
			fileWriter = createFileWriter(pathToWriteTo);
			// CsvWriterSettings settings = setCSVWriterSettings(';', "", "User
			// story", "Epic", "Link", "Link type",
			// "Last run");
			CsvWriterSettings settings = setCSVWriterSettings(delimiter, nullValue,"story", headers);
			CsvWriter writer = new CsvWriter(fileWriter, settings);
			writer.writeHeaders();

			List<Stories> stories = getIssueOfType(issues, Stories.class);
			for (Stories story : stories) {
				addMessageToDebug(Level.INFO, "Starting process for Story: " + story.getIssueKey());
				GeneratedCSV csv = new GeneratedCSV();
				writeIdUserStory(csv, story.getIssueKey());
				writeIdLinkedEpic(csv, story.getCustomFieldEpicLink());

				if (hasLinkedIssues(story)) {
					createLinks(story);
				} else {
					addMessageToDebug(Level.INFO, "Story: " + story.getIssueKey() + " has no linked issues");
					allRecords.add(csv);
				}
			}
			List<Bugs> bugs = getIssueOfType(issues, Bugs.class);
			for (Bugs bug : bugs) {
				GeneratedCSV csv = new GeneratedCSV();
				if (!hasLinkedIssues(bug) || !isAnyOfTheLinksAStory(bug)) {
					if (!bug.getEpicLink().equals("NULL")) {
						csv.setUserStory("NULL");
						csv.setEpic(bug.getEpicLink());
						csv.setLink(bug.getIssueKey());
						csv.setLinkType(bug.getIssueType());
						if (!allRecords.stream().anyMatch(
								e -> bug.getEpicLink().equals(e.getEpic()) && bug.getIssueKey().equals(e.getLink()))) {
							allRecords.add(csv);
						}

					}
				}
			}

			writer.processRecordsAndClose(allRecords);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileWriter != null) {
				fileWriter.close();
			}
		}
	}

	private boolean isAnyOfTheLinksAStory(IssueWithLinks issue) {
		List<Issue> issues = getLinkedIssues(issue);
		if (issues.size() > 0) {
			if (issues.stream().anyMatch(e -> IssueType.Story.equals(e.getIssueType()))) {
				return true;
			}
		}
		return false;
	}

	private void createLinks(Stories story) {
		List<Issue> linkedIssues = getLinkedIssues(story);
		GeneratedCSV csv = new GeneratedCSV();
		writeIdUserStory(csv, story.getIssueKey());
		writeIdLinkedEpic(csv, story.getCustomFieldEpicLink());

		// make this prettier
		if (linkedIssues.size() != getOutwardIssueLinks(story).size()) {

			List<String> issueKeysFoundLinks = linkedIssues.stream().map(e -> e.getIssueKey())
					.collect(Collectors.toList());

			int missinglinks = getOutwardIssueLinks(story).size() - linkedIssues.size();
			addMessageToDebug(Level.WARNING, "Missing: " + missinglinks + " links");
			for (String string : getOutwardIssueLinks(story)) {
				if (!issueKeysFoundLinks.contains(string)) {
					addMessageToDebug(Level.WARNING, "Could not find link: " + string);
					GeneratedCSV csv2 = new GeneratedCSV();
					writeIdUserStory(csv2, story.getIssueKey());
					writeIdLinkedEpic(csv2, story.getCustomFieldEpicLink());
					writeLinkedIssue(csv2, string);
					csv2.setLinkType(IssueType.NotFound);
					allRecords.add(csv2);
				}
			}

		}

		if (linkedIssues.size() > 0) {
			addMessageToDebug(Level.INFO,
					"Story: " + story.getIssueKey() + " has " + linkedIssues.size() + " linked issues");
			// System.out.println("Story: " + story.getIssueKey() + " has " +
			// linkedIssues.size() + " linked issues");
			for (Issue issue : linkedIssues) {
				GeneratedCSV csv2 = new GeneratedCSV();
				writeIdUserStory(csv2, story.getIssueKey());
				writeIdLinkedEpic(csv2, story.getCustomFieldEpicLink());
				addMessageToDebug(Level.INFO, "Issue: " + issue.getIssueKey());
				// System.out.println("Issue: " + issue.getIssueKey());
				if (!linkIdEqualTo(story.getIssueKey(), issue.getIssueKey())) {
					if (!linkIdEqualTo(story.getCustomFieldEpicLink(), issue.getIssueKey())) {
						writeLinkedIssue(csv2, issue.getIssueKey());
						writeLinkType(csv2, issue.getIssueKey());
						if (isLinkTest(issue)) {
							addMessageToDebug(Level.INFO, "Issue: " + issue.getIssueKey() + " is a test");
							// System.out.println("Issue: " +
							// issue.getIssueKey() + " is a test");
							createTestLinks(story, issue);
						} else {
							addMessageToDebug(Level.INFO, "Issue: " + issue.getIssueKey()
									+ " is not a test, continue with next link (if any)");
							// System.out.println("Issue: " +
							// issue.getIssueKey()
							// + " is not a test, continue with next link (if
							// any)");
							addMessageToDebug(Level.INFO, csv2.toString());
							// System.out.println(csv2.toString());
							allRecords.add(csv2);
						}
					}
				}
			}
		} else {
			addMessageToDebug(Level.WARNING, "Could not find linked Issues for story: " + story.getIssueKey());
		}
	}

	private void addMessageToDebug(Level level, String message) {
		MyLogger.log(level, message);
	}

	private void createTestLinks(Stories story, Issue issue) {
		Tests test = (Tests) issue;

		GeneratedCSV csv = new GeneratedCSV();
		writeIdUserStory(csv, story.getIssueKey());
		writeIdLinkedEpic(csv, story.getCustomFieldEpicLink());
		writeLinkedIssue(csv, issue.getIssueKey());
		writeLinkType(csv, issue.getIssueKey());

		if (hasLinkedIssues(test)) {

			List<Issue> linkedIssuesTest = getLinkedIssues(test);
			addMessageToDebug(Level.INFO,
					"Test: " + test.getIssueKey() + " has " + linkedIssuesTest.size() + " linked issues");
			for (Issue issueTest : linkedIssuesTest) {
				GeneratedCSV csv2 = new GeneratedCSV();
				writeIdUserStory(csv2, story.getIssueKey());
				writeIdLinkedEpic(csv2, story.getCustomFieldEpicLink());
				if (!linkIdEqualTo(story.getIssueKey(), issueTest.getIssueKey())) {
					if (!linkIdEqualTo(story.getCustomFieldEpicLink(), issueTest.getIssueKey())) {
						if (isLinkTest(issueTest)) {
							addMessageToDebug(Level.WARNING, "Test: " + issueTest.getIssueKey() + " is linked to Test: "
									+ test.getIssueKey() + "\n This will not be added to the report!");
						} else {
							addMessageToDebug(Level.INFO,
									"Issue: " + issueTest.getIssueKey() + " is linked to Test: " + test.getIssueKey());
							writeLinkedIssue(csv2, issueTest.getIssueKey());
							writeLinkType(csv2, issueTest.getIssueKey());
							allRecords.add(csv2);
						}
					}
				}
			}
		}
		// get the testResults of this test (if any)
		List<TestResults> testResults = getTestresultsOfTest(test);
		// first set the latest execution RESULT
		handleTestResultForIssue(testResults, csv);

		createTestResultLinks(testResults, csv);

	}

	private void createTestResultLinks(List<TestResults> testResults, GeneratedCSV csv) {
		// then check for each testresult if there are linked execution defect
		// if yes -> check if exists -> if no add line with EPIC USER Story
		// defect ID
		// then check for each testresult if there are linked step defects
		// if yes -> check if exists -> if no add line EPIC User story defect ID
		GeneratedCSV csv2 = new GeneratedCSV();
		csv2.setEpic(csv.getEpic());
		csv2.setUserStory(csv.getUserStory());

		for (TestResults testResult : testResults) {
			if (hasExecutionDefects(testResult)) {
				List<String> executionDefects = Arrays.asList(testResult.getExecutedDefects().split(defectDelimiter));
				addMessageToDebug(Level.INFO, "TestResult: " + testResult.toString() + "\nhas "
						+ executionDefects.size() + " execution defects");
				for (String executionDefect : executionDefects) {
					executionDefect = executionDefect.trim();
					if (!linkIdEqualTo(csv2.getUserStory(), executionDefect)) {
						if (!linkIdEqualTo(csv2.getEpic(), executionDefect)) {
							GeneratedCSV csv3 = new GeneratedCSV();
							csv3.setEpic(csv2.getEpic());
							csv3.setUserStory(csv2.getUserStory());
							csv3.setLink(executionDefect);
							csv3.setLinkType(IssueType.Bug);
							allRecords.add(csv3);
						}
					}
				}

			}
			if (hasStepDefects(testResult)) {
				List<String> stepDefects = Arrays.asList(testResult.getStepDefects().split(defectDelimiter));
				addMessageToDebug(Level.INFO,
						"TestResult: " + testResult.toString() + "\nhas " + stepDefects.size() + " step defects");
				for (String stepDefect : stepDefects) {
					stepDefect = stepDefect.trim();
					if (!linkIdEqualTo(csv2.getUserStory(), stepDefect)) {
						if (!linkIdEqualTo(csv2.getEpic(), stepDefect)) {
							GeneratedCSV csv3 = new GeneratedCSV();
							csv3.setEpic(csv2.getEpic());
							csv3.setUserStory(csv2.getUserStory());
							csv3.setLink(stepDefect);
							csv3.setLinkType(IssueType.Bug);
							allRecords.add(csv3);
						}
					}
				}
			}
		}

	}

	private boolean hasStepDefects(TestResults testResult) {
		if (testResult.getStepDefects() != null) {
			return true;
		}
		return false;
	}

	private List<TestResults> getTestresultsOfTest(Tests test) {
		List<TestResults> testResultsOfThisTest = new ArrayList<TestResults>();
		if (testResults.stream().anyMatch(e -> e.getIssueKey().equals(test.getIssueKey()))) {
			testResultsOfThisTest = testResults.stream().filter(e -> e.getIssueKey().equals(test.getIssueKey()))
					.collect(Collectors.toList());
		}
		return testResultsOfThisTest;
	}

	private void handleTestResultForIssue(List<TestResults> testResultsOfThisTest, GeneratedCSV csv) {
		csv.setLastRun(addLatestTestExecution(testResultsOfThisTest));
		addMessageToDebug(Level.INFO, "Write test data: " + csv.toString());
		allRecords.add(csv);
	}

	private String addLatestTestExecution(List<TestResults> testResultsOfThisTest) {
		if (testResultsOfThisTest.size() == 0) {
			return "";
		} else {
			TestResults test = Collections.max(testResultsOfThisTest, new TestResultComparator());
			return test.getExecutionStatus();
		}

	}

	private boolean isLinkTest(Issue issue) {
		if (IssueType.Test.equals(issue.getIssueType())) {
			return true;
		}
		return false;
	}

	private boolean isLinkBug(Issue issue) {
		if (IssueType.Bug.equals(issue.getIssueType())) {
			return true;
		}
		return false;
	}

	private void writeLinkType(GeneratedCSV csv, String linkId) {
		Optional<Issue> issue = issues.stream().filter(e -> linkId.equals(e.getIssueKey())).findFirst();
		if (issue.isPresent()) {
			csv.setLinkType(issue.get().getIssueType());
		} else {
			throw new NullPointerException("Can't find issue with id: " + linkId);
		}
	}

	private void writeLinkedIssue(GeneratedCSV csv, String linkId) {
		csv.setLink(linkId);

	}

	private boolean linkIdEqualTo(String valueToCompareTo, String linkId) {
		if (valueToCompareTo.equals(linkId)) {
			return true;
		}
		return false;
	}

	private List<Issue> getLinkedIssues(IssueWithLinks issue) {
		List<String> outwardIssueLinks = getOutwardIssueLinks(issue);
		// List<String> outwardIssueLinks =
		// Arrays.asList(issue.getOutwardIssueLink().split("\\|"));
		return issues.stream().filter(e -> outwardIssueLinks.contains(e.getIssueKey())).collect(Collectors.toList());
		// return Arrays.asList(issue.getOutwardIssueLink().split("\\|"));
	}

	private List<Issue> getLinkedTestResultIssues(TestResults issue) {
		List<String> testResultLinks = getTestResultLinks(issue);
		return issues.stream().filter(e -> testResultLinks.contains(e.getIssueKey())).collect(Collectors.toList());
		// return Arrays.asList(issue.getOutwardIssueLink().split("\\|"));
	}

	private List<String> getOutwardIssueLinks(IssueWithLinks issue) {
		if(issue.getOutwardIssueLink() != null){
			return Arrays.asList(issue.getOutwardIssueLink().split(linkDelimiter));
		}else{
			return new ArrayList<String>();
		}
		
	}

	private List<String> getTestResultLinks(TestResults issue) {
		List<String> testResultLinks = new ArrayList<String>();
		if (issue.getStepDefects() != null) {
			testResultLinks.addAll(Arrays.asList(issue.getStepDefects().split(linkDelimiter)));
		}
		if (issue.getExecutedDefects() != null) {
			testResultLinks.addAll(Arrays.asList(issue.getExecutedDefects().split(linkDelimiter)));
		}
		return testResultLinks;
	}

	private boolean hasLinkedIssues(IssueWithLinks issue) {
		if (issue.getOutwardIssueLink() != null) {
			return true;
		}
		return false;
	}

	private boolean hasExecutionDefects(TestResults testResult) {
		if (testResult.getExecutedDefects() != null) {
			return true;
		}
		return false;
	}

	private void writeIdLinkedEpic(GeneratedCSV csv, String customFieldEpicLink) {
		csv.setEpic(customFieldEpicLink);

	}

	private void writeIdUserStory(GeneratedCSV csv, String issueKey) {
		csv.setUserStory(issueKey);

	}

	private FileWriter createFileWriter(String pathToWriteTo) throws IOException {
		return new FileWriter(pathToWriteTo);
	}

	public void writeTestinformation(String pathToWriteTestsTo, char delimiterGenerated, String nullValue,
			String[] headersForTest) throws IOException {
		FileWriter fileWriter = null;
		try {
			fileWriter = createFileWriter(pathToWriteTestsTo);

			CsvWriterSettings settings = setCSVWriterSettings(delimiterGenerated, nullValue,"test", headersForTest);
			CsvWriter writer = new CsvWriter(fileWriter, settings);
			writer.writeHeaders();

			List<Tests> tests = getIssueOfType(issues, Tests.class);

			for (Tests test : tests) {
				// get the testResults of this test (if any)
				List<TestResults> testResults = getTestresultsOfTest(test);
				List<Issue> linkedIssues = getLinkedIssues(test);
				String latestTestExecution = addLatestTestExecution(testResults);
				for (Issue issue : linkedIssues) {

					if (isLinkBug(issue)) {
						GeneratedTestCSV csv = new GeneratedTestCSV();
						csv.setTest(test.getIssueKey());
						csv.setBug(issue.getIssueKey());
						csv.setTestResult(latestTestExecution);
						allTestRecords.add(csv);
					}
				}

				for (TestResults testResult : testResults) {
					List<Issue> linkedTestresultIssues = getLinkedTestResultIssues(testResult);
					for (Issue issue : linkedTestresultIssues) {
						if (isLinkBug(issue)) {
							GeneratedTestCSV csv = new GeneratedTestCSV();
							csv.setTest(test.getIssueKey());
							csv.setBug(issue.getIssueKey());
							csv.setTestResult(latestTestExecution);
							allTestRecords.add(csv);
						}
					}
				}

			}
			writer.processRecordsAndClose(allTestRecords);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception ex){
			ex.printStackTrace();
		} finally {
			if (fileWriter != null) {
				fileWriter.close();
			}
		}
	}
}
