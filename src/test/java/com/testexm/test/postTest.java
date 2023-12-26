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

    // Post Get Method
    public Response getPost() {
        Response response = given()
                .request(Method.GET, "/posts");
        return response;
    }



}
