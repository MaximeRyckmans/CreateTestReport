package beans;

import com.univocity.parsers.annotations.Parsed;

public class Bugs extends IssueWithLinks{
	
	@Parsed(field = "Urgency of Solution")
	private String urgencyOfSolution;
	
	@Parsed(field = "Outward issue link (Relates)")
	private String outwardIssueLink;
	
	@Parsed(field = "ENV Type (BED only)")
	private String envType;
	
	@Parsed(field = "updated")
	private String updated;
	
	@Parsed(field = "epic link")
	private String epicLink;

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

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	/**
	 * @return the epicLink
	 */
	public String getEpicLink() {
		return epicLink;
	}

	/**
	 * @param epicLink the epicLink to set
	 */
	public void setEpicLink(String epicLink) {
		this.epicLink = epicLink;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bugs [urgencyOfSolution=" + urgencyOfSolution + ", outwardIssueLink=" + outwardIssueLink + ", envType="
				+ envType + ", updated=" + updated + ", epicLink=" + epicLink + "]";
	}
}
