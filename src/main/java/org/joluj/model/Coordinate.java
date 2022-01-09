package org.joluj.model;


import org.jetbrains.annotations.NotNull;
import org.joluj.utils.PatternInstance;

@PatternInstance(
    patternName = "Factory Method",
    participants = {"AbstractCreator", "ConcreteCreator", "AbstractProduct"},
    description = "Method #Deserialize"
)
public interface Coordinate {
  /**
   * @return not null
   */
  @NotNull
  public CartesianCoordinate asCartesianCoordinate();

  /**
   * @throws IllegalArgumentException if other is null
   */
  public double getCartesianDistance(@NotNull Coordinate other);

  /**
   * @return not null
   */
  @NotNull
  public SphericCoordinate asSphericCoordinate();

  /**
   * @throws IllegalArgumentException if other is null
   */
  public double getCentralAngle(@NotNull Coordinate other);

  public boolean isEqual(Coordinate other);

  /**
   * Creates a Coordinate Object.
   *
   * @param str string-representation of a coordinate from {@link #serialize()}
   * @throws IllegalArgumentException if str is null
   * @throws IllegalArgumentException if str is not valid
   */
  static Coordinate Deserialize(String str) {
    if (str == null) throw new IllegalArgumentException("Argument is null");

    if (str.startsWith("cartesian")) {
      return CartesianCoordinate.Deserialize(str);
    }

    if (str.startsWith("spherical")) {
      return SphericCoordinate.Deserialize(str);
    }

    throw new IllegalArgumentException("Wrong coordinate type");
  }

  /**
   * Returns the Coordinate as String representation.
   * Output can be used as valid input for {@link #Deserialize}
   *
   * @return not null
   */
  @NotNull
  public String serialize();
}
