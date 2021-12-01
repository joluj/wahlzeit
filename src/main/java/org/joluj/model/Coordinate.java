package org.joluj.model;

public interface Coordinate {
  /**
   * @return not null
   */
  public CartesianCoordinate asCartesianCoordinate();

  /**
   * @throws IllegalArgumentException if other is null
   */
  public double getCartesianDistance(Coordinate other);

  /**
   * @return not null
   */
  public SphericCoordinate asSphericCoordinate();

  /**
   * @throws IllegalArgumentException if other is null
   */
  public double getCentralAngle(Coordinate other);

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
  public String serialize();
}
