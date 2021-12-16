package org.joluj.model;

import org.jetbrains.annotations.NotNull;
import org.joluj.utils.CoordinateCache;

public class CartesianCoordinate extends AbstractCoordinate {
  /**
   * Contains all instances of the {@link CartesianCoordinate}.
   */
  protected static final CoordinateCache<CartesianCoordinate> instances = new CoordinateCache<>();

  /**
   * Default allowed distance (in meters).
   */
  private static final double EPSILON = 1;

  /**
   * x-coordinate in meters
   */
  private final double x;
  /**
   * y-coordinate in meters
   */
  private final double y;
  /**
   * z-coordinate in meters
   */
  private final double z;

  /**
   * Creates a Coordinate following the cartesian scheme (with center of earth = [0,0,0]).
   * The params are in meters.
   */
  private CartesianCoordinate(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;

    this.assertClassInvariants();
  }

  /**
   * Creates a coordinate given the cartesian coordinates x, y, and z.
   * <p>
   * If there exists a cached version of a coordinate at (about) that point, then the existing object
   * is returned.
   * <p>
   * Uses #equals and #hashCode to compare the equality of the objects.
   */
  public static CartesianCoordinate FromXYZ(double x, double y, double z) {
    return CartesianCoordinate.instances.getOrSet(new CartesianCoordinate(x, y, z));
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
      return CartesianCoordinate.FromXYZ(0, 0, 0);
    }

    String[] parts = coordinate.split("\\|");

    if (parts.length != 4 || !"cartesian".equals(parts[0])) {
      throw new IllegalArgumentException("Incorrect String representation.");
    }

    try {
      double x = Double.parseDouble(parts[1]);
      double y = Double.parseDouble(parts[2]);
      double z = Double.parseDouble(parts[3]);

      return CartesianCoordinate.FromXYZ(x, y, z);
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
  @Override
  protected boolean doIsEqual(@NotNull Coordinate other) {
    return this.doIsEqual(other.asCartesianCoordinate(), EPSILON);
  }

  /**
   * returns true, if the distance to the other coordinate is
   * smaller than epsilon.
   */
  protected boolean doIsEqual(Coordinate other, double epsilon) {
    return this.getCartesianDistance(other) < epsilon;
  }

  /**
   * Returns the Coordinate as String representation after the following scheme:
   * "cartesian|x|y|z" where x,y,z are the attributes from the object. "cartesian"
   * is a const string for now.
   */
  @NotNull
  public String serialize() {
    return "cartesian"
        + "|" + this.x
        + "|" + this.y
        + "|" + this.z;
  }

  @NotNull
  @Override
  public CartesianCoordinate asCartesianCoordinate() {
    return this;
  }

  @Override
  protected double doGetCartesianDistance(@NotNull CartesianCoordinate other) {
    double x = this.x - other.x;
    double y = this.y - other.y;
    double z = this.z - other.z;
    return Math.sqrt(x * x + y * y + z * z);
  }

  @NotNull
  @Override
  public SphericCoordinate asSphericCoordinate() {
    double radius = Math.sqrt(x * x + y * y + z * z);
    double phi = Math.atan(y / x);
    double theta = Math.acos(z / radius);

    return SphericCoordinate.FromPhiThetaRadius(phi, theta, radius);
  }

  @Override
  protected void assertClassInvariants() {
    assert Double.isFinite(this.x);
    assert Double.isFinite(this.y);
    assert Double.isFinite(this.z);
    // In a real world app this would check if the coordinate is on earth,
    // but it would be too much here
  }
}
