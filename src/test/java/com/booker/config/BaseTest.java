package com.booker.config;

import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.given;

@Epic("Restful Booker API")
public class BaseTest {

  @BeforeSuite
  public void setUp() throws InterruptedException {
    RestAssured.requestSpecification  = SpecBuilder.getRequestSpec();
    RestAssured.responseSpecification = SpecBuilder.getResponseSpec();

    // Health Check Ping
    given()
            .when()
            .get("https://restful-booker.herokuapp.com/ping")
            .then()
            .statusCode(201);

    Thread.sleep(5000);
  }
}