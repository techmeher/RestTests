package api_tests;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dtos.MessageDto;
import dtos.UserDto;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

/***
 * API test user https://bookstore.toolsqa.com/swagger/#/
 * Authentication -- Whether you have access to server
 * Authorisation  -- Whether you have access to particular resource [this come after authentication]
 * Types for authentication
 * Basic Authentication -- username password as parameters or in request body sent as part of url unencrypted very vulnerable, used
 * to transport over regular http and also encoded with base 64
 * Token And Bearer Authentication used over https protocol similar to oauth2.0 but can be used independently as well.
 * OAUTH 1.0 / 2.0
 */


public class RestAuthenticationTests {

    @Test(testName = "Basic Authentication Test")
    public void BasicAuthenticationTest() {
        baseURI = "https://bookstore.toolsqa.com";
        Map<String, String> _userCredentials = new HashMap<>();
        _userCredentials.put("userName", "Meher78");
        _userCredentials.put("password", "Jaibaba01!");

        Gson _user = new Gson();
        RequestSpecification _reqSpec = given();
        String _convert = _user.toJson(_userCredentials);
        //create user for user basic authentication.
        Response _response = given()
                .contentType(ContentType.JSON)
                .body(_convert)
                .post("/Account/v1/User");
        UserDto _userBody = _response.body().as(UserDto.class);
        MessageDto _userCreationReponseDto = _response.as(MessageDto.class);
        System.out.println("User creation status " + _userCreationReponseDto);
        System.out.println("User Id created " + _userBody.userID + " : " + _userBody.username);


        //delete created user to keep the db tidy
        String _rawUserCredentials = _userCredentials.get("userName") + ":" + _userCredentials.get("password");
        byte[] _encodeToB64 = Base64.getEncoder().encode(_rawUserCredentials.getBytes());
        String _encodeCredentialsToBas64 = _encodeToB64.toString();

        String _deletionPath = "/Account/v1/User/" + _userBody.userID;

        Response _deletionresponse =
                _reqSpec.header("Authorization", "Basic " + _encodeCredentialsToBas64)
                        .delete(_deletionPath);

        MessageDto _deleteMessageBody = _deletionresponse.as(MessageDto.class);

        System.out.println("Deletion Message Response :: " + _deleteMessageBody);

    }

}
