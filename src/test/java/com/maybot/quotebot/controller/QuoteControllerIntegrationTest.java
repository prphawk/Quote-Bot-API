package com.maybot.quotebot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybot.quotebot.QuotebotApplication;
import com.maybot.quotebot.model.AllQuoteRequestModel;
import com.maybot.quotebot.model.QuoteRequestModel;
import com.maybot.quotebot.model.data.ReplyDataModel;
import com.maybot.quotebot.service.QueueServiceImpl;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.maybot.quotebot.constant.PathConstants.QUEUE;
import static com.maybot.quotebot.constant.PathConstants.QUEUE_FORCE_POP;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = QuotebotApplication.class
)
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class QuoteControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private QuoteControllerImpl quoteController;

    @Autowired
    private QueueServiceImpl queueService;

    private void saveItems(List<QuoteRequestModel> models)  {
        quoteController.saveAll(new AllQuoteRequestModel(false, models));
    }

    private void popQuote()  {
        queueService.popQueue();
    }

    @Test
    public void givenQuoteRequestModel_whenPostQuote_thenStatus200() throws Exception {

        List<ReplyDataModel> mockReplies = Collections.singletonList(new ReplyDataModel("Mock Reply"));
        QuoteRequestModel mockModel = new QuoteRequestModel(
                "Mock text", "Mock source", true, false, mockReplies, true);

        mvc.perform(MockMvcRequestBuilders
                .post("/quote/")
                .content(new ObjectMapper().writeValueAsString(mockModel))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value("Mock text"))
                .andExpect(jsonPath("$.source").value("Mock source"))
                .andExpect(jsonPath("$.showSource").value(true))
                .andExpect(jsonPath("$.invisible").value(false))
                .andExpect(jsonPath("$.replies").exists())
                .andExpect(jsonPath("$.replies[*].id").isNotEmpty())
                .andExpect(jsonPath("$.replies[*].text").value("Mock Reply"));
    }

    @Test
    public void givenArrayQuoteRequestModel_whenPostArrayQuote_thenStatus200() throws Exception {

        QuoteRequestModel mockModel = new QuoteRequestModel(
                "Mock text", "Mock source", true, false, Collections.singletonList(new ReplyDataModel("Mock Reply")), true);

        QuoteRequestModel mockModel2 = new QuoteRequestModel(
                "Mock text 2", "Mock source 2", true, false, null, true);

        AllQuoteRequestModel request = new AllQuoteRequestModel(Arrays.asList(mockModel, mockModel2));

        mvc.perform(MockMvcRequestBuilders
                .post("/quote/all")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].text").isNotEmpty())
                .andExpect(jsonPath("$[*].source").isNotEmpty())
                .andExpect(jsonPath("$[*].showSource").exists())
                .andExpect(jsonPath("$[*].invisible").exists())
                .andExpect(jsonPath("$[*].replies").exists());
    }

    @Test
    public void whenGetAllPosted_thenStatus200() throws Exception {

        QuoteRequestModel mockModel1 = new QuoteRequestModel(
                "Mock text 1", "Mock source 1", true, false, new ArrayList<>(), false);
        QuoteRequestModel mockModel2 = new QuoteRequestModel(
                "Mock text 2", "Mock source 2", true, false, new ArrayList<>(), false);

        List<QuoteRequestModel> models = Arrays.asList(mockModel1, mockModel2);

        saveItems(models);

        mvc.perform(MockMvcRequestBuilders
                .put(QUEUE_FORCE_POP)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        mvc.perform(MockMvcRequestBuilders
                .get("/quote/all/posted")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].text").value("Mock text 1"))
                .andExpect(jsonPath("$[*].source").value("Mock source 1"))
                .andExpect(jsonPath("$[*].showSource").value(true))
                .andExpect(jsonPath("$[*].invisible").value(false))
                .andExpect(jsonPath("$[*].replies").exists());
    }
}
