package com.maybot.quotebot.controller;

import com.maybot.quotebot.QuotebotApplication;
import com.maybot.quotebot.model.AllQuoteRequestModel;
import com.maybot.quotebot.model.QuoteRequestModel;
import com.maybot.quotebot.model.data.ReplyDataModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = QuotebotApplication.class
)
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class QueueControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private QuoteControllerImpl quoteController;



    private void saveItems(List<QuoteRequestModel> models)  {
        quoteController.saveAll(new AllQuoteRequestModel(false, models));
    }


}
