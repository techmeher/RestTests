package api_tests;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

public class RestPatchAndPostTests {

    @Test(testName = "Simple Post Request Test")
    public void PostRequestTest() throws URISyntaxException {
        URI _postUrl = new URI("https://reqres.in/api/users");
        Map<String, String> _user = new HashMap<String, String>();
        _user.put("name", "Jaibaba");
        _user.put("job", "God");

        Gson _gson = new Gson();
        String _convert = _gson.toJson(_user);
        System.out.println("Request body content " + _convert);
        given()
                .contentType(ContentType.JSON)
                .body(_convert).post(_postUrl)
                .then()
                .statusCode(201)
                .log().all();
    }

    @Test(testName = "Simple Patch Request Test")
    public void PatchRequestTest() throws URISyntaxException {
        URI _patchUrl = new URI("https://reqres.in/api/users/2");

        Map<String, String> _user = new HashMap<String, String>();
        _user.put("name", "Jaibaba");
        _user.put("job", "Avatar");

        Gson _gson = new Gson();
        String _convert = _gson.toJson(_user);
        System.out.println("Request body content " + _convert);
        given()
                .contentType(ContentType.JSON)
                .body(_convert).patch(_patchUrl)
                .then()
                .statusCode(200)
                .log().all();
    }


}
