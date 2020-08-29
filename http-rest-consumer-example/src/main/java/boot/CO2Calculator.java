package boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import service.OpenRouteService;
import util.DEConstant;

/**
 * Spring shell component used for taking the input values from the shell.
 * CO2 usage will be computed
 * @author ShivanandBiradar
 *
 */
@ShellComponent
public class CO2Calculator  {

	// Service used for accessing open source route api
	@Autowired
	private OpenRouteService routeService;
	
	/** Define the shell command method to be invoked and named shell arguments **/
    @ShellMethod(key = DEConstant.SHELL_CMD_NAME, value = DEConstant.SHELL_CMD_DESC)
    public String calculate_co2(
      @ShellOption(DEConstant.CMD_OPT_START) String startcity,
      @ShellOption(DEConstant.CMD_OPT_END) String endcity,
      @ShellOption(DEConstant.CMD_OPT_TRANS) String trans_method
    ) {
    	
      // Invoke service to get coordinates of two cities
      String coordinates = routeService.getCoordinatesBetweenCities(startcity, endcity);
      
      // Check if coordinates not located
      if(coordinates == null) {

    	  return DEConstant.EMPTY;
      }
      
      // Calculate the CO2 and return result directly to the console as String
      return routeService.calculateCO2(coordinates, trans_method);
      
    }	  
}
