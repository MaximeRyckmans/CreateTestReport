package beans;

public class IssueWithLinks extends Issue {
	
	public String getOutwardIssueLink() {
		return outwardIssueLink;
	}

	public void setOutwardIssueLink(String outwardIssueLink) {
		this.outwardIssueLink = outwardIssueLink;
	}

	protected String outwardIssueLink;
}
