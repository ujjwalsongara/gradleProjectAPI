package com.testexm;

import com.testexm.utils.ApplicationProperties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


import static io.restassured.RestAssured.*;

public class BaseClass {

      public  String accessToken = ApplicationProperties.INSTANCE.getToken();
     public  Logger logger = LoggerFactory.getLogger(BaseClass.class);

        @BeforeSuite
        public void beforeSuite() {
        RestAssured.baseURI = ApplicationProperties.INSTANCE.getUrl();
        logger.info("Before Suite Start");
         }
        //User Post Method
        public Response postUsers (String userBody){
        Response response = given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(userBody)
                .request(Method.POST, "/users");
        return response;
    }
        //  User Delete Method
        public Response deleteUsers ( int id){
        Response response = given()
                .header("Authorization", accessToken)
                .request(Method.DELETE, "/users/" + id);
        return response;
    }
        //Post Posts Method
        public Response postPost (String postPostBody,int id){
        Response response = given()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(postPostBody)
                .when()

                .request(Method.POST, "/users/" + id + "/posts");
        return response;
    }
        //Post Delete Method
        public Response deletePosts ( int id){
        Response response = given()
                .header("Authorization", accessToken)
                .request(Method.DELETE, "/posts/" + id);
        return response;
    }
        //Delete Comment Method
        public Response deleteComment ( int id){
        Response response = given()
                .header("Authorization", accessToken)
                .request(Method.DELETE, "/comments/" + id);
        return response;
    }

        @AfterSuite
        public void afterSuite () {
        logger.info("After Suite  completed");
    }

    }
