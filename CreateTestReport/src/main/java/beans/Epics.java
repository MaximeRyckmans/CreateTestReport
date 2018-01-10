package beans;

import com.univocity.parsers.annotations.Parsed;

public class Epics extends Issue{

	@Parsed(field = "Custom field (Testing Status)")
	private String customFieldTestingStatus;

	@Parsed(field = "Custom field (Epic Link)")
	private String customFieldEpicLink;

	@Parsed(field = "Creator")
	private String creator;

	@Parsed(field = "ENV Type (BED only)")
	private String envType;

	@Parsed(field = "Urgency of Solution")
	private String urgencyOfSolution;


	public String getCustomFieldTestingStatus() {
		return customFieldTestingStatus;
	}

	public void setCustomFieldTestingStatus(String customFieldTestingStatus) {
		this.customFieldTestingStatus = customFieldTestingStatus;
	}

	public String getCustomFieldEpicLink() {
		return customFieldEpicLink;
	}

	public void setCustomFieldEpicLink(String customFieldEpicLink) {
		this.customFieldEpicLink = customFieldEpicLink;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getEnvType() {
		return envType;
	}

	public void setEnvType(String envType) {
		this.envType = envType;
	}

	public String getUrgencyOfSolution() {
		return urgencyOfSolution;
	}

	public void setUrgencyOfSolution(String urgencyOfSolution) {
		this.urgencyOfSolution = urgencyOfSolution;
	}

	@Override
	public String toString() {
		return "Epics [issueType=" + issueType + ", issueKey=" + issueKey + ", issueId=" + issueID + ", summary="
				+ summary + ", reporter=" + reporter + ", priority=" + priority + ", status=" + status + ", created="
				+ created + ", customFieldTestingStatus=" + customFieldTestingStatus + ", customFieldEpicLink="
				+ customFieldEpicLink + ", assignee=" + assignee + ", creator=" + creator + ", envType=" + envType
				+ ", urgencyOfSolution=" + urgencyOfSolution + "]";
	}
}
