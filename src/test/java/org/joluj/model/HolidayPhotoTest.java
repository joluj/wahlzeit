package org.joluj.model;

import org.joluj.model.exceptions.SqlParseException;
import org.junit.Test;
import org.mockito.Mockito;
import org.wahlzeit.model.PhotoStatus;
import org.wahlzeit.model.Tags;
import org.wahlzeit.services.EmailAddress;
import org.wahlzeit.services.Language;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;


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
    assertEquals(photo.getCountry(), "China");
  }

  @Test
  public void writeTo() throws SQLException, MalformedURLException {
    // prepare
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    var photo = new HolidayPhoto();
    photo.setCountry("USA");
    // other fields without null checks in {@link Photo#writeOn}
    photo.setOwnerEmailAddress(EmailAddress.EMPTY);
    photo.setOwnerHomePage(new URL("https://example.com"));
    photo.setOwnerLanguage(Language.JAPANESE);
    photo.setTags(Tags.EMPTY_TAGS);
    photo.setStatus(PhotoStatus.DELETED);

    // real act
    photo.writeOn(resultSet);

    // assert
    Mockito.verify(resultSet, Mockito.times(1)).updateString("country", "USA");
  }

}
