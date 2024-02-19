package com.test.adidata.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.adidata.service.fitness.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/fitness/subscription")
public class SubscriptionController {
    @Autowired
    SubscriptionService subscriptionService;


    @PostMapping(value = "/add-subscription", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addSubscription(@RequestHeader Map<String, String> headers, @RequestBody JsonNode body) {
        return subscriptionService.addSubscription(headers, body);
    }

    @PostMapping(value = "/update-subscription", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateSubscription(@RequestHeader Map<String, String> headers, @RequestBody JsonNode body) {
        return subscriptionService.updateSubscription(headers, body);
    }

}
