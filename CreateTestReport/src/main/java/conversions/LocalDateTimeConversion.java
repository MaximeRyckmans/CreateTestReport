package conversions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.univocity.parsers.conversions.Conversion;

public class LocalDateTimeConversion implements Conversion<String, LocalDateTime> {

	@Override
	public LocalDateTime execute(String date) {
		if(date != null){
			date=date.replaceAll("\\.\\d*\\+\\d*", "");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
			
			return localDateTime;
		}
		
		return null;
		
	}

	@Override
	public String revert(LocalDateTime date) {
		return date.toString();
	}

}
