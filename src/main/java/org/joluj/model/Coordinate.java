package org.joluj.model;

public interface Coordinate {
  public CartesianCoordinate asCartesianCoordinate();

  /**
   * @throws NullPointerException if other is null
   */
  public double getCartesianDistance(Coordinate other);

  public SphericCoordinate asSphericCoordinate();

  /**
   * @throws NullPointerException if other is null
   */
  public double getCentralAngle(Coordinate other);

  public boolean isEqual(Coordinate other);

  /**
   * Creates a Coordinate Object.
   *
   * @param str string-representation of a coordinate from {@link #serialize()}
   */
  static Coordinate Deserialize(String str) {
    if (str == null) throw new NullPointerException("Argument is null");

    if (str.startsWith("cartesian")) {
      return CartesianCoordinate.Deserialize(str);
    }

    if (str.startsWith("spherical")) {
      return SphericCoordinate.Deserialize(str);
    }

    throw new IllegalArgumentException("Wrong coordinate type"); // TODO
  }

  /**
   * Returns the Coordinate as String representation.
   * Output can be used as valid input for {@link #Deserialize}
   */
  public String serialize();
}
