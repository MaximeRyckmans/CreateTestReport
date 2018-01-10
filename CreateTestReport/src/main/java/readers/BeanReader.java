package readers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import beans.Issue;

public class BeanReader {
	
	private char delimiter;
	public BeanReader(char delimiter) {
		if(delimiter != 0){
			this.delimiter = delimiter;
		}else{
			this.delimiter = ';';
		}
	}
	
	public <T extends Issue> List<T> read(Class<T> clazz , String path){
		CsvParserSettings parserSettings = new CsvParserSettings();
		parserSettings.setLineSeparatorDetectionEnabled(true);
		CsvFormat format = new CsvFormat();
		format.setDelimiter(delimiter);
		parserSettings.setFormat(format);
		BeanListProcessor<T> rowProcessor = new BeanListProcessor<T>(clazz);
		parserSettings.setProcessor(rowProcessor);
		CsvParser parser = new CsvParser(parserSettings);
		parser.parse(getReader(path));
		
		List<T> rows = rowProcessor.getBeans();
		
		return rows;
	}
	
	private Reader getReader(String path) {
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(path);
			return new InputStreamReader(inputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
		// return new
		// InputStreamReader(CSVParser.class.getResourceAsStream(relativePath),
		// "UTF-8");

	}

}
