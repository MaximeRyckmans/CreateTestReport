package beans;

import com.univocity.parsers.annotations.Parsed;

public class Stories extends IssueWithLinks{
	
	@Parsed(field = "Urgency of Solution")
	private String urgencyOfSolution;

	@Parsed(field = "Outward issue link (Blocks)")
	private String outwardIssueLink;
	
	@Parsed(field = "Sprint")
	private String sprint;
	
	@Parsed(field = "Custom field (Epic Link)")
	private String customFieldEpicLink;
	
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

	public String getSprint() {
		return sprint;
	}

	public void setSprint(String sprint) {
		this.sprint = sprint;
	}

	public String getCustomFieldEpicLink() {
		return customFieldEpicLink;
	}

	public void setCustomFieldEpicLink(String customFieldEpicLink) {
		this.customFieldEpicLink = customFieldEpicLink;
	}

	public String getEnvType() {
		return envType;
	}

	public void setEnvType(String envType) {
		this.envType = envType;
	}

	@Override
	public String toString() {
		return "Stories [issueType=" + issueType + ", issueKey=" + issueKey + ", issueId=" + issueID + ", summary="
				+ summary + ", reporter=" + reporter + ", priority=" + priority + ", urgencyOfSolution="
				+ urgencyOfSolution + ", status=" + status + ", created=" + created + ", outwardIssueLink="
				+ outwardIssueLink + ", sprint=" + sprint + ", customFieldEpicLink=" + customFieldEpicLink
				+ ", assignee=" + assignee + ", envType=" + envType + "]";
	}
	
	
}
