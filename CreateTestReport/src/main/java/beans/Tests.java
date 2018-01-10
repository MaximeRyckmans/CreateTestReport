package beans;

import com.univocity.parsers.annotations.Parsed;

public class Tests extends IssueWithLinks {
	@Parsed(field = "Urgency of Solution")
	private String urgencyOfSolution;
	
	@Parsed(field = "Outward issue link (Relates)")
	private String outwardIssueLink;
	
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

	public String getEnvType() {
		return envType;
	}

	public void setEnvType(String envType) {
		this.envType = envType;
	}

	@Override
	public String toString() {
		return "Tests [urgencyOfSolution=" + urgencyOfSolution + ", outwardIssueLink=" + outwardIssueLink + ", envType="
				+ envType + ", issueType=" + issueType + ", issueKey=" + issueKey + ", issueId=" + issueID
				+ ", summary=" + summary + ", reporter=" + reporter + ", priority=" + priority + ", status=" + status
				+ ", created=" + created + ", assignee=" + assignee + "]";
	}
	
	
}
