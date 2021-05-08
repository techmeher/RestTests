package api_tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@Test
public class RestGetTests {

    //https://restcountries.eu/#api-endpoints-all
    //https://reqres.in/api/users/2

    @Test(testName = "Traditional Style")
    public void TestOneTraditionalStyle() throws URISyntaxException {
        //Traditional Style
        URI _regesBaseURL = new URI("https://reqres.in/api/users/2");
        Response _response = get(_regesBaseURL);
        Assert.assertEquals(200, _response.statusCode());
        System.out.println(_response.getBody().asString());
    }

    @Test(testName = "BDD Style")
    public void TestTwoBddStyle() throws URISyntaxException {
        URI _restCountriesBaseURL = new URI("https://restcountries.eu/rest/v2/all");
        //BDD style
        given().
                get(_restCountriesBaseURL)
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("Afghanistan"));

        System.out.println("Rest countries response :: " + get(_restCountriesBaseURL).getBody().asString());
    }
}
