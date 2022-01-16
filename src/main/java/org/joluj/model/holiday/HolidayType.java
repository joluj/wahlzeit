package org.joluj.model.holiday;

import org.jetbrains.annotations.NotNull;
import org.joluj.model.AssertionHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Subtypes are created
 */
public class HolidayType {

  public final static String SQL_KEY = "holidayType";

  @NotNull
  private final String type;

  protected HolidayType(@NotNull String type) {
    AssertionHelper.AssertNotNull(type);

    this.type = type;
  }

  protected HolidayType(@NotNull HolidayType parent, @NotNull String type) {
    AssertionHelper.AssertNotNull(parent);
    AssertionHelper.AssertNotNull(type);

    this.type = parent.getType() + " / " + type;
  }

  protected static HolidayType ReadFrom(@NotNull ResultSet resultSet) throws SQLException {
    var typeAsString = resultSet.getString(SQL_KEY);
    if (typeAsString == null || typeAsString.isBlank()) return new NullHolidayType();

    return new HolidayType(typeAsString);
  }

  public void writeOn(@NotNull ResultSet resultSet) throws SQLException {
    resultSet.updateString(SQL_KEY, type);
  }

  @NotNull
  public String getType() {
    return type;
  }

  /**
   * Returns true iff this is a subtype of other
   */
  public boolean isSubtypeOf(@NotNull HolidayType other) {
    AssertionHelper.AssertNotNull(other);

    return this.type.startsWith(other.type);
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
