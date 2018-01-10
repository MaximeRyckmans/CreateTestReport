package beans;

import com.univocity.parsers.annotations.Parsed;

public class GeneratedTestCSV {

	@Parsed(field = "Test")
	private String test;

	@Parsed(field = "Bug")
	private String bug;

	@Parsed(field = "Test result")
	private String testResult;

	

	/**
	 * @return the test
	 */
	public String getTest() {
		return test;
	}

	/**
	 * @param test the test to set
	 */
	public void setTest(String test) {
		this.test = test;
	}

	/**
	 * @return the bug
	 */
	public String getBug() {
		return bug;
	}

	/**
	 * @param bug the bug to set
	 */
	public void setBug(String bug) {
		this.bug = bug;
	}

	/**
	 * @return the testResult
	 */
	public String getTestResult() {
		return testResult;
	}

	/**
	 * @param testResult the testResult to set
	 */
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GeneratedTestCSV [test=" + test + ", bug=" + bug + ", testResult=" + testResult + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((test == null) ? 0 : test.hashCode());
		result = prime * result + ((bug == null) ? 0 : bug.hashCode());
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
		if (!(obj instanceof GeneratedTestCSV)) {
			return false;
		}
		GeneratedTestCSV other = (GeneratedTestCSV) obj;
		if (test == null) {
			if (other.test != null) {
				return false;
			}
		} else if (!test.equals(other.test)) {
			return false;
		}
		if (bug == null) {
			if (other.bug != null) {
				return false;
			}
		} 
		return true;
	}

}
