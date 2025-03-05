package com.techelevator.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.techelevator.model.EmailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    @Value("${email.api.key}")
    private String apiKey;

    public EmailService(@Value("${email.api.key}") String apiKey) {
        this.apiKey = apiKey;
    }

    public void sendSimpleMessage(EmailDto email) throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/sandbox086dc67468cb4a979d49363452c7453d.mailgun.org/messages")
                .basicAuth("api", apiKey)
                .queryString("from", "Frost and Found <noreply-frostandfound@gmail.com>")
                .queryString("to", email.getTo())
                .queryString("subject", email.getSubject())
                .queryString("text", email.getText())
                .asJson();
    }
}