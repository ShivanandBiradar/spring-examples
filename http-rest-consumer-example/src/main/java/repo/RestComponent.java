package repo;

/**
 * Component used for the interacting with REST services
 * @author ShivanandBiradar
 *
 */
public interface RestComponent {

	/**
	 * Returns coordinates [longitude,latitude] for given City name
	 * @param city
	 * @return coordinates [longitude,latitude]
	 */
	public String getCoordinates(String city);

	/**
	 * Return distances between two cities in km
	 * @param coordinates
	 * @return distance
	 */
	public String getDistance(String coordinates);
}
