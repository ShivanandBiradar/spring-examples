package repo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import util.DEConstant;

/**
 * Component used for the interacting with REST services
 * @author ShivanandBiradar
 *
 */
@Repository
public class RestComponentImpl implements RestComponent {

	@Value("${ORS_TOKEN}")
	private String api_key;

	@Value("${query.profile}")	
	private String queryProfile;

	@Value("${query.size}")	
	private String resultSize;

	@Value("${query.layers}")	
	private String queryLayers;

	@Value("${api.url}")	
	private String apiUrl;

	@Value("${api.distance_matrix}")	
	private String dmatrix;

	@Value("${api.geo_search}")	
	private String geosearch;

	@Value("${api.content}")	
	private String contentType;

	/* (non-Javadoc)
	 * @see repo.RestComponet#getDistance(java.lang.String)
	 */
	public String getDistance(String coordinates) {

		// Create a JSON query to filter
		String json = DEConstant.DIST_JSON_QUERY1 + coordinates + DEConstant.DIST_JSON_QUERY2;

		// HttpPost object by assigning distance matrix uri
		HttpPost request = new HttpPost(getDistanceMatrixUri());

		request.addHeader(DEConstant.AUTH, api_key);

		request.addHeader(DEConstant.CONTENTTYPE, contentType);

		request.setEntity(getEntityJsonQuery(json));

		// Request and Response call
		HttpResponse response = getResponseFromRequest(request);

		int statusCode = response.getStatusLine().getStatusCode();

		// verify response  code
		if (statusCode != DEConstant.HTTP_OK) {

			throw new RuntimeException(DEConstant.HTTP_ERROR_MSG+ statusCode);
		}

		String result = getResultFromHttpEntity(response.getEntity());

		// parse json string and retrieve the distances
		JSONObject jsonObj = new JSONObject(result);

		JSONArray distances = (JSONArray)jsonObj.getJSONArray(DEConstant.DISTANCES).get(0);

		//if distances not found, through error don't proceed
		if(distances == null) {

			throw new RuntimeException(DEConstant.DISTANCE_NOTFOUND_MSG);
		}

		return distances.toString();
	}

	/* (non-Javadoc)
	 * @see repo.RestComponet#getCoordinates(java.lang.String)
	 */
	public String getCoordinates(String city) {

		//HTTP Request and Response
		HttpGet request = new HttpGet(getCitySearchUri(city));

		HttpResponse response = getResponseFromRequest(request);

		//verify the valid error code first
		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode != DEConstant.HTTP_OK) {

			throw new RuntimeException(DEConstant.HTTP_ERROR_MSG + statusCode);
		}

		String result = getResultFromHttpEntity(response.getEntity());

		JSONObject jsonObj = new JSONObject(result);

		//Check If unable to locate the coordinates
		JSONArray ftJSArray = jsonObj.getJSONArray(DEConstant.FEATURES);

		if(null == ftJSArray || ftJSArray.isEmpty()) {

			throw new RuntimeException(DEConstant.COORDINATES_NOTFOUND_MSG);
		}

		JSONObject ftJson = (JSONObject) ftJSArray.get(0);

		JSONObject geoJson = (JSONObject)ftJson.get(DEConstant.GEOMETRY);

		JSONArray coordJson = geoJson.getJSONArray(DEConstant.COORDINATES);

		return coordJson.toString();

	}

	/**
	 * Formulate the uri along with different parameters
	 * @param city
	 * @return
	 */
	private URI getCitySearchUri(String city) {

		URIBuilder builder = new URIBuilder();

		URI searchUri = null;

		builder.setScheme(DEConstant.HTTPS).setHost(apiUrl).setPath(geosearch)
		.setParameter(DEConstant.API_KEY, api_key)
		.setParameter(DEConstant.TEXT, city)
		.setParameter(DEConstant.LAYERS, queryLayers)
		.setParameter(DEConstant.SIZE, resultSize);

		try {

			searchUri =  builder.build().toURL().toURI();

		} catch (MalformedURLException | URISyntaxException e) {

			throw new RuntimeException(DEConstant.CITY_SRC_ERROR_MSG + e.getMessage());
		}

		return searchUri;
	}

	/**
	 * Create Distance matrix Uri along with parameters
	 * @return uri
	 */
	private URI getDistanceMatrixUri() {

		URIBuilder builder = new URIBuilder();

		URI matrxUri = null;

		builder.setScheme(DEConstant.HTTPS).setHost(apiUrl).setPath(dmatrix + queryProfile);

		try {

			matrxUri = builder.build().toURL().toURI();

		} catch (MalformedURLException | URISyntaxException e) {

			throw new RuntimeException(DEConstant.DISTMATRX_ERROR_MSG + e.getMessage());
		}

		return matrxUri;

	}
	
	/**
	 * Return the http response from the request executed by http client
	 * @param request
	 * @return http response
	 */
	private HttpResponse getResponseFromRequest(HttpUriRequest request) {

		HttpClient httpClient = HttpClientBuilder.create().build();

		HttpResponse response = null;

		try {

			response = httpClient.execute(request);

		} catch (IOException e) {

			throw new RuntimeException(DEConstant.RESPONSE_ERROR_MSG + e.getMessage());
		}

		return response;
	}

	/**
	 * Query JSON as StringEntity
	 * @param json
	 * @return StringEntity
	 */
	private StringEntity getEntityJsonQuery(String json) {

		StringEntity input = null;

		try {

			input = new StringEntity(json);

		} catch (UnsupportedEncodingException e) {

			throw new RuntimeException(DEConstant.ENTITY_STR_ERROR_MSG + e.getMessage());
		}

		return input;

	}
	
	/**
	 * Now pull back the response object
	 * @param httpEntity
	 * @return json string
	 */
	private String getResultFromHttpEntity(HttpEntity httpEntity) {

		String result = null;

		try {

			result = EntityUtils.toString(httpEntity);

		} catch (ParseException | IOException e) {

			throw new RuntimeException(DEConstant.ENTITY_HTTP_ERROR_MSG + e.getMessage());
		}

		return result;
	}

}
