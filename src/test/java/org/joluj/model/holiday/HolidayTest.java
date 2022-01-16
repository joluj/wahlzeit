package org.joluj.model.holiday;

import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class HolidayTest {

  @Test
  public void testAttributesSet() throws SQLException {
    var calender = new GregorianCalendar();

    ResultSet resultSet = Mockito.mock(ResultSet.class);
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.COUNTRY)).thenReturn("East Germany");
    Mockito.when(resultSet.getString(HolidayType.SQL_KEY)).thenReturn("Urlaub 1970-71");
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.START_DATE)).thenReturn("1970-06-15");
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.END_DATE)).thenReturn("1971-07-01");

    var holiday = Holiday.ReadFrom(resultSet);

    assertNotNull(holiday);
    assertEquals(holiday.getCountry(), "East Germany");

    var startDate = holiday.getStartDate();
    assertNotNull(startDate);
    calender.setTime(startDate);
    assertEquals(calender.get(Calendar.YEAR), 1970);
    assertEquals(calender.get(Calendar.MONTH), 5); // zero-indexed
    assertEquals(calender.get(Calendar.DAY_OF_MONTH), 15);

    var endDate = holiday.getEndDate();
    assertNotNull(endDate);
    calender.setTime(endDate);
    assertEquals(calender.get(Calendar.YEAR), 1971);
    assertEquals(calender.get(Calendar.MONTH), 6); // zero-indexed
    assertEquals(calender.get(Calendar.DAY_OF_MONTH), 1);
  }


  @Test
  public void testReadFromEmptyResultSetReturnsNull() throws SQLException {
    ResultSet resultSet = Mockito.mock(ResultSet.class);

    var holiday = Holiday.ReadFrom(resultSet);
    assertNull(holiday);
  }

  @Test
  public void testAttributesAllowEmptyDate() throws SQLException {
    var calender = new GregorianCalendar();

    ResultSet resultSet = Mockito.mock(ResultSet.class);
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.COUNTRY)).thenReturn("East Germany");
    Mockito.when(resultSet.getString(HolidayType.SQL_KEY)).thenReturn("Urlaub 1970-71");
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.START_DATE)).thenReturn("1970-06-15");

    var holiday = Holiday.ReadFrom(resultSet);

    assertNotNull(holiday);
    assertEquals(holiday.getCountry(), "East Germany");

    var startDate = holiday.getStartDate();
    assertNotNull(startDate);
    calender.setTime(startDate);
    assertEquals(calender.get(Calendar.YEAR), 1970);
    assertEquals(calender.get(Calendar.MONTH), 5); // zero-indexed
    assertEquals(calender.get(Calendar.DAY_OF_MONTH), 15);

    var endDate = holiday.getEndDate();
    assertNull(endDate);
  }

  @Test(expected = SQLException.class)
  public void testAttributesDatesWrongOrder() throws SQLException {
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.COUNTRY)).thenReturn("East Germany");
    Mockito.when(resultSet.getString(HolidayType.SQL_KEY)).thenReturn("Urlaub 1970-71");

    // End is before start
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.START_DATE)).thenReturn("1971-07-01");
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.END_DATE)).thenReturn("1970-06-15");


    Holiday.ReadFrom(resultSet);
  }

  @Test
  public void testAttributesDatesSameDay() throws SQLException {
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.COUNTRY)).thenReturn("East Germany");
    Mockito.when(resultSet.getString(HolidayType.SQL_KEY)).thenReturn("Tagesausflug");

    // End is before start
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.START_DATE)).thenReturn("2022-02-22");
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.END_DATE)).thenReturn("2022-02-22");

    var holiday = Holiday.ReadFrom(resultSet);
    assertNotNull(holiday);
    assertEquals(holiday.getStartDate(), holiday.getEndDate());
  }


  @Test
  public void testEqualsAndHashCode() throws SQLException {
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.COUNTRY)).thenReturn("East Germany");
    Mockito.when(resultSet.getString(HolidayType.SQL_KEY)).thenReturn("Urlaub 1970-71");
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.START_DATE)).thenReturn("1970-06-15");
    Mockito.when(resultSet.getString(Holiday.HolidaySqlKeys.END_DATE)).thenReturn("1971-07-01");

    var a = Holiday.ReadFrom(resultSet);
    var b = Holiday.ReadFrom(resultSet);

    assertNotNull(a);
    assertNotNull(b);

    assertNotSame(a, b); // Same only if created with the HolidayManager
    assertEquals(a.hashCode(), b.hashCode());
    assertEquals(a, b);
  }

  @Test
  public void testWriteOn() throws SQLException {
    ResultSet input = Mockito.mock(ResultSet.class);
    Mockito.when(input.getString(Holiday.HolidaySqlKeys.COUNTRY)).thenReturn("East Germany");
    Mockito.when(input.getString(Holiday.HolidaySqlKeys.START_DATE)).thenReturn("1970-06-15");
    Mockito.when(input.getString(Holiday.HolidaySqlKeys.END_DATE)).thenReturn("1971-07-01");

    var holiday = Holiday.ReadFrom(input);
    assertNotNull(holiday);

    ResultSet output = Mockito.mock(ResultSet.class);
    holiday.writeOn(output);
    Mockito.verify(output, Mockito.times(1)).updateString(Holiday.HolidaySqlKeys.COUNTRY, "East Germany");
    Mockito.verify(output, Mockito.times(1)).updateString(Holiday.HolidaySqlKeys.START_DATE, "1970-06-15");
    Mockito.verify(output, Mockito.times(1)).updateString(Holiday.HolidaySqlKeys.END_DATE, "1971-07-01");
  }
}
