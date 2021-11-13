package org.joluj.model;

import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class HolidayPhotoTest {

  @Test
  public void isDirtyCountry() {
    var photo = new HolidayPhoto();

    photo.setCountry("North Korea");
    assertTrue(photo.isDirty());
  }

  @Test
  public void readFrom() throws SQLException {

    ResultSet resultSet = Mockito.mock(ResultSet.class);
    Mockito.when(resultSet.getString("owner_email_address")).thenReturn("test@example.com");
    Mockito.when(resultSet.getString("owner_home_page")).thenReturn("https://example.com");
    Mockito.when(resultSet.getString("country")).thenReturn("China");

    var photo = new HolidayPhoto(resultSet);

    assertFalse(photo.isDirty());
    Mockito.verify(resultSet, Mockito.times(9));
  }

}
