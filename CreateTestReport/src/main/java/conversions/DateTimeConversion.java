package conversions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.univocity.parsers.conversions.Conversion;

public class DateTimeConversion implements Conversion<String, LocalDateTime> {

	@Override
	public LocalDateTime execute(String date) {
		if(date != null){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz uuuu", Locale.US);
			LocalDateTime DateTime = LocalDateTime.parse(date, formatter);
			return DateTime;
		}
		
		return null;
		
	}

	@Override
	public String revert(LocalDateTime date) {
		return date.toString();
	}

}
