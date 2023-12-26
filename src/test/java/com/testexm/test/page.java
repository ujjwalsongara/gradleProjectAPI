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


public class page extends BaseClass{
    @Test
        public void getPage() throws JSONException {
            Response commentResponse = getComment();

            assertThat(commentResponse.getStatusCode(), is(HttpStatus.SC_OK));

            JSONArray getCommentData = new JSONArray(commentResponse.asString());

            assertThat(getCommentData.length(), greaterThan(0));

            JSONObject jsonObjectComment = getCommentData.getJSONObject(0);

            assertThat(jsonObjectComment.getInt("id"), is(notNullValue()));
            assertThat(jsonObjectComment.getInt("post_id"), is(notNullValue()));
            assertThat(jsonObjectComment.getString("name"), is(notNullValue()));
            assertThat(jsonObjectComment.getString("body"), is(notNullValue()));
            assertThat(jsonObjectComment.getString("email"), is(notNullValue()));
        }

        @Test
        public void postPage() throws JSONException {
            Faker faker = new Faker();
            String userName = faker.name().name();
            String userEmail = faker.internet().emailAddress();
            String userGender = "male";
            String userStatus = "active";
            String postsTitle = "Et tam  curso certe denique tristis.";
            String postsBody = "Tenus vigor ut. Triduana praesentium qui. Ab repellendus tertius. Copiose adultus sit. Molestiae cubo voluptatum. Agnosco color creta. Circumvenio debilito thermae. Vinitor vesica animi. Accusantium aeneus velociter. Despirmatio comminor speciosus. Temeritas quo tamen. Alioqui explicabo dolorem. Maiores versus sono. Tantum texo acceptus. Omnis ademptio catena. Valde argumentum qui.";
            String postCommentName = faker.name().name();
            String postCommentEmail = faker.internet().emailAddress();
            String postCommentPostsBody = "Provident eos voluptas. Iusto accusamus assumenda. Sint vero ipsum.";
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

            System.out.println(postUserResponse.asString());

            assertThat(postUserResponse.getStatusCode(), is(HttpStatus.SC_CREATED));

            JSONObject userData = new JSONObject(postUserResponse.asString());

            int userId = userData.getInt("id");

            assertThat(userData.getInt("id"), is(notNullValue()));
            assertThat(userData.getString("name"), is(userName));
            assertThat(userData.getString("email"), is(userEmail));
            assertThat(userData.getString("gender"), is(userGender));
            assertThat(userData.getString("status"), is(userStatus));

            Response postResponse = postPost(postBody ,userId);

            assertThat(postResponse.getStatusCode(), is(HttpStatus.SC_CREATED));

            JSONObject putData = new JSONObject(postResponse.asString());

            int postId = putData.getInt("id");

            assertThat(putData.getInt("id"), is(postId));
            assertThat(putData.getInt("user_id"), is(userId));
            assertThat(putData.getString("title"), is(postsTitle));
            assertThat(putData.getString("body"), is(postsBody));

            String postCommentBody = " {\n" +
                    "        \"post_id\": " + postId + ",\n" +
                    "        \"name\": \"" + postCommentName + "\",\n" +
                    "        \"email\": \"" + postCommentEmail + "\",\n" +
                    "        \"body\": \"" + postCommentPostsBody + "\"\n" +
                    "    }";
            Response responsePostComment = postComment(postCommentBody, postId);

            assertThat(responsePostComment.getStatusCode(), is(HttpStatus.SC_CREATED));

            JSONObject commentData = new JSONObject(responsePostComment.asString());

            int commentId = commentData.getInt("id");

            assertThat(commentData.getInt("id"), is(commentId));
            assertThat(commentData.getInt("post_id"), is(postId));
            assertThat(commentData.getString("name"), is(postCommentName));
            assertThat(commentData.getString("email"), is(postCommentEmail));
            assertThat(commentData.getString("body"), is(postCommentPostsBody));

            Response deleteCommentResponse = deleteComment(commentId);

            assertThat(deleteCommentResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

            Response deletePostResponse = deletePosts(postId);

            assertThat(deletePostResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

            Response deleteUserResponse = deleteUsers(userId);

            assertThat(deleteUserResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

        }

        @Test
        public void putCommentTest() throws JSONException {
            Faker faker = new Faker();
            String userName = faker.name().name();
            String userEmail = faker.internet().emailAddress();
            String userGender = "male";
            String userStatus = "active";
            String postsTitle = "Et tam  curso certe denique tristis.";
            String postsBody = "Tenus vigor ut. Triduana praesentium qui. Ab repellendus tertius. Copiose adultus sit. Molestiae cubo voluptatum. Agnosco color creta. Circumvenio debilito thermae. Vinitor vesica animi. Accusantium aeneus velociter. Despirmatio comminor speciosus. Temeritas quo tamen. Alioqui explicabo dolorem. Maiores versus sono. Tantum texo acceptus. Omnis ademptio catena. Valde argumentum qui.";
            String postCommentName = faker.name().name();
            String postCommentEmail = faker.internet().emailAddress();
            String postCommentPostsBody = "Provident eos voluptas. Iusto accusamus assumenda. Sint vero ipsum.";
            String putCommentName = faker.name().name();
            String putCommentEmail = faker.internet().emailAddress();
            String putCommentPutBody = "Provident eos voluptas. Iusto accusamus assumenda. Sint vero ipsu";
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

            String postCommentBody = " {\n" +
                    "        \"post_id\": " + postId + ",\n" +
                    "        \"name\": \"" + postCommentName + "\",\n" +
                    "        \"email\": \"" + postCommentEmail + "\",\n" +
                    "        \"body\": \"" + postCommentPostsBody + "\"\n" +
                    "    }";
            Response responsePostComment = postComment(postCommentBody, postId);

            assertThat(responsePostComment.getStatusCode(), is(HttpStatus.SC_CREATED));

            JSONObject commentData = new JSONObject(responsePostComment.asString());

            int commentId = commentData.getInt("id");
            assertThat(commentData.getInt("id"), is(commentId));
            assertThat(commentData.getInt("post_id"), is(postId));
            assertThat(commentData.getString("name"), is(postCommentName));
            assertThat(commentData.getString("email"), is(postCommentEmail));
            assertThat(commentData.getString("body"), is(postCommentPostsBody));

            String putCommentBody = " {\n" +
                    "        \"post_id\": " + postId + ",\n" +
                    "        \"name\": \"" + putCommentName + "\",\n" +
                    "        \"email\": \"" + putCommentEmail + "\",\n" +
                    "        \"body\": \"" + putCommentPutBody + "\"\n" +
                    "    }";
            Response putCommentResponse = putComment(putCommentBody, commentId);

            assertThat(putCommentResponse.getStatusCode(), is(HttpStatus.SC_OK));

            JSONObject commentPutData = new JSONObject(putCommentResponse.asString());

            int idCommentPut = commentPutData.getInt("id");
            assertThat(commentPutData.getInt("id"), is(idCommentPut));
            assertThat(commentPutData.getInt("post_id"), is(postId));
            assertThat(commentPutData.getString("name"), is(putCommentName));
            assertThat(commentPutData.getString("email"), is(putCommentEmail));
            assertThat(commentPutData.getString("body"), is(putCommentPutBody));

            Response deletePutCommentResponse = deleteComment(idCommentPut);

            assertThat(deletePutCommentResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));


            Response deletePostResponse = deletePosts(postId);

            assertThat(deletePostResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

            Response deleteUserResponse = deleteUsers(userId);

            assertThat(deleteUserResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

        }

        @Test
        public void deleteCommentTest() throws JSONException {
            Faker faker = new Faker();
            String userName = faker.name().name();
            String userEmail = faker.internet().emailAddress();
            String userGender = "male";
            String userStatus = "active";
            String postsTitle = "Et tam  curso certe denique tristis.";
            String postsBody = "Tenus vigor ut. Triduana praesentium qui. Ab repellendus tertius. Copiose adultus sit. Molestiae cubo voluptatum. Agnosco color creta. Circumvenio debilito thermae. Vinitor vesica animi. Accusantium aeneus velociter. Despirmatio comminor speciosus. Temeritas quo tamen. Alioqui explicabo dolorem. Maiores versus sono. Tantum texo acceptus. Omnis ademptio catena. Valde argumentum qui.";
            String postCommentName = faker.name().name();
            String postCommentEmail = faker.internet().emailAddress();
            String postCommentPostsBody = "Provident eos voluptas. Iusto accusamus assumenda. Sint vero ipsum.";
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

            String postCommentBody = " {\n" +
                    "        \"post_id\": " + postId + ",\n" +
                    "        \"name\": \"" + postCommentName + "\",\n" +
                    "        \"email\": \"" + postCommentEmail + "\",\n" +
                    "        \"body\": \"" + postCommentPostsBody + "\"\n" +
                    "    }";
            Response responsePostComment = postComment(postCommentBody, postId);

            assertThat(responsePostComment.getStatusCode(), is(HttpStatus.SC_CREATED));

            JSONObject commentData = new JSONObject(responsePostComment.asString());

            int commentId = commentData.getInt("id");
            assertThat(commentData.getInt("id"), is(commentId));
            assertThat(commentData.getInt("post_id"), is(postId));
            assertThat(commentData.getString("name"), is(postCommentName));
            assertThat(commentData.getString("email"), is(postCommentEmail));
            assertThat(commentData.getString("body"), is(postCommentPostsBody));

            JSONObject commentDeleteData = new JSONObject(responsePostComment.asString());

            int commentPostId = commentDeleteData.getInt("id");


            Response deleteCommentResponse = deleteComment(commentId);

            assertThat(deleteCommentResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

            Response deletePostResponse = deletePosts(postId);

            assertThat(deletePostResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

            Response deleteUserResponse = deleteUsers(userId);

            assertThat(deleteUserResponse.getStatusCode(), is(HttpStatus.SC_NO_CONTENT));

        }

        // Get Comment Method
        public Response getComment() {
            Response response = given()
                    .request(Method.GET, "/comments");
            return response;
        }

        //Post Comment Method
        public Response postComment(String body, int id) {
            Response response = given()
                    .header("Authorization", accessToken)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .request(Method.POST, "/posts/" + id + "/comments");
            return response;
        }

        // Put Comment Method
        public Response putComment(String body, int id) {
            Response response = given()
                    .header("Authorization", accessToken)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .request(Method.PUT, "/comments/" + id);
            return response;
        }
    }

