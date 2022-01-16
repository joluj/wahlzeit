package org.joluj.model.holiday;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joluj.model.AssertionHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

public class Holiday {

  public static final DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

  @Nullable
  private final String country;

  @NotNull
  private final HolidayType type;

  @Nullable
  private final Date startDate;

  @Nullable
  private final Date endDate;

  private Holiday(@Nullable String country, @NotNull HolidayType type, @Nullable Date startDate, @Nullable Date endDate) {
    AssertionHelper.AssertNotNull(type);
    if (startDate != null && endDate != null && endDate.before(startDate)) {
      throw new IllegalArgumentException("End date is before start date.");
    }

    this.country = country;
    this.type = type;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  /**
   * Returns null if no values are given in the given resultSet
   */
  @Nullable
  protected static Holiday ReadFrom(@NotNull ResultSet resultSet) throws SQLException {
    // Parse country
    var country = resultSet.getString(HolidaySqlKeys.COUNTRY);

    // Parse type
    var type = HolidayManager.getInstance().getOrCreateHolidayType(resultSet);

    // Parse start date
    Date startDate = null;
    try {
      var dateAsString = resultSet.getString(HolidaySqlKeys.START_DATE);
      if (dateAsString != null) {
        startDate = DATE_FORMATTER.parse(dateAsString);
      }
    } catch (ParseException e) {
      throw new SQLException("Cannot parse start date. Must be formatted as yyyy-MM-dd");
    }

    // Parse end date
    Date endDate = null;
    try {
      var dateAsString = resultSet.getString(HolidaySqlKeys.END_DATE);
      if (dateAsString != null) {
        endDate = DATE_FORMATTER.parse(dateAsString);
      }
    } catch (ParseException e) {
      throw new SQLException("Cannot parse end date. Must be formatted as yyyy-MM-dd");
    }

    // if empty: return null
    if (country == null && type.getType().equals("") && startDate == null && endDate == null) {
      return null;
    }

    try {
      return new Holiday(country, type, startDate, endDate);
    } catch (IllegalArgumentException e) {
      throw new SQLException(e);
    }
  }

  public void writeOn(@NotNull ResultSet resultSet) throws SQLException {
    resultSet.updateString(HolidaySqlKeys.COUNTRY, country);
    type.writeOn(resultSet);
    if (startDate != null) {
      resultSet.updateString(HolidaySqlKeys.START_DATE, DATE_FORMATTER.format(startDate));
    }
    if (endDate != null) {
      resultSet.updateString(HolidaySqlKeys.END_DATE, DATE_FORMATTER.format(endDate));
    }
  }

  @Nullable
  public String getCountry() {
    return this.country;
  }

  @NotNull
  public HolidayType getType() {
    return type;
  }

  @Nullable
  public Date getStartDate() {
    return startDate;
  }

  @Nullable
  public Date getEndDate() {
    return endDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Holiday holiday = (Holiday) o;
    return Objects.equals(country, holiday.country) && type.equals(holiday.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(country, type);
  }

  public static class HolidaySqlKeys {
    public static final String COUNTRY = "country";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
  }
}
