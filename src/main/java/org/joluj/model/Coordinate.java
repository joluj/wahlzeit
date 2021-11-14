package org.joluj.model;

import java.util.Objects;

public class Coordinate {

  /**
   * Default allowed distance (in meters).
   */
  private static final double EPSILON = 1;

  /**
   * x-coordinate in meters
   */
  private double x;
  /**
   * y-coordinate in meters
   */
  private double y;
  /**
   * z-coordinate in meters
   */
  private double z;

  /**
   * Creates a Coordinate following the cartesian scheme (with center of earth = [0,0,0]).
   * The params are in meters.
   */
  public Coordinate(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Creates a Coordinate object from a string that follows the scheme described {@link #toString here}.
   * Can accept null or an empty string.
   *
   * @param coordinate string representation of the coordinate
   * @throws IllegalArgumentException if the string is not parsable
   */
  public static Coordinate FromString(String coordinate) {
    if (coordinate == null || coordinate.isEmpty()) {
      // Coordinate is empty / not initialized
      return new Coordinate(0, 0, 0);
    }

    String[] parts = coordinate.split("\\|");

    if (parts.length != 4 || !"cartesian".equals(parts[0])) {
      throw new IllegalArgumentException("Incorrect String representation.");
    }

    try {
      double x = Double.parseDouble(parts[1]);
      double y = Double.parseDouble(parts[2]);
      double z = Double.parseDouble(parts[3]);

      return new Coordinate(x, y, z);
    } catch (NullPointerException | NumberFormatException e) {
      // Catch Exceptions and rethrow as IllegalArgumentException to match JavaDoc.
      throw new IllegalArgumentException("Incorrect String representation.", e);
    }

  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  /**
   * Returns the distance of this coordinate to other in meters
   */
  public double getDistance(Coordinate other) {
    double x = this.x - other.x;
    double y = this.y - other.y;
    double z = this.z - other.z;
    return Math.sqrt(x * x + y * y + z * z);
  }

  /**
   * Returns true, if the distance to the other coordinate is
   * smaller than 1 meter.
   */
  public boolean isEqual(Coordinate other) {
    return this.isEqual(other, EPSILON);
  }

  /**
   * returns true, if the distance to the other coordinate is
   * smaller than epsilon.
   */
  public boolean isEqual(Coordinate other, double epsilon) {
    return this.getDistance(other) < epsilon;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Coordinate) {
      return this.isEqual((Coordinate) other);
    }
    return false;
  }

  @Override
  public int hashCode() {
    // Custom implementation since the hashCodes should be equal for objects that are
    // equal according to #equals.
    // This implementation does have a higher hash collision that traditional approaches
    // that calculate with large primes. However, this implementation fits the purpose of this
    // class and is faster than calculating with large primes.

    int a = (int) Math.round(this.x);
    int b = (int) Math.round(this.y);
    int c = (int) Math.round(this.z);

    return a + b + c;
  }

  /**
   * Returns the Coordinate as String representation after the following scheme:
   * "cartesian|x|y|z" where x,y,z are the attributes from the object. "cartesian"
   * is a const string for now.
   */
  public String toString() {
    return "cartesian"
        + "|" + this.x
        + "|" + this.y
        + "|" + this.z;
  }
}
