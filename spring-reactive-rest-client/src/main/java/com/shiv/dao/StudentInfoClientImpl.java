package com.shiv.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.shiv.service.DataServiceImpl;
import com.shiv.utils.ResponseHandler;

import io.reactivex.Flowable;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.MediaType.JSON_UTF_8;
import static com.shiv.constants.LogMessageConstants.*;
import static com.shiv.constants.ApplicationConstants.*;

@Repository
public class StudentInfoClientImpl implements StudentInfoClient{
	
    private static final Logger LOG = LoggerFactory.getLogger(StudentInfoClientImpl.class);
    
    @Value("${studentInfo.url}")
    private String studentInfo;

    @Value("${api.accesskey}")
    private String apiKey;
    
    private final HttpClient httpClient = HttpClient.newBuilder().build();
    
	@Override
	public Flowable<HttpResponse<JsonNode>> getStudentsInformation(final String name) {
		 return Flowable.fromFuture(httpClient.sendAsync(createStudentInfoRequest(name), ResponseHandler.responseBodyHandler()))
	                .map(ResponseHandler::handleResponseStatusCode)
	                .doOnError(error -> LOG.error(ERROR_RETRIEVING_STUDENT_DETAILS));
	}

    private HttpRequest createStudentInfoRequest(final String studentName) {
        return createRequestBuilder()
                .uri(createURI(String.format(STUDENTINFO_CLIENT_ENDPOINT, encodeValue(studentName))))
                .GET()
                .build();
    }

    private HttpRequest.Builder createRequestBuilder() {
        return HttpRequest.newBuilder()
                .header(CONTENT_TYPE, JSON_UTF_8.toString())
                .header(AUTHORIZATION, apiKey);
    }

    private URI createURI(final String endpoint) {
        final String url = studentInfo + endpoint;
        return URI.create(url);
    }

    private String encodeValue(final String value) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            LOG.error(ENCODING_ERROR, e.getMessage(), e);
        }
        return encoded;
    }
}
