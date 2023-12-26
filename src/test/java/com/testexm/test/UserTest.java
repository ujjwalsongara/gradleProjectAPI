package com.testexm.test;

import com.testexm.BaseClass;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.http.Method;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTest extends BaseClass {

    @Test
    public void getUserTest() throws JSONException {
        Response userResponse = getUser();
        assertThat(userResponse.getStatusCode(), is(HttpStatus.SC_OK));

        JSONArray userArrayList = new JSONArray(userResponse.asString());

        assertThat(userArrayList.length(), greaterThan(0));

        JSONObject userList = userArrayList.getJSONObject(0);


        assertThat(userList.getInt("id"), notNullValue());
        assertThat(userList.getString("name"), notNullValue());
        assertThat(userList.getString("gender"), notNullValue());
        assertThat(userList.getString("email"), notNullValue());
        assertThat(userList.getString("status"), notNullValue());
    }

    @Test
    public void postUserTest() throws JSONException {
        Faker faker = new Faker();
        String userName = faker.name().name();
        String userEmail = faker.internet().emailAddress();
        String userGender = "male";
        String userStatus = "active";

        String userBody = "{\n " +
                "\"name\":\"" + userName + "\",\n" +
                "\"email\":\"" + userEmail + "\",\n" +
                "\"gender\":\"" + userGender + "\",\n" +
                "\"status\":\"" + userStatus + "\"\n" +
                "}";
        Response postUserResponse = postUsers(userBody);

        assertThat(postUserResponse.getStatusCode(), is(HttpStatus.SC_CREATED));

        JSONObject postData = new JSONObject(postUserResponse.asString());

        logger.info(postUserResponse.asString());

        int userId = postData.getInt("id");

        assertThat(postData.getInt("id"), notNullValue());
        assertThat(postData.getString("name"), is(userName));
        assertThat(postData.getString("email"), is(userEmail));
        assertThat(postData.getString("gender"), is(userGender));
        assertThat(postData.getString("status"), is(userStatus));

        Response deleteUserResponse = deleteUsers(userId);

        assertThat(deleteUserResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));
    }

    @Test
    public void putUserTest() throws JSONException {
        Faker faker = new Faker();
        String userName = faker.name().name();
        String userEmail = faker.internet().emailAddress();
        String userGender = "male";
        String userStatus = "active";
        String updatedUserName = faker.name().name();
        String updateUserEmail = faker.internet().emailAddress();
        String updateUserGender = "male";
        String updateUserStatus = "active";

        String userBody = "{\n " +
                "\"name\":\"" + userName + "\",\n" +
                "\"email\":\"" + userEmail + "\",\n" +
                "\"gender\":\"" + userGender + "\",\n" +
                "\"status\":\"" + userStatus + "\"\n" +
                "}";

        String updateUserBody = "  {\n" +
                "    \"email\":\"" + updateUserEmail + "\",\n" +
                "    \"name\":\"" + updatedUserName + "\",\n" +
                "    \"gender\": \"" + updateUserGender + "\",\n" +
                "    \"status\":\"" + updateUserStatus + "\"\n" +
                "}";
        Response postUserResponse = postUsers(userBody);
        assertThat(postUserResponse.getStatusCode(), is(HttpStatus.SC_CREATED));
        JSONObject postData = new JSONObject(postUserResponse.asString());

        logger.info(postUserResponse.asString());

        int userId = postData.getInt("id");
        assertThat(postData.getInt("id"), (notNullValue()));
        assertThat(postData.getString("name"), is(userName));
        assertThat(postData.getString("email"), is(userEmail));
        assertThat(postData.getString("gender"), is(userGender));
        assertThat(postData.getString("status"), is(userStatus));

        Response putUserResponse = putUser(updateUserBody, userId);
        assertThat(putUserResponse.getStatusCode(), is(HttpStatus.SC_OK));

        JSONObject putUserData = new JSONObject(putUserResponse.asString());

        int putUserId = putUserData.getInt("id");

        assertThat(putUserData.getInt("id"), is(putUserId));
        assertThat(putUserData.getString("name"), is(updatedUserName));
        assertThat(putUserData.getString("email"), is(updateUserEmail));
        assertThat(putUserData.getString("gender"), is(updateUserGender));
        assertThat(putUserData.getString("status"), is(updateUserStatus));

        Response deleteUserResponse = deleteUsers(userId);

        assertThat(deleteUserResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));
    }

    @Test
    public void deleteUserTest() throws JSONException {
        Faker faker = new Faker();
        String userName = faker.name().name();
        String userEmail = faker.internet().emailAddress();
        String userGender = "male";
        String userStatus = "active";
        String userBody = "{\n " +
                "\"name\":\"" + userName + "\",\n" +
                "\"email\":\"" + userEmail + "\",\n" +
                "\"gender\":\"" + userGender + "\",\n" +
                "\"status\":\"" + userStatus + "\"\n" +
                "}";
        Response postUserResponse = postUsers(userBody);

        assertThat(postUserResponse.getStatusCode(), is(HttpStatus.SC_CREATED));

        JSONObject postData = new JSONObject(postUserResponse.asString());

        logger.info(postUserResponse.asString());

        int userId = postData.getInt("id");

        assertThat(postData.getInt("id"), notNullValue());
        assertThat(postData.getString("name"), is(userName));
        assertThat(postData.getString("email"), is(userEmail));
        assertThat(postData.getString("gender"), is(userGender));
        assertThat(postData.getString("status"), is(userStatus));

        Response deleteUserResponse = deleteUsers(userId);

        assertThat(deleteUserResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));
    }

    //User Get Method
    public Response getUser() {
        Response response = given()
                .request(Method.GET, "/users");
        return response;
    }

    // User Put Method
    public Response putUser(String updateUserBody, int id) {
        Response response = given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(updateUserBody)
                .request(Method.PUT, "/users/" + id);
        return response;
    }

}