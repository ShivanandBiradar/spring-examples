package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import repo.RestComponent;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OpenRouteServiceImplTest {

	@MockBean
	private RestComponent restComponet;
	
	@Test
	public String getCoordinatesBetweenCities(String startcity, String endcity) {
		
		//Make Async call for following two web request and combining the results
		
		//when() to be implemented
		return null;		
		
	}
	
}
