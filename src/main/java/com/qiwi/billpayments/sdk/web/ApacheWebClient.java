package com.qiwi.billpayments.sdk.web;

import com.qiwi.billpayments.sdk.exception.ApacheHttpClientException;
import com.qiwi.billpayments.sdk.exception.UrlEncodingException;
import com.qiwi.billpayments.sdk.model.ResponseData;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

public class ApacheWebClient implements WebClient {
    private final HttpClient httpClient;

    public ApacheWebClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public ResponseData request(
            String method,
            String url,
            Optional<String> entityOpt,
            Map<String, String> headers
    ) {
        try {
            HttpRequest request = buildRequest(method, url, entityOpt, headers);
            HttpResponse response = httpClient.execute(extractHost(url), request);

            try (InputStream is = response.getEntity().getContent();
                 InputStreamReader reader = new InputStreamReader(is);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {

                return new ResponseData(
                        bufferedReader.readLine(),
                        response.getStatusLine().getStatusCode()
                );
            }
        } catch (IOException e) {
            throw new ApacheHttpClientException(e);
        }
    }

    private HttpRequest buildRequest(
            String method,
            String url,
            Optional<String> entityOpt,
            Map<String, String> headers
    ) {
        BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest(method, url);
        headers.forEach(request::addHeader);
        entityOpt.map(e -> new StringEntity(e, StandardCharsets.UTF_8))
                .ifPresent(request::setEntity);

        return request;
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
