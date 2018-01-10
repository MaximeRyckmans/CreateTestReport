package beans;

import java.time.LocalDateTime;

import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.Parsed;

import conversions.LocalDateTimeConversion;
import enums.IssueType;

public abstract class Issue {
	@Parsed(field = "Issue Type")
	protected IssueType issueType;

	@Parsed(field = "Issue key")
	protected String issueKey;

	@Parsed(field = "Issue id")
	protected String issueID;

	@Parsed(field = "Summary")
	protected String summary;

	@Parsed(field = "Reporter")
	protected String reporter;

	@Parsed(field = "Priority")
	protected String priority;
	
	@Parsed(field = "Status")
	protected String status;
	
	@Convert(conversionClass = LocalDateTimeConversion.class)
	@Parsed(field = "Created",defaultNullRead = "1000-01-01T12:00:00")
	protected LocalDateTime created;
	
	@Parsed(field = "Assignee")
	protected String assignee;

	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	public String getIssueKey() {
		return issueKey;
	}

	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}

	public String getIssueID() {
		return issueID;
	}

	public void setIssueID(String issueID) {
		this.issueID = issueID;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
}
