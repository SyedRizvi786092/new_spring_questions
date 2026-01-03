
package com.example.YAMLFileRead.controller;

import com.example.YAMLFileRead.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService service;

    public TopicController(TopicService service) {
        this.service = service;
    }

    // GET /topics -> 200
    @GetMapping
    public ResponseEntity<Set<String>> getAllTopics() {
        return ResponseEntity.ok(service.getAllTopics());
    }

    // GET /topics/{topic} -> 200 / 400 / 404
    @GetMapping("/{topic}")
    public ResponseEntity<String> getTopic(@PathVariable String topic) {

        if (topic == null || topic.trim().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Topic is empty");
        }

        if (!service.hasTopic(topic)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Topic not found");
        }

        return ResponseEntity.ok(topic);
    }

    // POST /topics -> 201 / 400
    @PostMapping
    public ResponseEntity<String> addTopic(@RequestParam String topic) {

        if (topic == null || topic.trim().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Topic is empty");
        }

        service.addTopic(topic.trim());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Topic added");
    }

    // DELETE /topics/{topic} -> 204 / 404
    @DeleteMapping("/{topic}")
    public ResponseEntity<Void> deleteTopic(@PathVariable String topic) {

        if (!service.deleteTopic(topic)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
