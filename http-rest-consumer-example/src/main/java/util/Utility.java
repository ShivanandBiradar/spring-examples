package util;

/**
 * Utility class
 * @author ShivanandBiradar
 *
 */
public class Utility {

	/**
	 * Returns pre-defined CO2 usage for different vehicle types
	 * @param vehicleType
	 * @return usage
	 */
	public static double getCO2Usage(String vehicleType) {

		double usage = 0.0;

		switch(vehicleType) {

			case "small-diesel-car":
				usage = 142.0;
				break;
	
			case "small-petrol-car":
				usage = 154.0;
				break;
				
			case "small-plugin-hybrid-car":
				usage = 73.0;
				break;
				
			case "small-electric-car":
				usage = 50.0;
				break;
				
			case "medium-diesel-car":
				usage = 171.0;
				break;
				
			case "medium-petrol-car":
				usage = 192.0;
				break;
				
			case "medium-plugin-hybrid-car":
				usage = 110.0;
				break;
				
			case "medium-electric-car":
				usage = 58.0;
				break;
				
			case "large-diesel-car":
				usage = 209.0;
				break;
				
			case "large-petrol-car":
				usage = 282.0;
				break;
				
			case "large-plugin-hybrid-car":
				usage = 126.0;
				break;
				
			case "large-electric-car":
				usage = 73.0;
				break;
				
			case "bus":
				usage = 27.0;
				break;
				
			case "train":
				usage = 6.0;
				break;
				
			default:
				usage = 0.0;
				break;
		}

		return usage;
	}
}
