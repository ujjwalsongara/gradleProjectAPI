package com.testexm.test;

import com.testexm.BaseClass;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class ToDoTest extends BaseClass {
    private static Logger logger= LoggerFactory.getLogger(BaseClass.class);
    @Test
    public void getToDoTest() throws JSONException {
        Response toDoResponse = getToDo();

        assertThat(toDoResponse.getStatusCode(), is(HttpStatus.SC_OK));

        JSONArray toDoGetArray = new JSONArray(toDoResponse.asString());

        assertThat(toDoGetArray.length(), greaterThan(0));

        JSONObject toDoData = toDoGetArray.getJSONObject(0);

        assertThat(toDoData.getInt("id"), is(notNullValue()));
        assertThat(toDoData.getInt("user_id"), is(notNullValue()));
        assertThat(toDoData.getString("title"), is(notNullValue()));
        assertThat(toDoData.getString("due_on"), is(notNullValue()));
        assertThat(toDoData.getString("status"), is(notNullValue()));
    }

    @Test
    public void postToDoTest() throws JSONException {
        Faker faker = new Faker();
        String userName = faker.name().name();
        String userEmail = faker.internet().emailAddress();
        String userGender = "male";
        String userStatus = "active";
        String postTitle = "Capitulus adeo illo aurum consuasor.";
        String postDue_on = "2023-07-07T00:00:00.000+05:30";
        String postStatus = "completed";
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
        String postBody = "{\n" +
                "        \n" +
                "        \"user_id\": " + userId + ",\n" +
                "        \"title\": \"" + postTitle + "\",\n" +
                "        \"due_on\": \"" + postDue_on + "\",\n" +
                "        \"status\": \"" + postStatus + "\"\n" +
                "    }";
        Response toDoPostResponse = postToDo(postBody);
        assertThat(toDoPostResponse.getStatusCode(), is(HttpStatus.SC_CREATED));

        JSONObject postToDoData = new JSONObject(toDoPostResponse.asString());

        int toDoId = postToDoData.getInt("id");

        assertThat(postToDoData.getInt("user_id"), is(userId));
        assertThat(postToDoData.getString("title"), is(postTitle));
        assertThat(postToDoData.getString("due_on"), is(postDue_on));
        assertThat(postToDoData.getString("status"), is(postStatus));

        Response toDoDeleteResponse = deleteToDo(toDoId);

        assertThat(toDoDeleteResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

        Response deleteUserResponse = deleteUsers(userId);

        assertThat(deleteUserResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

    }

    @Test
    public void putToDoTest() throws JSONException {
        Faker faker = new Faker();
        String userName = faker.name().name();
        String userEmail = faker.internet().emailAddress();
        String userGender = "male";
        String userStatus = "active";
        String postTitle = "Capitulus adeo illo aurum consuasor.";
        String postDue_on = "2023-07-07T00:00:00.000+05:30";
        String postStatus = "completed";
        String putTitle = "Capitulus adeo illo aurum consuasor.";
        String putDue_on = "2023-07-07T00:00:00.000+05:30";
        String putStatus = "completed";

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
        String postBody = "{\n" +
                "        \n" +
                "        \"user_id\": " + userId + ",\n" +
                "        \"title\": \"" + postTitle + "\",\n" +
                "        \"due_on\": \"" + postDue_on + "\",\n" +
                "        \"status\": \"" + postStatus + "\"\n" +
                "    }";
        Response toDoPostResponse = postToDo(postBody);
        assertThat(toDoPostResponse.getStatusCode(), is(HttpStatus.SC_CREATED));

        JSONObject postToDoData = new JSONObject(toDoPostResponse.asString());

        int toDoId = postToDoData.getInt("id");

        assertThat(postToDoData.getInt("user_id"), is(userId));
        assertThat(postToDoData.getString("title"), is(postTitle));
        assertThat(postToDoData.getString("due_on"), is(postDue_on));
        assertThat(postToDoData.getString("status"), is(postStatus));

        String putBody = "{\n" +
                "        \n" +
                "        \"user_id\": " + userId + ",\n" +
                "        \"title\": \"" + putTitle + "\",\n" +
                "        \"due_on\": \"" + putDue_on + "\",\n" +
                "        \"status\": \"" + putStatus + "\"\n" +
                "    }";
        Response toDoPutResponse = putToDo(putBody, toDoId);

        assertThat(toDoPutResponse.getStatusCode(), is(HttpStatus.SC_OK));

        JSONObject putToDoData = new JSONObject(toDoPutResponse.asString());

        logger.info(toDoPutResponse.asString());

        int toDoPutId = putToDoData.getInt("id");

        assertThat(putToDoData.getInt("user_id"), is(userId));
        assertThat(putToDoData.getString("title"), is(postTitle));
        assertThat(putToDoData.getString("due_on"), is(postDue_on));
        assertThat(putToDoData.getString("status"), is(postStatus));

        Response toDoPutDeleteResponse = deleteToDo(toDoPutId);

        assertThat(toDoPutDeleteResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

        Response deleteUserResponse = deleteUsers(userId);

        assertThat(deleteUserResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

    }

    @Test
    public void deleteToDoTest() throws JSONException {
        Faker faker = new Faker();
        String userName = faker.name().name();
        String userEmail = faker.internet().emailAddress();
        String userGender = "male";
        String userStatus = "active";
        String postTitle = "Capitulus adeo illo aurum consuasor.";
        String postDue_on = "2023-07-07T00:00:00.000+05:30";
        String postStatus = "completed";
        String userBody = "{\n " +
                "\"name\":\"" + userName + "\",\n" +
                "\"email\":\"" + userEmail + "\",\n" +
                "\"gender\":\"" + userGender + "\",\n" +
                "\"status\":\"" + userStatus + "\"\n" +
                "}";
        Response postUserResponse = postUsers(userBody);

        assertThat(postUserResponse.getStatusCode(), is(HttpStatus.SC_CREATED));

        JSONObject postData = new JSONObject(postUserResponse.asString());

        int userId = postData.getInt("id");

        logger.info(postUserResponse.asString());

        assertThat(postData.getInt("id"), notNullValue());
        assertThat(postData.getString("name"), is(userName));
        assertThat(postData.getString("email"), is(userEmail));
        assertThat(postData.getString("gender"), is(userGender));
        assertThat(postData.getString("status"), is(userStatus));
        String postBody = "{\n" +
                "        \n" +
                "        \"user_id\": " + userId + ",\n" +
                "        \"title\": \"" + postTitle + "\",\n" +
                "        \"due_on\": \"" + postDue_on + "\",\n" +
                "        \"status\": \"" + postStatus + "\"\n" +
                "    }";
        Response toDoPostResponse = postToDo(postBody);
        assertThat(toDoPostResponse.getStatusCode(), is(HttpStatus.SC_CREATED));

        JSONObject postToDoData = new JSONObject(toDoPostResponse.asString());

        int toDoId = postToDoData.getInt("id");

        assertThat(postToDoData.getInt("user_id"), is(userId));
        assertThat(postToDoData.getString("title"), is(postTitle));
        assertThat(postToDoData.getString("due_on"), is(postDue_on));
        assertThat(postToDoData.getString("status"), is(postStatus));

        Response toDoDeleteResponse = deleteToDo(toDoId);

        assertThat(toDoDeleteResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

        Response deleteUserResponse = deleteUsers(userId);

        assertThat(deleteUserResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));


    }

    //Get ToDos Method
    public Response getToDo() {
        Response response = given()
                .request(Method.GET, "/todos");
        return response;
    }

    //Post ToDOs Method
    public Response postToDo(String body) {
        Response response = given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .request(Method.POST, "/todos");
        return response;
    }

    //Put ToDos Method
    public Response putToDo(String body, int id) {
        Response response = given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .request(Method.PUT, "/todos/" + id);
        return response;
    }
    //Delete ToDos Method
    public Response deleteToDo(int id) {
        Response response = given()
                .header("Authorization", accessToken)
                .request(Method.DELETE, "/todos/" + id);
        return response;
    }
}