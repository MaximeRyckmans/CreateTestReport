package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import beans.Bugs;
import beans.Epics;
import beans.Issue;
import beans.Stories;
import beans.Tasks;
import beans.TestResults;
import beans.Tests;
import enums.IssueType;
import logging.MyLogger;
import readers.BeanReader;
import writers.GenerateCSV;

public class Main {

	public static void main(String[] args) {
		long startTime = time();
		try {
			Properties properties = loadProperties(args[0]);
			String epicsPath = properties.getProperty("epics_csv");
			String storiesPath = properties.getProperty("stories_csv");
			String bugsPath = properties.getProperty("bugs_csv");
			String tasksPath = properties.getProperty("tasks_csv");
			String testsPath = properties.getProperty("tests_csv");
			String testResultsPath = properties.getProperty("testResults_csv");
			String pathToWriteTo = properties.getProperty("generated_csv");
			String pathToWriteTestsTo = properties.getProperty("generated_test_csv");
			char delimiterIssues = properties.getProperty("delimiter_issues").toCharArray()[0];
			char delimiterTestResults = properties.getProperty("delimiter_testResults").toCharArray()[0];
			char delimiterGenerated = properties.getProperty("delimiter_generatedFile", ";").toCharArray()[0];
			String nullValue = properties.getProperty("nullValue_generatedFile", "");
			String[] headers = properties
					.getProperty("headers_generatedFile", "User story,Epic,Link,Link type,Last run").split(",");
			String[] headersForTest = properties.getProperty("headers_test__generatedFile", "Test,Bug,Test result")
					.split(",");
			String pathForDebug = properties.getProperty("path_debug", "debug.txt");
			boolean generateIssueReport = Boolean.parseBoolean(properties.getProperty("generateIssueReport", "true"));
			boolean generateTestReport = Boolean.parseBoolean(properties.getProperty("generateTestReport", "true"));

			String linkDelimiter = properties.getProperty("link_delimiter", "\\|");
			String defectDelimiter = properties.getProperty("defect_delimiter", "\\|");

			MyLogger.location = pathForDebug;
			List<Issue> issues = readAllIssues(epicsPath, storiesPath, bugsPath, tasksPath, testsPath, delimiterIssues);
			List<TestResults> testResults = readAllTestResults(testResultsPath, delimiterTestResults);

			GenerateCSV write = new GenerateCSV(issues, testResults, linkDelimiter, defectDelimiter);
			if (generateIssueReport) {
				write.writeStoryInformation(pathToWriteTo, delimiterGenerated, nullValue, headers);
			}
			if (generateTestReport) {
				write.writeTestinformation(pathToWriteTestsTo, delimiterGenerated, nullValue, headersForTest);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			displayTotalProgramRuntime(startTime);
		}

	}

	private static Properties loadProperties(String propertiesFileLocation) {
		Properties prop = new Properties();
		try {
			prop.load(FileUtils.openInputStream(new File(propertiesFileLocation)));

		} catch (IOException e) {
			System.out.println("Error loading properties file:");
			e.printStackTrace();
		}
		return prop;
	}

	private static List<Issue> readAllIssues(String epicsPath, String storiesPath, String bugsPath, String tasksPath,
			String testsPath, char delimiter) {
		BeanReader reader = new BeanReader(delimiter);
		List<Issue> issues = new ArrayList<Issue>();
		issues.addAll(reader.read(Epics.class, epicsPath));
		issues.addAll(reader.read(Stories.class, storiesPath));
		issues.addAll(reader.read(Bugs.class, bugsPath));
		issues.addAll(reader.read(Tasks.class, tasksPath));
		List<Tests> tests = reader.read(Tests.class, testsPath).stream()
				.filter(e -> IssueType.Test.equals(e.getIssueType())).collect(Collectors.toList());

		issues.addAll(tests);
		return issues;
	}

	private static List<TestResults> readAllTestResults(String testResultsPath, char delimiter) {
		BeanReader reader = new BeanReader(delimiter);
		return reader.read(TestResults.class, testResultsPath);

	}

	private static long time() {
		return System.nanoTime();
	}

	private static void displayTotalProgramRuntime(long startTime) {
		long estimatedTime = time() - startTime;
		double seconds = (double) estimatedTime / 1000000000.0;
		System.out.println("Program finished in: " + seconds + " seconds");
	}
}
