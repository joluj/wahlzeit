package org.joluj.model.holiday;

import org.jetbrains.annotations.NotNull;
import org.joluj.model.AssertionHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Class to declare Holiday Types.
 * Subtypes can be created using the HolidayType(HolidayType, String) constructor.
 * Subtypes are tracked by string pattern matching. Subtypes are separated from their parents by " / ".
 * <p>
 * <b>Example</b>:
 * If "Trip to Berlin" is a Subtype of "Trip to Germany", then it's stored as "Trip to Germany / Trip to Berlin".
 * <p>
 * The reason for that construction is the following:
 * In the app, the HolidayType information is saved in the DB by a String column.
 * There exists no HolidayType Entity in the DB.
 * If we want to save the hierarchy information, there must be some way to store it in the DB.
 * The HolidayType solves this problem by having the hierarchy path in its name as described above.
 * This also has the advantage that the subtype information can be retrieved without querying the database.
 * If the subtype information was saved via reference only, then it wouldn't be possible to make the subtype information persistent.
 * If the subtype information was saved via entity id, then the method has to perform a database query.
 * So, the subtyping was designed like that because it suits the application context better than the alternatives.
 */
public class HolidayType {

  public final static String SQL_KEY = "holidayType";

  /**
   * String that separates the parent type from its child.
   * <p>
   * E.g.: If "Trip to Berlin" is a subtype of "Trip to Germany", then it's saved as
   * "Trip to Berlin" + SEPARATION_STRING + "Trip to Germany".
   */
  protected final static String SEPARATION_STRING = " / ";

  @NotNull
  private final String type;

  protected HolidayType(@NotNull String type) {
    AssertionHelper.AssertNotNull(type);

    this.type = type;
  }

  protected HolidayType(@NotNull HolidayType parent, @NotNull String type) {
    AssertionHelper.AssertNotNull(parent);
    AssertionHelper.AssertNotNull(type);

    this.type = parent.getType() + SEPARATION_STRING + type;
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
   * Returns true iff this is a subtype of other.
   * A type is considered a subtype of itself.
   * <p>
   * Example with movies:
   * <ul>
   *   <li> Movie
   *     <ul>
   *       <li>Action Movie </li>
   *       <li>Comedy Movie </li>
   *     </ul>
   *   </li>
   * </ul>
   * <p>
   * "Movie" is subtype of "Action Movie": false <br>
   * "Action Movie" is subtype of "Movie": true <br>
   * "Action Movie" is subtype of "Action Movie": true <br>
   * <p>
   * <p>
   * For Syntax, see {@link HolidayType class annotation}.
   */
  public boolean isSubtypeOf(@NotNull HolidayType other) {
    AssertionHelper.AssertNotNull(other);

    // Either full string match or this.type starts with the other type but with the separation string as postfix.
    // The separation string ensures that the last component of the other type is finished (e.g.: "A / B" would
    // be considered as a subtype of "A / B1" without the postfix; See test {@link org.joluj.model.holiday.HolidayTypeTest#testSubtypeSubstring}).
    return this.type.equals(other.type) || (this.type).startsWith(other.type + SEPARATION_STRING);
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
