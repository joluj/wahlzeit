package org.joluj.model.holiday;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HolidayTypeTest {

  @Test
  public void testReadFrom() throws SQLException {
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    Mockito.when(resultSet.getString(HolidayType.SQL_KEY)).thenReturn("Urlaub 1789");

    var type = HolidayType.ReadFrom(resultSet);
    assertEquals(type.getType(), "Urlaub 1789");
  }

  @Test
  public void testWriteOn() throws SQLException {
    ResultSet input = Mockito.mock(ResultSet.class);
    Mockito.when(input.getString(HolidayType.SQL_KEY)).thenReturn("Urlaub 1789");

    var type = HolidayType.ReadFrom(input);
    assertNotNull(type);

    ResultSet output = Mockito.mock(ResultSet.class);
    type.writeOn(output);

    Mockito.verify(output, Mockito.times(1)).updateString(HolidayType.SQL_KEY, "Urlaub 1789");
  }
}
