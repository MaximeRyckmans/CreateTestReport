package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

public class CSVParser {

	public List<String[]> readAllRows(String path) {
		// The settings object provides many configuration options
		CsvParserSettings parserSettings = new CsvParserSettings();

		// You can configure the parser to automatically detect what line
		// separator sequence is in the input
		parserSettings.setLineSeparatorDetectionEnabled(true);
		parserSettings.setMaxCharsPerColumn(-1);
		CsvFormat format = new CsvFormat();

		format.setDelimiter(';');
		parserSettings.setFormat(format);
		// A RowListProcessor stores each parsed row in a List.
		RowListProcessor rowProcessor = new RowListProcessor();
//		parserSettings.setNullValue("EMPTY");
		parserSettings.setSkipEmptyLines(true);
		parserSettings.setNumberOfRecordsToRead(-1);
		// You can configure the parser to use a RowProcessor to process the
		// values of each parsed row.
		// You will find more RowProcessors in the
		// 'com.univocity.parsers.common.processor' package, but you can also
		// create your own.
		parserSettings.setProcessor(rowProcessor);

		// Let's consider the first parsed row as the headers of each column in
		// the file.
		parserSettings.setHeaderExtractionEnabled(false);

		// creates a parser instance with the given settings
		CsvParser parser = new CsvParser(parserSettings);

		parser.parse(getReader(path));

		// get the parsed records from the RowListProcessor here.
		// Note that different implementations of RowProcessor will provide
		// different sets of functionalities.
		// String[] headers = rowProcessor.getHeaders();
		List<String[]> rows = rowProcessor.getRows();
		return rows;
	}

	public void write(List<String[]> allRows, Map<String, String> ids, String fileToName, String copyOrChange,
			int rowToCheck) {
		// All you need is to create an instance of CsvWriter with the default
		// CsvWriterSettings.
		// By default, only values that contain a field separator are enclosed
		// within quotes.
		// If quotes are part of the value, they are escaped automatically as
		// well.
		// Empty rows are discarded automatically.
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(fileToName);
			CsvWriterSettings settings = new CsvWriterSettings();
			CsvFormat format = new CsvFormat();
			format.setDelimiter(';');
			settings.setFormat(format);
			CsvWriter writer = new CsvWriter(fileWriter, settings);

			// Write the record headers of this file
			// writer.writeHeaders("Year", "Make", "Model", "Description",
			// "Price");

			// Here we just tell the writer to write everything and close the
			// given output Writer instance.
			List<Object[]> rows = new ArrayList<>();
			switch (copyOrChange) {
			case "COPY":
				for (String[] row : allRows) {
					if (ids.containsKey(row[rowToCheck])) {
						rows.add(row);
					}
				}
				break;

			case "CHANGE":
				for (String[] row : allRows) {

					if (ids.containsKey(row[rowToCheck])) {
						row[rowToCheck] = ids.get(row[rowToCheck]);
					}
					rows.add(row);
				}
				break;

			case "COPYCHANGE":
				for (String[] row : allRows) {

					if (ids.containsKey(row[rowToCheck])) {
						row[rowToCheck] = ids.get(row[rowToCheck]);
						rows.add(row);
					}
				}
				break;
			case "GET":
				HashSet<String> valuesToGet = new HashSet<String>();
				for (String[] row : allRows) {

					String value = row[rowToCheck];
					valuesToGet.add(value);
					
				}
				
				for (String string : valuesToGet) {
					rows.add(new String[]{string});
				}
				break;
			case "INSERT":
				for (String[] row : allRows) {
					row[rowToCheck]= ids.get("value");
					rows.add(row);
				}
				break;
			}

			writer.writeRowsAndClose(rows);
			System.out.println("---------------------------------------DONE---------------------------------------");
			System.out.println("Number of rows in new file: " + rows.size());
			System.out.println("---------------------------------------DONE---------------------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Reader getReader(String path) {
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(path);
			return new InputStreamReader(inputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

		// return new
		// InputStreamReader(CSVParser.class.getResourceAsStream(relativePath),
		// "UTF-8");

	}
}
