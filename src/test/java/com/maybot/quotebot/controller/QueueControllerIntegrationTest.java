package com.maybot.quotebot.controller;

import com.maybot.quotebot.QuotebotApplication;
import com.maybot.quotebot.model.AllQuoteRequestModel;
import com.maybot.quotebot.model.QuoteRequestModel;
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

import static com.maybot.quotebot.constant.PathConstants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    public void whenGetQueue_thenStatus200() throws Exception {

        QuoteRequestModel mockModel1 = new QuoteRequestModel(
                "Mock text 1", "Mock source 1", true, false, new ArrayList<>(), false);
        QuoteRequestModel mockModel2 = new QuoteRequestModel(
                "Mock text 2", "Mock source 2", true, false, new ArrayList<>(), false);
        QuoteRequestModel mockModel3 = new QuoteRequestModel(
                "Mock text 3", "Mock source 3", true, false, new ArrayList<>(), false);

        List<QuoteRequestModel> models = Arrays.asList(mockModel1, mockModel2, mockModel3);
        saveItems(models);

        mvc.perform(MockMvcRequestBuilders
                .get(QUEUE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].index").isNotEmpty())
                .andExpect(jsonPath("$[*].priority").isNotEmpty())
                .andExpect(jsonPath("$[*].quote").isNotEmpty());
    }

    @Test
    public void whenPopQueue_thenStatus200() throws Exception {

        List<String> mockReplies = Collections.singletonList("Mock Reply");

        QuoteRequestModel mockModel1 = new QuoteRequestModel(
                "Mock text 1", "Mock source 1", true, false, mockReplies, false);
        QuoteRequestModel mockModel2 = new QuoteRequestModel(
                "Mock text 2", "Mock source 2", true, false, new ArrayList<>(), false);
        QuoteRequestModel mockModel3 = new QuoteRequestModel(
                "Mock text 3", "Mock source 3", true, false, new ArrayList<>(), false);

        List<QuoteRequestModel> models = Arrays.asList(mockModel1, mockModel2, mockModel3);
        saveItems(models);

        mvc.perform(MockMvcRequestBuilders
                .put(QUEUE_FORCE_POP)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value("Mock text 1"))
                .andExpect(jsonPath("$.source").value("Mock source 1"))
                .andExpect(jsonPath("$.showSource").value(true))
                .andExpect(jsonPath("$.invisible").value(false))
                .andExpect(jsonPath("$.replies").exists())
                .andExpect(jsonPath("$.replies[*].id").isNotEmpty())
                .andExpect(jsonPath("$.replies[*].text").value("Mock Reply 1"));
    }

    @Test
    public void whenPopQueue_AndPriorityTrue_thenStatus200() throws Exception {

        List<String> mockReplies = Collections.singletonList("Mock Reply 2");

        QuoteRequestModel mockModel1 = new QuoteRequestModel(
                "Mock text 1", "Mock source 1", true, false, new ArrayList<>(), false);
        QuoteRequestModel mockModel2 = new QuoteRequestModel(
                "Mock text 2", "Mock source 2", true, false, mockReplies, true);
        QuoteRequestModel mockModel3 = new QuoteRequestModel(
                "Mock text 3", "Mock source 3", true, false, new ArrayList<>(), false);

        List<QuoteRequestModel> models = Arrays.asList(mockModel1, mockModel2, mockModel3);
        saveItems(models);

        mvc.perform(MockMvcRequestBuilders
                .put(QUEUE_FORCE_POP)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value("Mock text 2"))
                .andExpect(jsonPath("$.source").value("Mock source 2"))
                .andExpect(jsonPath("$.showSource").value(true))
                .andExpect(jsonPath("$.invisible").value(false))
                .andExpect(jsonPath("$.replies").exists())
                .andExpect(jsonPath("$.replies[*].id").isNotEmpty())
                .andExpect(jsonPath("$.replies[*].text").value("Mock Reply 2"));
    }

    @Test
    public void whenPopQueue_AndInvisibilityTrue_thenStatus200() throws Exception {

        QuoteRequestModel mockModel1 = new QuoteRequestModel(
                "Mock text 1", "Mock source 1", true, true, new ArrayList<>(), false);
        QuoteRequestModel mockModel2 = new QuoteRequestModel(
                "Mock text 2", "Mock source 2", true, false, new ArrayList<>(), false);
        QuoteRequestModel mockModel3 = new QuoteRequestModel(
                "Mock text 3", "Mock source 3", true, false, new ArrayList<>(), false);

        List<QuoteRequestModel> models = Arrays.asList(mockModel1, mockModel2, mockModel3);

        saveItems(models);

        mvc.perform(MockMvcRequestBuilders
                .put(QUEUE_FORCE_POP)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value("Mock text 2"))
                .andExpect(jsonPath("$.source").value("Mock source 2"))
                .andExpect(jsonPath("$.showSource").value(true))
                .andExpect(jsonPath("$.invisible").value(false))
                .andExpect(jsonPath("$.replies").exists());
    }

    @Test
    public void whenPopQueue_AndAgain_thenStatus200() throws Exception {

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
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value("Mock text 1"))
                .andExpect(jsonPath("$.source").value("Mock source 1"))
                .andExpect(jsonPath("$.showSource").value(true))
                .andExpect(jsonPath("$.invisible").value(false))
                .andExpect(jsonPath("$.replies").exists());

        mvc.perform(MockMvcRequestBuilders
                .put(QUEUE_FORCE_POP)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value("Mock text 2"))
                .andExpect(jsonPath("$.source").value("Mock source 2"))
                .andExpect(jsonPath("$.showSource").value(true))
                .andExpect(jsonPath("$.invisible").value(false))
                .andExpect(jsonPath("$.replies").exists());

        // Empty queue, makes a new one randomly

        mvc.perform(MockMvcRequestBuilders
                .put(QUEUE_FORCE_POP)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").isNotEmpty())
                .andExpect(jsonPath("$.source").isNotEmpty())
                .andExpect(jsonPath("$.showSource").isNotEmpty())
                .andExpect(jsonPath("$.invisible").isNotEmpty())
                .andExpect(jsonPath("$.replies").exists());
    }

    @Test
    public void whenPopQueue_AndWhenGetAllPosted_thenStatus200() throws Exception {

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
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.text").value("Mock text 1"))
                .andExpect(jsonPath("$.source").value("Mock source 1"))
                .andExpect(jsonPath("$.showSource").value(true))
                .andExpect(jsonPath("$.invisible").value(false))
                .andExpect(jsonPath("$.replies").exists());


        mvc.perform(MockMvcRequestBuilders
                .get(QUEUE_POSTED)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].index").isNotEmpty())
                .andExpect(jsonPath("$[*].priority").isNotEmpty())
                .andExpect(jsonPath("$[*].quote").isNotEmpty())
                .andExpect(jsonPath("$[*].quote.id").isNotEmpty())
                .andExpect(jsonPath("$[*].quote.text").value("Mock text 1"))
                .andExpect(jsonPath("$[*].quote.source").value("Mock source 1"))
                .andExpect(jsonPath("$[*].quote.showSource").value(true))
                .andExpect(jsonPath("$[*].quote.invisible").value(false))
                .andExpect(jsonPath("$[*].quote.replies").exists());
    }
}
