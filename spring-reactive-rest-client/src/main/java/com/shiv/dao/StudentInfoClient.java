package com.shiv.dao;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.http.HttpResponse;

import io.reactivex.Flowable;

public interface StudentInfoClient {

	/**
	 * Fetch students information from the service by name
	 * @param name
	 * @return
	 */
	Flowable<HttpResponse<JsonNode>> getStudentsInformation(String name);
	
}
