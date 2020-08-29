package boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring boot configuration
 * 
 * @author ShivanandBiradar
 *
 */
@SpringBootApplication
@ComponentScan(basePackages= {"service","boot","repo"})
public class ApplicationMain {

	public static void main(String[] args) {
		
		SpringApplication.run(ApplicationMain.class, args);
	}

}
