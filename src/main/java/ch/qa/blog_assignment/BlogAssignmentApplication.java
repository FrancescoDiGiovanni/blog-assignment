package ch.qa.blog_assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BlogAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAssignmentApplication.class, args);
	}

}
