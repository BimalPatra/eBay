package APIAutomation;
import static io.restassured.RestAssured.given;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;


public class getRequest {
	
	@Test()
	public void getResult(){
		String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
		
		Response res = given()
		   .when()
		   .get(url);
		
		Assert.assertEquals(res.statusCode(), 200);
		
		Map<String, Map<String, Object>> bpi = res.jsonPath().getMap("bpi");
		
		Assert.assertEquals(bpi.size(), 3, "BPI does not contain exactly 3 currencies");
        Assert.assertTrue(bpi.containsKey("USD"), "BPI does not contain USD");
        Assert.assertTrue(bpi.containsKey("GBP"), "BPI does not contain GBP");
        Assert.assertTrue(bpi.containsKey("EUR"), "BPI does not contain EUR");

        String gbpDescription = res.jsonPath().getString("bpi.GBP.description");
        Assert.assertEquals(gbpDescription, "British Pound Sterling", "The GBP description mismatch"); // validation GBP description
	}  
}
