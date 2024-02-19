package com.test.adidata.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.adidata.model.rest.fitness.RestAccountOtp;
import com.test.adidata.service.fitness.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/fitness/registration")
public class AccountController {
    @Autowired
    AccountService accountService;


    // account
    @PostMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object test(@RequestBody JsonNode body) {
        return accountService.addAccount(body);
    }

    @PostMapping(value = "/add-account", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addAccount(@RequestBody JsonNode body) {
        return accountService.addAccount(body);
    }

    @PostMapping(value = "/confirm-otp", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object confirmOtp(@RequestBody RestAccountOtp restAccountOtp) {
        return accountService.confirmOtp(restAccountOtp);
    }

    @PostMapping(value = "/inquiry-account", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object inquiryAccount(@RequestBody JsonNode body) {
        return accountService.inquiryAccount(body);
    }



    @PostMapping(value = "/reset-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object resetPassword(@RequestBody JsonNode body) {
        return accountService.resetPassword(body);
    }

    @PostMapping(value = "/confirm-otp-reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object confirmOtpReset(@RequestBody RestAccountOtp restAccountOtp) {
        return accountService.confirmOtpReset(restAccountOtp);
    }

}
