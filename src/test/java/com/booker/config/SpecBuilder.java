package com.booker.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.HeaderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

  private static final String BASE_URL = "https://restful-booker.herokuapp.com";

  public static RequestSpecification getRequestSpec() {
    return new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setContentType(ContentType.JSON)
//            // Force ONLY application/json — overrides REST Assured's multi-value default
//            .setConfig(RestAssuredConfig.config().headerConfig(
//                    HeaderConfig.headerConfig().overwriteHeadersWithName("Accept")
//            ))
            .addHeader("Accept", "application/json")
            .addFilter(new AllureRestAssured())
            .log(LogDetail.ALL)
            .build();
  }

  public static ResponseSpecification getResponseSpec() {
    return new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();
  }
}