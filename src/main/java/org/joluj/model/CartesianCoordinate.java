package org.joluj.model;

public class CartesianCoordinate extends AbstractCoordinate {

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
  public CartesianCoordinate(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Creates a Coordinate object from a string that follows the scheme described {@link #serialize here}.
   * Can accept null or an empty string.
   *
   * @param coordinate string representation of the coordinate
   * @throws IllegalArgumentException if the string is not parsable
   */
  public static CartesianCoordinate Deserialize(String coordinate) {
    if (coordinate == null || coordinate.isEmpty()) {
      // Coordinate is empty / not initialized
      return new CartesianCoordinate(0, 0, 0);
    }

    String[] parts = coordinate.split("\\|");

    if (parts.length != 4 || !"cartesian".equals(parts[0])) {
      throw new IllegalArgumentException("Incorrect String representation.");
    }

    try {
      double x = Double.parseDouble(parts[1]);
      double y = Double.parseDouble(parts[2]);
      double z = Double.parseDouble(parts[3]);

      return new CartesianCoordinate(x, y, z);
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
   * Returns true, if the distance to the other coordinate is
   * smaller than 1 meter.
   *
   * @throws NullPointerException if other is null
   */
  public boolean isEqual(Coordinate other) {
    return this.isEqual(other.asCartesianCoordinate(), EPSILON);
  }

  /**
   * returns true, if the distance to the other coordinate is
   * smaller than epsilon.
   */
  public boolean isEqual(Coordinate other, double epsilon) {
    return this.getCartesianDistance(other) < epsilon;
  }

  /**
   * Returns the Coordinate as String representation after the following scheme:
   * "cartesian|x|y|z" where x,y,z are the attributes from the object. "cartesian"
   * is a const string for now.
   */
  public String serialize() {
    return "cartesian"
        + "|" + this.x
        + "|" + this.y
        + "|" + this.z;
  }

  @Override
  public CartesianCoordinate asCartesianCoordinate() {
    return this;
  }

  @Override
  public double getCartesianDistance(Coordinate other) {
    var otherCartesian = other.asCartesianCoordinate();
    double x = this.x - otherCartesian.x;
    double y = this.y - otherCartesian.y;
    double z = this.z - otherCartesian.z;
    return Math.sqrt(x * x + y * y + z * z);
  }

  @Override
  public SphericCoordinate asSphericCoordinate() {
    double radius = Math.sqrt(x * x + y * y + z * z);
    double phi = Math.atan(y / x);
    double theta = Math.acos(z / radius);

    return new SphericCoordinate(phi, theta, radius);
  }
}
