package com.booker.utils;

import com.booker.models.Booking;
import com.booker.models.BookingDates;

public class BookingDataFactory {

  public static Booking createDefaultBooking() {
    BookingDates dates = new BookingDates();
    dates.setCheckin("2025-06-01");
    dates.setCheckout("2025-06-07");

    Booking booking = new Booking();
    booking.setFirstname("James");
    booking.setLastname("Brown");
    booking.setTotalprice(150);
    booking.setDepositpaid(true);
    booking.setBookingdates(dates);
    booking.setAdditionalneeds("Breakfast");

    return booking;
  }

  public static Booking createUpdatedBooking() {
    BookingDates dates = new BookingDates();
    dates.setCheckin("2025-07-01");
    dates.setCheckout("2025-07-10");

    Booking booking = new Booking();
    booking.setFirstname("UpdatedJames");
    booking.setLastname("UpdatedBrown");
    booking.setTotalprice(300);
    booking.setDepositpaid(false);
    booking.setBookingdates(dates);
    booking.setAdditionalneeds("Dinner");

    return booking;
  }
}