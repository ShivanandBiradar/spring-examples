package util;

/**
 * Declaring the Constants
 * @author ShivanandBiradar
 *
 */
public interface DEConstant {
	
	public static final String SHELL_CMD_NAME = "co2-calculator";
	
	public static final String CMD_OPT_START = "--start";
	
	public static final String CMD_OPT_END = "--end";
	
	public static final String CMD_OPT_TRANS = "--transportation-method";
	
	public static final String SHELL_CMD_DESC = "Determine the travel usage of CO2 from one city to another.";
	
	public static final String HTTPS = "https";

	public static final String API_KEY="api_key";

	public static final String TEXT = "text";

	public static final String LAYERS = "layers";

	public static final String AUTH = "Authorization";

	public static final String CONTENTTYPE = "Content-Type";

	public static final String DIST_JSON_QUERY1 = "{\"locations\":[";

	public static final String DIST_JSON_QUERY2 = "],\"metrics\":[\"distance\"],\"units\":\"km\"}";

	public static final String SIZE = "size";

	public static final String OPEN_SBRAKET = "[";

	public static final String CLOSE_SBRAKET = "]";

	public static final String COMMA = ",";

	public static final String EMPTY = "";
	
	public static final String COORDINATES = "coordinates";
	
	public static final String GEOMETRY = "geometry";
	
	public static final String FEATURES = "features";
	
	public static final String DISTANCES = "distances";
	
	public static final String DECIFMT = "%.2f";
	
	public static final int HTTP_OK = 200;
	
	public static final String SUCCES_MSG_PRFX = "Your trip caused ";
	
	public static final String SUCCES_MSG_KG_SFX = "kg of CO2-equivalent.";
	
	public static final String SUCCES_MSG_G_SFX = "g of CO2-equivalent.";
	
	/*Declaration of error messages*/
	
	public static final String HTTP_ERROR_MSG = "Failed with HTTP error code : ";
	
	public static final String DISTANCE_NOTFOUND_MSG = "Unable to locate the distance between two places.";
	
	public static final String CITY_SRC_ERROR_MSG = "Exception with City search Uri : ";
	
	public static final String DISTMATRX_ERROR_MSG = "Exception with distance matrix Uri : ";
	
	public static final String RESPONSE_ERROR_MSG = "Exception while getting response : ";
	
	public static final String ENTITY_STR_ERROR_MSG = "Exception with Json query StringEntity : ";
	
	public static final String ENTITY_HTTP_ERROR_MSG ="Exception with result from HttpEntity : ";
	
	public static final String INVALID_VEHICLE_TYPE = "Couldn't find CO2 usage for the specified transportation -";
	
	public static final String DISTNACE_NOT_FOUND_MSG = "Couldn't find travelling distance between the specified cities.";
	
	public static final String COORDINATES_NOTFOUND_MSG = "Couldn't locate the coordinates for the cities (Verify city name)!";
	
	public static final String FETCH_ASYNC_ERR_MSG = "Exception while asyncronous fetching of coordinates.";
	
}
