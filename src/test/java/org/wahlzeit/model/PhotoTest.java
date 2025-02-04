package org.wahlzeit.model;

import org.joluj.model.CartesianCoordinate;
import org.joluj.model.Coordinate;
import org.joluj.model.Location;
import org.joluj.model.exceptions.SqlParseException;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class PhotoTest {
  @Test
  public void location() {
    var location = new Location(CartesianCoordinate.FromXYZ(1, 2.3, 4.5));
    var photo = new Photo();

    assertNull(photo.getLocation());
    photo.setLocation(location);
    assertEquals(photo.getLocation(), location);

    assertTrue(photo.isDirty());
  }

  @Test(expected = SqlParseException.class)
  public void readFromThrowsSqlParseException() throws SQLException {

    ResultSet resultSet = Mockito.mock(ResultSet.class);
    Mockito.when(resultSet.getString("owner_email_address")).thenReturn("test@example.com");
    Mockito.when(resultSet.getString("owner_home_page")).thenReturn("https://example.com");
    Mockito.when(resultSet.getString("country")).thenReturn("China");
    Mockito.when(resultSet.getString("location")).thenReturn("cannot-parse");

    new Photo(resultSet);
  }

  @Test
  public void testLocationDirtyCheckOnEqualObject() throws SQLException {
    // Arrange
    // Create Photo with a location
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    Mockito.when(resultSet.getString("owner_email_address")).thenReturn("test@example.com");
    Mockito.when(resultSet.getString("owner_home_page")).thenReturn("https://example.com");
    Mockito.when(resultSet.getString("country")).thenReturn("China");
    Mockito.when(resultSet.getString("location")).thenReturn("cartesian|1|2.3|4.5");
    var photo = new Photo(resultSet);
    assertFalse(photo.isDirty()); // asserts that the setup is correct before the actual act

    // Act 1
    // Updating the location to the same location should not make it dirty
    photo.setLocation(new Location(CartesianCoordinate.FromXYZ(1, 2.3, 4.5)));
    // Assert 1
    assertFalse(photo.isDirty());

    // Act 2
    // Updating the location to a different location should make it dirty
    photo.setLocation(new Location(CartesianCoordinate.FromXYZ(100, 100, 100)));
    // Assert 2
    assertTrue(photo.isDirty());
  }
}
