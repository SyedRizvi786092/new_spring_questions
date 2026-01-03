package com.example.YAMLFileRead;

import com.example.YAMLFileRead.service.TopicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TopicService topicService;

    // 1️⃣ YAML -> Service load
    @Test
    void yamlDataLoadedIntoService() throws Exception {
        Set<String> topics = topicService.getAllTopics();
        assertThat(topics).contains("technical", "health", "sports");
    

        mockMvc.perform(get("/topics"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("technical")))
                .andExpect(content().string(containsString("health")))
                .andExpect(content().string(containsString("sports")));
    }

    // 3️⃣ GET existing topic -> 200
    @Test
    void getExistingTopicReturns200() throws Exception {
        String topic = topicService.getAllTopics().iterator().next();
        mockMvc.perform(get("/topics/" + topic))
                .andExpect(status().isOk())
                .andExpect(content().string(topic));
    }

    // 4️⃣ GET missing topic -> 404
    @Test
    void getMissingTopicReturns404() throws Exception {
        mockMvc.perform(get("/topics/unknown"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Topic not found"));
    }

    // 5️⃣ GET blank topic -> 400
    @Test
    void getBlankTopicReturns400() throws Exception {
        mockMvc.perform(get("/topics/ "))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Topic is empty"));
    }

    // 6️⃣ POST add topic -> 201
    @Test
    void addTopicReturns201() throws Exception {
        mockMvc.perform(post("/topics")
                        .param("topic", "finance"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Topic added"));

        assertThat(topicService.hasTopic("finance")).isTrue();
    }

    // 7️⃣ POST empty topic -> 400
    @Test
    void addEmptyTopicReturns400() throws Exception {
        mockMvc.perform(post("/topics")
                        .param("topic", ""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Topic is empty"));
    }

    // 8️⃣ DELETE existing topic -> 204
    @Test
    void deleteExistingTopicReturns204() throws Exception {
        topicService.addTopic("temp");

        mockMvc.perform(delete("/topics/temp"))
                .andExpect(status().isNoContent());
    }

    // 9️⃣ DELETE missing topic -> 404
    @Test
    void deleteMissingTopicReturns404() throws Exception {
        mockMvc.perform(delete("/topics/not-present"))
                .andExpect(status().isNotFound());
    }

    // 🔟 Controller returns Service data
    @Test
    void controllerReturnsUpdatedServiceData() throws Exception {
        topicService.addTopic("service-topic");

        mockMvc.perform(get("/topics"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("service-topic")));
    }
}

