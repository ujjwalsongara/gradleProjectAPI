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
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class postTest extends BaseClass {

    @Test
    public void getPostTest() throws JSONException {

        Response postResponse = getPost();

        assertThat(postResponse.getStatusCode(), is(HttpStatus.SC_OK));

        JSONArray getPost = new JSONArray(postResponse.asString());

        assertThat(getPost.length(), greaterThan(0));

        JSONObject jsonObjectPost = getPost.getJSONObject(0);

        assertThat(jsonObjectPost.getInt("id"), is(notNullValue()));
        assertThat(jsonObjectPost.getInt("user_id"), is(notNullValue()));
        assertThat(jsonObjectPost.getString("title"), is(notNullValue()));
        assertThat(jsonObjectPost.getString("body"), is(notNullValue()));
    }
    @Test
    public void postPostsTest() throws JSONException {
        Faker faker = new Faker();
        String userName = faker.name().name();
        String userEmail = faker.internet().emailAddress();
        String userGender = "male";
        String userStatus = "active";
        String postsTitle = "Et tam  curso certe denique tristis.";
        String postsBody = "Tenus vigor ut. Triduana praesentium qui. Ab repellendus tertius. Copiose adultus sit. Molestiae cubo voluptatum. Agnosco color creta. Circumvenio debilito thermae. Vinitor vesica animi. Accusantium aeneus velociter. Despirmatio comminor speciosus. Temeritas quo tamen. Alioqui explicabo dolorem. Maiores versus sono. Tantum texo acceptus. Omnis ademptio catena. Valde argumentum qui.";

        String userBody = "{\n " +
                "\"name\":\"" + userName + "\",\n" +
                "\"email\":\"" + userEmail + "\",\n" +
                "\"gender\":\"" + userGender + "\",\n" +
                "\"status\":\"" + userStatus + "\"\n" +
                "}";

        String postBody = " { \n" +
                "    \"title\": \"" + postsTitle + "\",\n" +
                "    \"body\":\"" + postsBody + "\"\n" +
                "}";

        Response postUserResponse = postUsers(userBody);

        assertThat(postUserResponse.getStatusCode(), is(HttpStatus.SC_CREATED));

        JSONObject userData = new JSONObject(postUserResponse.asString());

        int userId = userData.getInt("id");

        assertThat(userData.getInt("id"), is(notNullValue()));
        assertThat(userData.getString("name"), is(userName));
        assertThat(userData.getString("email"), is(userEmail));
        assertThat(userData.getString("gender"), is(userGender));
        assertThat(userData.getString("status"), is(userStatus));

        Response postResponse = postPost(postBody, userId);

        assertThat(postResponse.getStatusCode(), is(HttpStatus.SC_CREATED));

        JSONObject putData = new JSONObject(postResponse.asString());

        int postId = putData.getInt("id");

        assertThat(putData.getInt("id"), is(postId));
        assertThat(putData.getInt("user_id"), is(userId));
        assertThat(putData.getString("title"), is(postsTitle));
        assertThat(putData.getString("body"), is(postsBody));

        Response deletePostResponse = deletePosts(postId);
        assertThat(deletePostResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

        Response deleteUserResponse = deleteUsers(userId);

        assertThat(deleteUserResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

    }

    @Test
    public void putPostTest() throws JSONException {
        Faker faker = new Faker();
        String userName = faker.name().name();
        String userEmail = faker.internet().emailAddress();
        String userGender = "male";
        String userStatus = "active";
        String postsTitle = "Et tam  certe denique istis.";
        String postsBody = "Tenus vigor ut. Triduana praesentiu qui. Ab repellendus tertius. Copiose adultus sit.";
        String putPostTitle = "Et tam  certe denique istis.";
        String putPostBody = "Tenus vigor ut. Triduana praesentiu qui. Ab repellendus tertius. Copiose adultus sit.";
        String userBody = "{\n " +
                "\"name\":\"" + userName + "\",\n" +
                "\"email\":\"" + userEmail + "\",\n" +
                "\"gender\":\"" + userGender + "\",\n" +
                "\"status\":\"" + userStatus + "\"\n" +
                "}";
        String postBody = " { \n" +
                "    \"title\": \"" + postsTitle + "\",\n" +
                "    \"body\":\"" + postsBody + "\"\n" +
                "}";
        Response postUserResponse = postUsers(userBody);

        assertThat(postUserResponse.getStatusCode(), is(HttpStatus.SC_CREATED));

        JSONObject postData = new JSONObject(postUserResponse.asString());

        int userId = postData.getInt("id");

        assertThat(postData.getInt("id"), notNullValue());
        assertThat(postData.getString("name"), is(userName));
        assertThat(postData.getString("email"), is(userEmail));
        assertThat(postData.getString("gender"), is(userGender));
        assertThat(postData.getString("status"), is(userStatus));

        Response postResponse = postPost(postBody, userId);

        assertThat(postResponse.getStatusCode(), is(HttpStatus.SC_CREATED));

        JSONObject putData = new JSONObject(postResponse.asString());

        int postId = putData.getInt("id");

        assertThat(putData.getInt("id"), is(postId));
        assertThat(putData.getInt("user_id"), is(userId));
        assertThat(putData.getString("title"), is(postsTitle));
        assertThat(putData.getString("body"), is(postsBody));

        String putBody = "{\n" +
                "    \"user_id\": " + userId + ",\n" +
                "    \"title\": \"" + putPostTitle + "\",\n" +
                "    \"body\": \"" + putPostBody + "\"\n" +
                "}";
        Response putResponse = putPosts(putBody, postId);
        assertThat(putResponse.getStatusCode(), is(HttpStatus.SC_OK));

        JSONObject putPostData = new JSONObject(postResponse.asString());
        int putId = putPostData.getInt("id");
        assertThat(putPostData.getString("title"), is(putPostTitle));
        assertThat(putPostData.getString("body"), is(putPostBody));


        Response deletePostResponse = deletePosts(postId);
        assertThat(deletePostResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

        Response deleteUserResponse = deleteUsers(userId);

        assertThat(deleteUserResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

    }

    @Test
    public void deletePostTest() throws JSONException {
        Faker faker = new Faker();
        String userName = faker.name().name();
        String userEmail = faker.internet().emailAddress();
        String userGender = "male";
        String userStatus = "active";
        String postsTitle = "Et tam  certe denique istis.";
        String postsBody = "Tenus vigor ut. Triduana praesentiu qui. Ab repellendus tertius. Copiose adultus sit.";
        String userBody = "{\n " +
                "\"name\":\"" + userName + "\",\n" +
                "\"email\":\"" + userEmail + "\",\n" +
                "\"gender\":\"" + userGender + "\",\n" +
                "\"status\":\"" + userStatus + "\"\n" +
                "}";
        String postBody = " { \n" +
                "    \"title\": \"" + postsTitle + "\",\n" +
                "    \"body\":\"" + postsBody + "\"\n" +
                "}";
        Response postUserResponse = postUsers(userBody);

        assertThat(postUserResponse.getStatusCode(), is(HttpStatus.SC_CREATED));

        JSONObject postData = new JSONObject(postUserResponse.asString());

        int userId = postData.getInt("id");

        assertThat(postData.getInt("id"), notNullValue());
        assertThat(postData.getString("name"), is(userName));
        assertThat(postData.getString("email"), is(userEmail));
        assertThat(postData.getString("gender"), is(userGender));
        assertThat(postData.getString("status"), is(userStatus));

        Response postResponse = postPost(postBody, userId);

        assertThat(postResponse.getStatusCode(), is(HttpStatus.SC_CREATED));

        JSONObject putData = new JSONObject(postResponse.asString());

        int postId = putData.getInt("id");

        assertThat(putData.getInt("id"), is(postId));
        assertThat(putData.getInt("user_id"), is(userId));
        assertThat(putData.getString("title"), is(postsTitle));
        assertThat(putData.getString("body"), is(postsBody));

        Response deletePostResponse = deletePosts(postId);

        assertThat(deletePostResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

        Response deleteUserResponse = deleteUsers(userId);

        assertThat(deleteUserResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

    }

    // Post Get Method
    public Response getPost() {
        Response response = given()
                .request(Method.GET, "/posts");
        return response;
    }

    // Put Post Method
    public Response putPosts(String body, int id) {
        Response response = given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(body)
                .request(Method.PUT, "/posts/" + id);

        return response;
    }
}
