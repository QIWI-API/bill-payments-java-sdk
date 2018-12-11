package com.qiwi.billpayments.sdk.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiwi.billpayments.sdk.exception.HttpException;
import com.qiwi.billpayments.sdk.exception.UrlEncodingException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

public class ApacheWebClient implements WebClient {
    private final ObjectMapper mapper;
    private final HttpClient httpClient;

    public ApacheWebClient(HttpClient httpClient, ObjectMapper objectMapper) {
        this.mapper = objectMapper;
        this.httpClient = httpClient;
    }

    @Override
    public <T> T doRequest(
            String method,
            String url,
            Optional<Object> entityOpt,
            Class<T> responseClass,
            Map<String, String> headers
    ) {
        try {
            BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest(method, url);
            headers.forEach(request::addHeader);
            entityOpt.ifPresent(entity -> {
                try {
                    String json = mapper.writeValueAsString(entity);
                    request.setEntity(new StringEntity(json));
                } catch (IOException e) {
                    throw new HttpException(e);
                }
            });
            HttpResponse response = httpClient.execute(extractHost(url), request);
            return mapper.readValue(
                    response.getEntity().getContent(),
                    responseClass
            );
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }

    private HttpHost extractHost(String baseUrl) {
        try {
            URIBuilder uri = new URIBuilder(baseUrl);
            return new HttpHost(
                    uri.getHost(),
                    uri.getPort(),
                    uri.getScheme()
            );
        } catch (URISyntaxException e) {
            throw new UrlEncodingException(e);
        }
    }
}
