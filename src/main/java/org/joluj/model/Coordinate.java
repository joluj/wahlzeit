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
  public Coordinate(String coordinate) {
    if (coordinate == null || coordinate.isEmpty()) {
      // Coordinate is empty / not initialized
      return;
    }

    String[] parts = coordinate.split("\\|");

    if (parts.length != 4 || !"cartesian".equals(parts[0])) {
      throw new IllegalArgumentException("Incorrect String representation.");
    }

    try {
      this.x = Double.parseDouble(parts[1]);
      this.y = Double.parseDouble(parts[2]);
      this.z = Double.parseDouble(parts[3]);
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
