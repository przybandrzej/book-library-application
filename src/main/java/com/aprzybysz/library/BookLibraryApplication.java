package com.aprzybysz.library;
import com.aprzybysz.library.data.provider.DataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.regex.Pattern;

@SpringBootApplication
public class BookLibraryApplication {

	static Logger logger = LogManager.getLogger(BookLibraryApplication.class);

	public static void main(String[] args) {
		for(String arg : args) {
			if(Pattern.matches(DataProvider.JSON_FILE_REGEX, arg)) {
				DataProvider.getInstance().setFileToRead(arg);
				logger.info("Set new external JSON file: %s" + arg);
			}
		}
		SpringApplication.run(BookLibraryApplication.class, args);
	}
}
