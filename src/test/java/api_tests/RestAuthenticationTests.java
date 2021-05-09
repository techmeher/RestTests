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
        _userCredentials.put("userName", "Meher78757");
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

/**
 * Now that we created user lets get the user
 */
        String _getUserURI = "/Account/v1/User/{UUID}";
        Response _getUserRes =
                _reqSpec
                        .pathParam("UUID", _userBody.userID)
                        .get(_getUserURI);

        UserDto _createdUser = _getUserRes.as(UserDto.class);
        MessageDto _userReponseDto = _response.as(MessageDto.class);
        System.out.println("User get status " + _userReponseDto);
        System.out.println("Created User details: " + _createdUser);


/*        //delete created user to keep the db tidy
        String _rawUserCredentials = _userCredentials.get("userName") + ":" + _userCredentials.get("password");
        byte[] _encodeToB64 = Base64.getEncoder().encode(_rawUserCredentials.getBytes());
        String _encodeCredentialsToBas64 = _encodeToB64.toString();

        String _deletionPath = "/Account/v1/User/{userId}";

        Response _deletionresponse =
                _reqSpec
                        .pathParam("userId", _userBody.userID)
                        .header("Authorization", "Basic " + _encodeCredentialsToBas64)
                        .delete(_deletionPath);

        MessageDto _deleteMessageBody = _deletionresponse.as(MessageDto.class);

        System.out.println("Deletion Message Response :: " + _deleteMessageBody);*/

    }

    @Test(testName = "Bearer Authentication Test End to End Test")
    public void BearerAuthenticationTest() {
        baseURI = "https://bookstore.toolsqa.com";
        String userCreateURI = "/Account/v1/User";
        String generateTokenURI = "/Account/v1/GenerateToken";
        String addBookURI = "/BookStore/v1/Books";
        String getUserDetailsURI = "/Account/v1/User/{UUID}";
        String deleteUserURI = "/Account/v1/User/{UUID}";

        Map<String, String> _userCredentials = new HashMap<>();
        _userCredentials.put("userName", "Meher109");
        _userCredentials.put("password", "Jaibaba01!");

        Gson _user = new Gson();
        RequestSpecification _reqSpec = given();
        String _convert = _user.toJson(_userCredentials);


        //Create user for user basic authentication.
        Response _userCreationResponse = given()
                .contentType(ContentType.JSON)
                .body(_convert)
                .post(userCreateURI);
        UserDto _userBody = _userCreationResponse.body().as(UserDto.class);
        MessageDto _userCreationResponseDto = _userCreationResponse.as(MessageDto.class);
        System.out.println("User creation status " + _userCreationResponseDto);
        System.out.println("User Id created " + _userBody.userID + " : " + _userBody.username);

        //Generate token for the above createdUserAccount
        Response _tokenGenerationResponse =
                given()
                        .body(_convert)
                        .contentType(ContentType.JSON)
                        .post(generateTokenURI);

        MessageDto _generateTokenMessage = _tokenGenerationResponse.as(MessageDto.class);
        System.out.println(_generateTokenMessage);

        //now do a put to insert book for the user account
        String _addBookPayload = "{\n" +
                "  \"userId\": \"" + _userBody.userID + "\",\n" +
                "  \"collectionOfIsbns\": [\n" +
                "    {\n" +
                "      \"isbn\": \"9781449331818\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Response _addBookToUserAccount =
                given()
                        .contentType(ContentType.JSON)
                        .body(_addBookPayload)
                        .header("Authorization", "Bearer " + _generateTokenMessage.token)
                        .post(addBookURI);

        MessageDto _bookAdditionDto = _addBookToUserAccount.as(MessageDto.class);

        System.out.println("Book addition Status " + _bookAdditionDto);

        //get user details

        Response _getUserDetailsResponse =
                given()
                        .header("Authorization", "Bearer " + _generateTokenMessage.token)
                        .pathParam("UUID", _userBody.userID)
                        .get(getUserDetailsURI);

        MessageDto _getUserMessageResponse = _getUserDetailsResponse.as(MessageDto.class);

        System.out.println("Get user details message dto : " + _getUserMessageResponse);
        UserDto _updatedUser = _getUserDetailsResponse.getBody().as(UserDto.class);

        System.out.println("Updated User Details " + _updatedUser);

/*
        //user deletion

        Response _userDeletionResponse =
                given()
                        .header("Authorization", "Bearer " + _generateTokenMessage.token)
                        .pathParam("UUID", _userBody.userID)
                        .delete(deleteUserURI);

        MessageDto _deleteMessageDto = _userDeletionResponse.as(MessageDto.class);

        System.out.println("User Deletion Message " + _deleteMessageDto);
*/

    }
}
