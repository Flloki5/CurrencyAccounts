package com.currencyapp.client;

import com.currencyapp.model.Currency;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class NbpClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public NbpClient(ObjectMapper objectMapper) {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        this.objectMapper = objectMapper;
    }

    public Currency getNbpCurrency(String code) throws URISyntaxException, InterruptedException, IOException {

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.nbp.pl/api/exchangerates/rates/c/" + code))
                .headers(HttpHeaders.ACCEPT, "application/json")
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(httpResponse.body(), Currency.class);

    }
}

