package org.joluj.model;

public class Coordinate {

  private double x;
  private double y;
  private double z;

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

  public double getDistance(Coordinate other) {
    double x = this.x - other.x;
    double y = this.y - other.y;
    double z = this.z - other.z;
    return Math.sqrt(x * x + y * y + z * z);
  }

  public boolean isEqual(Coordinate other) {
    return this.x == other.x && this.y == other.y && this.z == other.z;
  }

  public boolean equals(Object other) {
    if (other instanceof Coordinate) {
      return this.isEqual((Coordinate) other);
    }
    return false;
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
