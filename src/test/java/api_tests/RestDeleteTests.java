package api_tests;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class RestDeleteTests {

    @Test(testName = "Delete related tests")
    public void DeleteRequestTest() {
        baseURI = "https://bookstore.toolsqa.com";
        RequestSpecification _httpRequest = given();

        //initial check whether base page is working or not
        _httpRequest.get().then().statusCode(200).log().all();

        //check all books response first
        Response _allBooksResponse = _httpRequest.request(Method.GET, "/BookStore/v1/Books");

        _allBooksResponse.then().statusCode(200).log().all();
    }
}
