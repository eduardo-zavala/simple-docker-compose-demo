package com.example.greeting.controller;

import com.example.logging.dto.LogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GreetingController {

    private final RestTemplate restTemplate;

    private  final String LOGGING_SERVICE_HOST;

    public GreetingController(RestTemplate restTemplate,
                              @Value("${logging-service.url}") String loggingServiceHost) {
        this.restTemplate = restTemplate;
        LOGGING_SERVICE_HOST = loggingServiceHost;
    }

    @GetMapping("/greet")
    public String greeting(@RequestParam String name) {

        final String message = "Hello " + name + " from Docker microservices";

        final LogRequest logRequest = new LogRequest(message);

        this.restTemplate.postForObject(LOGGING_SERVICE_HOST + "/logs", logRequest, String.class);

        return message;
    }

}
