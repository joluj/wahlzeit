package org.joluj.model.holiday;

import org.jetbrains.annotations.NotNull;
import org.joluj.model.AssertionHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class HolidayType {

  private final static String SQL_KEY = "holidayType";

  @NotNull
  private final String type;

  protected HolidayType(@NotNull String type) {
    AssertionHelper.AssertNotNull(type);

    this.type = type;
  }

  protected static HolidayType ReadFrom(@NotNull ResultSet resultSet) throws SQLException {
    var typeAsString = resultSet.getString(SQL_KEY);
    if (typeAsString == null) typeAsString = "";

    return HolidayManager.getInstance().getOrCreateHolidayType(typeAsString);
  }

  public void writeOn(@NotNull ResultSet resultSet) throws SQLException {
    resultSet.updateString(SQL_KEY, type);
  }

  @NotNull
  public String getType() {
    return type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HolidayType that = (HolidayType) o;
    return type.equals(that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type);
  }
}
