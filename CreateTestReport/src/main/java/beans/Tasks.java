package beans;

import com.univocity.parsers.annotations.Parsed;

public class Tasks extends IssueWithLinks{
	@Parsed(field = "Priority")
	private String priority;

	@Parsed(field = "Urgency of Solution")
	private String urgencyOfSolution;

	@Parsed(field = "Outward issue link (Relates)")
	private String outwardIssueLink;

	@Parsed(field = "Custom field (Epic Link")
	private String customField;

	@Parsed(field = "ENV Type (BED only)")
	private String envType;


	public String getUrgencyOfSolution() {
		return urgencyOfSolution;
	}

	public void setUrgencyOfSolution(String urgencyOfSolution) {
		this.urgencyOfSolution = urgencyOfSolution;
	}


	public String getOutwardIssueLink() {
		return outwardIssueLink;
	}

	public void setOutwardIssueLink(String outwardIssueLink) {
		this.outwardIssueLink = outwardIssueLink;
	}

	public String getCustomField() {
		return customField;
	}

	public void setCustomField(String customField) {
		this.customField = customField;
	}

	public String getEnvType() {
		return envType;
	}

	public void setEnvType(String envType) {
		this.envType = envType;
	}

	@Override
	public String toString() {
		return "Tasks [issueType=" + issueType + ", issueKey=" + issueKey + ", issueId=" + issueID + ", summary="
				+ summary + ", reporter=" + reporter + ", priority=" + priority + ", status=" + status
				+ ", urgencyOfSolution=" + urgencyOfSolution + ", created=" + created + ", outwardIssueLink="
				+ outwardIssueLink + ", customField=" + customField + ", assignee=" + assignee + ", envType=" + envType
				+ "]";
	}
}
