package service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repo.RestComponent;
import util.DEConstant;
import util.Utility;

/**
 * Service used for the calculating CO2 usage and interacting with REST services
 * @author ShivanandBiradar
 *
 */
@Service
public class OpenRouteServiceImpl implements OpenRouteService {
	
	@Autowired
	private RestComponent restComponet;
	
	@Override
	public String getCoordinatesBetweenCities(String startcity, String endcity) {
		
		//Make Async call for following two web request and combining the results
		
		CompletableFuture<String> startAsync = getCoordinateAsync(startcity);
		
		CompletableFuture<String> endAsync = getCoordinateAsync(endcity);
		
		CompletableFuture<String> completableFuture = startAsync.thenCombine(endAsync, (s1,s2) -> s1 + DEConstant.COMMA + s2);
		
		try {
			
			return completableFuture.get();
			
		} catch (InterruptedException | ExecutionException e) {
			
			throw new RuntimeException(DEConstant.FETCH_ASYNC_ERR_MSG + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see service.OpenRouteService#calculateCO2(java.lang.String, java.lang.String)
	 */
	@Override
	public String calculateCO2(String coordinates, String vehicleType) {
		
		//Get the the distance from coordinates
		String distance = restComponet.getDistance(coordinates);
		
		//strip special chars and retail values by comma seperated
		distance = distance.replace(DEConstant.OPEN_SBRAKET, DEConstant.EMPTY).replace(DEConstant.CLOSE_SBRAKET, DEConstant.EMPTY);
		
		//get the distances as array
		String [] dists = distance.split(DEConstant.COMMA);
		
		// find the distance, from matrix value from src to destination from last index
		double fdistance = Double.valueOf(dists[1]);
		
		//Check if there are no more ways to travel
		if(fdistance <= 0.0) {
			
			throw new RuntimeException(DEConstant.DISTNACE_NOT_FOUND_MSG);
		}
		
		double usage = Utility.getCO2Usage(vehicleType);
		
		//Check invalid vehicle types
		if (usage == 0.0) {

			throw new RuntimeException(DEConstant.INVALID_VEHICLE_TYPE + vehicleType);

		}
		
		// calculating for the total distance of km
		usage = usage * fdistance;

		//converting into kg if total usage less that 1kg
		if(usage > 1000.0) {
			
			usage = usage/1000.0;
			
			return DEConstant.SUCCES_MSG_PRFX + String.format(DEConstant.DECIFMT, usage)+ DEConstant.SUCCES_MSG_KG_SFX;
			
		} else {
			
			return DEConstant.SUCCES_MSG_PRFX + String.format(DEConstant.DECIFMT, usage)+ DEConstant.SUCCES_MSG_G_SFX;
		}

	}
	
	/**
	 * Makes Async Web request
	 * @param city
	 * @return CompletableFuture
	 */
	private CompletableFuture<String> getCoordinateAsync(String city){

        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
        	
            @Override
            public String get() {
            	
                final String coordinates = restComponet.getCoordinates(city);

                return coordinates;
            }
        });

        return future;
    }

}
