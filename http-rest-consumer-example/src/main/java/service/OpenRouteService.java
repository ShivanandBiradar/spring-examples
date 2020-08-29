package service;

/**
 * Service used for the calculating CO2 usage and interacting with REST services
 * @author ShivanandBiradar
 *
 */
public interface OpenRouteService {

	/**
	 * Returns the json array of coordinates [longitude,latitude] for given Cities
	 * @param startcity
	 * @param endcity
	 * @return coordinates
	 */
	public String getCoordinatesBetweenCities(String startcity, String endcity);
	
	/**
	 * Calculates the usage of the CO2 consumed for the distance
	 * @param coordinates
	 * @param vehicleType
	 */
	public String calculateCO2(String coordinates, String vehicleType);
}

