package api_tests;

import dtos.Book;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@Test
public class RestGetTests {

    //https://restcountries.eu/#api-endpoints-all
    //https://reqres.in/api/users/2
    //https://bookstore.toolsqa.com/swagger/#/

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
        URI _restCountriesBaseURL = new URI("https://restcountries.eu/rest/v2/name/india");
        //BDD style with assertions and logging
        given().
                get(_restCountriesBaseURL)
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("British Indian Ocean Territory"))
                .body("name", hasItem("India"))
                .log().all(); //printing output to console including response headers and body
    }

    @Test(testName = "Test Pojos in Get request")
    public void TestGetToPOJOs() {
        baseURI = "https://bookstore.toolsqa.com";


        JsonPath jsonPath =
                when()
                        .get("/BookStore/v1/Books")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .assertThat()
                        .extract().body().jsonPath();

        List<Book> _books = jsonPath.getList("books", Book.class);

        System.out.println("List size of retrieved Books :: " + _books.size());

        _books.stream().forEach(System.out::println);
    }
}
