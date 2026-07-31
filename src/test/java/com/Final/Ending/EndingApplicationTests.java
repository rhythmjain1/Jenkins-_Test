package com.Final.Ending;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EndingApplicationTests {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	Environment environment;

	@Test
	void shouldLoadApplicationContext() {
		assertNotNull(applicationContext);
	}

	@Test
	void shouldRegisterEndingApplicationAsBean() {
		assertNotNull(applicationContext.getBean(EndingApplication.class));
	}

	@Test
	void shouldExposeApplicationNameProperty() {
		assertEquals("Ending", environment.getProperty("spring.application.name"));
	}

	@Test
	void shouldResolveServerPortPropertyWhenValueContainsLeadingWhitespace() {
		assertEquals("8082", environment.getProperty("server.port"));
	}

}
