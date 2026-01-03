
package com.example.YAMLFileRead.service;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@ConfigurationProperties(prefix = "app")   // Reads: app.topics from YAML
public class TopicService {

    private List<String> topics;  
    // Bound from YAML
    private Set<String> topicSet = new HashSet<>();

    // Required setter for YAML binding
    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    // Called once after YAML values are set
    @PostConstruct
    public void loadTopics() {

        if (topics == null) {
            throw new IllegalStateException("Topics not found in application.yml");
        }

        for (String topic : topics) {
            addIfValid(topic);
        }
    }

    // Helper method (keeps code clean)
    private void addIfValid(String topic) {
        if (topic != null && !topic.trim().isEmpty()) {
            topicSet.add(topic.trim());
        }
    }

    // Get all topics
    public Set<String> getAllTopics() {
        return topicSet;
    }

    // Check if topic exists
    public boolean hasTopic(String topic) {
        return topicSet.contains(topic);
    }

    // Add a new topic
    public void addTopic(String topic) {
        addIfValid(topic);
    }

    // Delete topic
    public boolean deleteTopic(String topic) {
        return topicSet.remove(topic);
    }
}
