package com.booker.utils;

import com.booker.models.TokenRequest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class AuthHelper {

  private static String token;

  public static String getToken() {
    if (token == null) {
      token = given()
              .contentType(ContentType.JSON)
              .accept("application/json")
              .body(new TokenRequest("admin", "password123"))
              .when()
              .post("https://restful-booker.herokuapp.com/auth")
              .then()
              .statusCode(200)
              .extract()
              .path("token");
    }
    return token;
  }
}