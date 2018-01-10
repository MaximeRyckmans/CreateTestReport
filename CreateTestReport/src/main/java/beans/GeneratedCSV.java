package beans;

import com.univocity.parsers.annotations.Parsed;

import enums.IssueType;

public class GeneratedCSV {

	@Parsed(field = "User story")
	private String userStory;

	@Parsed(field = "Epic")
	private String epic;

	@Parsed(field = "Link")
	private String link;

	@Parsed(field = "Last run")
	private String lastRun;

	public String getLastRun() {
		return lastRun;
	}

	public void setLastRun(String lastRun) {
		this.lastRun = lastRun;
	}

	public String getUserStory() {
		return userStory;
	}

	public void setUserStory(String userStory) {
		this.userStory = userStory;
	}

	public String getEpic() {
		return epic;
	}

	public void setEpic(String epic) {
		this.epic = epic;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public IssueType getLinkType() {
		return linkType;
	}

	public void setLinkType(IssueType linkType) {
		this.linkType = linkType;
	}

	@Parsed(field = "Link type")
	private IssueType linkType;

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((epic == null) ? 0 : epic.hashCode());
//		result = prime * result + ((link == null) ? 0 : link.hashCode());
//		result = prime * result + ((userStory == null) ? 0 : userStory.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
////		if (this == obj)
////			return true;
////		if (obj == null)
////			return false;
//		if (!(obj instanceof GeneratedCSV))
//			return false;
//		
//		GeneratedCSV other = (GeneratedCSV) obj;
//		
//		if(this.epic.equals(other.epic) && this.userStory.equals(userStory)){
//			if(this.link != null){
//				if(this.link.equals(other.link)){
//					return true;
//				}
//			}else{
//				if(other.link != null){
//					return false;
//				}else{
//					return true;
//				}
//			}
//		}
//		return false;
////		if (epic == null) {
////			if (other.epic != null)
////				return false;
////		} else if (!epic.equals(other.epic))
////			return false;
////		if (link == null) {
////			if (other.link != null)
////				return false;
////		} else if (!link.equals(other.link))
////			return false;
////		if (linkType != other.linkType)
////			return false;
////		if (userStory == null) {
////			if (other.userStory != null)
////				return false;
////		} else if (!userStory.equals(other.userStory))
////			return false;
////		return true;
//	}

	@Override
	public String toString() {
		return "GeneratedCSV [userStory=" + userStory + ", epic=" + epic + ", link=" + link + ", lastRun=" + lastRun
				+ ", linkType=" + linkType + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((epic == null) ? 0 : epic.hashCode());
//		result = prime * result + ((link == null) ? 0 : link.hashCode());
//		result = prime * result + ((userStory == null) ? 0 : userStory.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GeneratedCSV)) {
			return false;
		}
		GeneratedCSV other = (GeneratedCSV) obj;
		if (epic == null) {
			if (other.epic != null) {
				return false;
			}
		} else if (!epic.equals(other.epic)) {
			return false;
		}
		if (link == null) {
			if (other.link != null) {
				return false;
			}
		} else if (!link.equals(other.link)) {
			return false;
		}
		if (userStory == null) {
			if (other.userStory != null) {
				return false;
			}
		} else if (!userStory.equals(other.userStory)) {
			return false;
		}
		return true;
	}

}
