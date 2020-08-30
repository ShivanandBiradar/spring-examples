package com.shiv.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiv.exception.CustomClientException;

import java.net.http.HttpResponse;

import static com.shiv.constants.ApplicationConstants.*;

public class ResponseHandler implements HttpResponse.BodyHandler<JsonNode> {

    private final ObjectMapper mapper;

    public static ResponseHandler responseBodyHandler() {
        return new ResponseHandler();
    }

    private ResponseHandler() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public HttpResponse.BodySubscriber<JsonNode> apply(final HttpResponse.ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofByteArray(),
                bytes -> {
                    try {
                        return this.mapper.readTree(new ByteArrayInputStream(bytes));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
    
    public static HttpResponse<JsonNode> handleResponseStatusCode(final HttpResponse<JsonNode> response) {
        final int responseStatusCode = response.statusCode();
        if (responseStatusCode > SUCCESS_STATUS_CODE_LIMIT) {
            throw new CustomClientException(response.body().toString(), responseStatusCode);
        }
        return response;
    }
}
