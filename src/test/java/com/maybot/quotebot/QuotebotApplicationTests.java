package com.maybot.quotebot;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:test.properties")
class QuotebotApplicationTests {

	@Test
	void contextLoads() {
	}

}
