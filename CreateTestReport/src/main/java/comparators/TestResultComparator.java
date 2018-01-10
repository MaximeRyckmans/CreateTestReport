package comparators;

import java.time.LocalDateTime;
import java.util.Comparator;

import beans.TestResults;

public class TestResultComparator implements Comparator<TestResults> {

	@Override
	public int compare(TestResults o1, TestResults o2) {
		LocalDateTime dateFromO1 = (o1.getExecutedOn() != null)?o1.getExecutedOn():o1.getCreationDate();
		LocalDateTime dateFromO2 = (o2.getExecutedOn() != null)?o2.getExecutedOn():o2.getCreationDate();

		return	dateFromO1.compareTo(dateFromO2);	
	}

}
