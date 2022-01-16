package org.joluj.model.holiday;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class HolidayManagerTest {
  private final HolidayManager manager = HolidayManager.getInstance();

  @Test
  public void testHoliday_SameObject() throws SQLException {
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.COUNTRY)).thenReturn("Republic of the Congo");
    Mockito.when(resultSet.getString(HolidayType.SQL_KEY)).thenReturn("Sommerurlaub 2030");

    var a = manager.getOrCreateHoliday(resultSet);
    var b = manager.getOrCreateHoliday(resultSet);

    assertNotNull(a);
    assertNotNull(b);

    assertSame(a, b);
    assertSame(a.getType(), b.getType());
  }

  @Test
  public void testHoliday_NotSameObject() throws SQLException {
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.COUNTRY)).thenReturn("Republic of the Congo");

    var a = manager.getOrCreateHoliday(resultSet);
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.COUNTRY)).thenReturn("Democratic Republic of the Congo");
    var b = manager.getOrCreateHoliday(resultSet);

    assertNotSame(a, b);
    assertNotEquals(a, b);
  }

  @Test
  public void testHolidayType_NotSameObject() {
    var a = manager.getOrCreateHolidayType("A");
    var b = manager.getOrCreateHolidayType("B");

    assertNotSame(a, b);
    assertNotEquals(a, b);
  }

  @Test
  public void testHolidayType_SameObject() {
    var a = manager.getOrCreateHolidayType("A");
    var b = manager.getOrCreateHolidayType("A");

    assertSame(a, b);
  }

  @Test
  public void testHolidaySubType_SameObject() {
    var a = manager.getOrCreateHolidayType("A");

    var b1 = manager.getOrCreateHolidaySubType(a, "B");
    var b2 = manager.getOrCreateHolidaySubType(a, "B");

    assertTrue(b1.isSubtypeOf(a));
    assertSame(b1, b2);
  }
}
