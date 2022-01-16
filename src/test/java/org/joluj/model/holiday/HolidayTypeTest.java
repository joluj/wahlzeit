package org.joluj.model.holiday;

import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

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

  @Test
  public void testEqualsAndHashCode() throws SQLException {
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    Mockito.when(resultSet.getString(HolidayType.SQL_KEY)).thenReturn("Urlaub 1789");

    var a = HolidayType.ReadFrom(resultSet);
    var b = HolidayType.ReadFrom(resultSet);

    assertNotNull(a);
    assertNotNull(b);

    assertNotSame(a, b); // Same only if created with the HolidayManager
    assertEquals(a.hashCode(), b.hashCode());
    assertEquals(a, b);
  }

  @Test
  public void testSubtype() {
    var a = new HolidayType("A");
    var b = new HolidayType(a, "B");

    assertTrue(a.isSubtypeOf(b));
    assertFalse(b.isSubtypeOf(a));
  }

  @Test
  public void testSubtype_Self() {
    var a = new HolidayType("A");

    assertTrue(a.isSubtypeOf(a));
  }

  @Test
  public void testNotSubtype() {
    var a = new HolidayType("A");
    var b = new HolidayType("B");

    assertFalse(a.isSubtypeOf(b));
    assertFalse(b.isSubtypeOf(a));
  }

}
