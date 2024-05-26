package com.cau.issuemanagement.issuestation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class IssuestationApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssuestationApplication.class, args);
	}

}
