package com.booker.tests;

import com.booker.config.BaseTest;
import com.booker.models.TokenRequest;
import com.booker.utils.AuthHelper;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@Feature("Authentication")
public class AuthTest extends BaseTest {

  @Test
  @Story("Valid credentials return a token")
  public void tokenIssuance() {
    String token = AuthHelper.getToken();

    assertThat(token)
            .isNotNull()
            .isNotEmpty()
            .hasSizeGreaterThan(10);
  }

  @Test
  @Story("Invalid credentials return no token")
  public void invalidCredentials() {
    given()
            .body(new TokenRequest("wronguser", "wrongpass"))
            .when()
            .post("/auth")
            .then()
            .statusCode(200)
            .body("reason", equalTo("Bad credentials"));
  }

  @Test
  @Story("Token is reused across calls — not re-issued each time")
  public void tokenIsCached() {
    String first  = AuthHelper.getToken();
    String second = AuthHelper.getToken();
    assertThat(first).isEqualTo(second);
  }
}