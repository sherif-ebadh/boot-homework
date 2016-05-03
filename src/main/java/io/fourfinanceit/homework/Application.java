package io.fourfinanceit.homework;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	private static Logger logger = LoggerFactory.getLogger("");
	public static void main(String[] args) {
		logger.info("Start the Loan App main Method");
		SpringApplication.run(Application.class, args);
	}
}
