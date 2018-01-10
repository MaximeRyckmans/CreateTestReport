package beans;

import java.time.LocalDateTime;

import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.Parsed;

import conversions.DateTimeConversion;

public class TestResults extends Issue {

	
	@Parsed(field = "PROJECT")
	private String project;
	
	@Parsed(field = "VERSION")
	private String version;
	
	@Parsed(field = "CYCLE")
	private String cycle;
	
	@Parsed(field = "ISSUE ID")
	private String issueID;
	
	@Parsed(field = "ISSUE KEY")
	private String issueKey;
	
	@Parsed(field = "SUMMARY")
	private String summary;
	
	@Parsed(field = "EXECUTION STATUS")
	private String executionStatus;
	
	@Parsed(field = "EXECUTED BY")
	private String executedBy;
	
	@Convert(conversionClass = DateTimeConversion.class)
	@Parsed(field = "EXECUTED ON")
	private LocalDateTime executedOn;
	
	@Parsed(field = "EXECUTED DEFECTS")
	private String executedDefects;
	
	@Parsed(field = "STEP DEFECTS")
	private String stepDefects;
	
	@Convert(conversionClass = DateTimeConversion.class)
	@Parsed(field = "CREATION DATE")
	private LocalDateTime creationDate;

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getIssueID() {
		return issueID;
	}

	public void setIssueID(String issueID) {
		this.issueID = issueID;
	}

	public String getIssueKey() {
		return issueKey;
	}

	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}

	public String getExecutedBy() {
		return executedBy;
	}

	public void setExecutedBy(String executedBy) {
		this.executedBy = executedBy;
	}
	
	public LocalDateTime getExecutedOn() {
		return executedOn;
	}

	public void setExecutedOn(LocalDateTime executedOn) {
		this.executedOn = executedOn;
	}

	public String getExecutedDefects() {
		return executedDefects;
	}

	public void setExecutedDefects(String executedDefects) {
		this.executedDefects = executedDefects;
	}

	public String getStepDefects() {
		return stepDefects;
	}

	public void setStepDefects(String stepDefects) {
		this.stepDefects = stepDefects;
	}

	/**
	 * @return the creationDate
	 */
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestResults [project=" + project + ", version=" + version + ", cycle=" + cycle + ", issueID=" + issueID
				+ ", issueKey=" + issueKey + ", summary=" + summary + ", executionStatus=" + executionStatus
				+ ", executedBy=" + executedBy + ", executedOn=" + executedOn + ", executedDefects=" + executedDefects
				+ ", stepDefects=" + stepDefects + ", creationDate=" + creationDate + "]";
	}
	
}
