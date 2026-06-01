package com.booker.tests;

import com.booker.config.BaseTest;
import com.booker.models.Booking;
import com.booker.utils.AuthHelper;
import com.booker.utils.BookingDataFactory;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Feature("Booking CRUD Lifecycle")
public class BookingCrudTest extends BaseTest {

  private static int bookingId;
  private static Booking originalBooking;

  @Test(priority = 1)
  @Story("Create a new booking")
  public void createBooking() {
    originalBooking = BookingDataFactory.createDefaultBooking();

    bookingId = given()
            .body(originalBooking)
            .when()
            .post("/booking")
            .then()
            .statusCode(200)
            .body("booking.firstname",  equalTo(originalBooking.getFirstname()))
            .body("booking.lastname",   equalTo(originalBooking.getLastname()))
            .body("booking.totalprice", equalTo(originalBooking.getTotalprice()))
            .extract()
            .path("bookingid");

    assertThat(bookingId).isGreaterThan(0);
  }

  @Test(priority = 2, dependsOnMethods = "createBooking")
  @Story("Read booking and verify data persistence")
  public void getBookingAndVerifyPersistence() {
    Booking fetched = given()
            .when()
            .get("/booking/" + bookingId)
            .then()
            .statusCode(200)
            .extract()
            .as(Booking.class);

    // Persistence check — every field verified
    assertThat(fetched.getFirstname())
            .isEqualTo(originalBooking.getFirstname());
    assertThat(fetched.getLastname())
            .isEqualTo(originalBooking.getLastname());
    assertThat(fetched.getTotalprice())
            .isEqualTo(originalBooking.getTotalprice());
    assertThat(fetched.isDepositpaid())
            .isEqualTo(originalBooking.isDepositpaid());
    assertThat(fetched.getAdditionalneeds())
            .isEqualTo(originalBooking.getAdditionalneeds());
    assertThat(fetched.getBookingdates().getCheckin())
            .isEqualTo(originalBooking.getBookingdates().getCheckin());
    assertThat(fetched.getBookingdates().getCheckout())
            .isEqualTo(originalBooking.getBookingdates().getCheckout());
  }

  @Test(priority = 3, dependsOnMethods = "getBookingAndVerifyPersistence")
  @Story("Update booking with new data")
  public void updateBooking() {
    Booking updated = BookingDataFactory.createUpdatedBooking();

    // Fetch token first — before making the PUT request
    String token = AuthHelper.getToken();

    given()
            .header("Cookie", "token=" + token)
            .body(updated)
            .when()
            .put("/booking/" + bookingId)
            .then()
            .statusCode(200)
            .body("firstname",  equalTo(updated.getFirstname()))
            .body("lastname",   equalTo(updated.getLastname()))
            .body("totalprice", equalTo(updated.getTotalprice()));

    originalBooking = updated;
  }

  @Test(priority = 4, dependsOnMethods = "updateBooking")
  @Story("Delete booking and verify it no longer exists")
  public void deleteBookingAndVerifyGone() {

    // Fetch token first — before making the DELETE request
    String token = AuthHelper.getToken();

    given()
            .header("Cookie", "token=" + token)
            .when()
            .delete("/booking/" + bookingId)
            .then()
            .statusCode(201);

    // Persistence check — booking must be gone
    given()
            .when()
            .get("/booking/" + bookingId)
            .then()
            .statusCode(404);
  }
}