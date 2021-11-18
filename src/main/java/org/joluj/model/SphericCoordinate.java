package org.joluj.model;

public class SphericCoordinate implements Coordinate {

  private final double phi;
  private final double theta;
  private final double radius;

  public SphericCoordinate(double phi, double theta, double radius) {
    this.phi = phi;
    this.theta = theta;
    this.radius = radius;
  }

  @Override
  public CartesianCoordinate asCartesianCoordinate() {
    // TODO
    return null;
  }

  @Override
  public double getCartesianDistance(Coordinate other) {
    return this.asCartesianCoordinate().getCartesianDistance(other);
  }

  @Override
  public SphericCoordinate asSphericCoordinate() {
    return this;
  }

  @Override
  public double getCentralAngle(Coordinate other) {
    // TODO
    return 0;
  }

  @Override
  public boolean isEqual(Coordinate other) {
    return this.asCartesianCoordinate().isEqual(other);
  }

  public static SphericCoordinate Deserialize(String str) {
    if (str == null || str.isEmpty()) {
      // Coordinate is empty / not initialized
      return new SphericCoordinate(0, 0, 0);
    }

    String[] parts = str.split("\\|");

    if (parts.length != 4 || !"spherical".equals(parts[0])) {
      throw new IllegalArgumentException("Incorrect String representation.");
    }

    try {
      double phi = Double.parseDouble(parts[1]);
      double theta = Double.parseDouble(parts[2]);
      double radius = Double.parseDouble(parts[3]);

      return new SphericCoordinate(phi, theta, radius);
    } catch (NullPointerException | NumberFormatException e) {
      // Catch Exceptions and rethrow as IllegalArgumentException to match JavaDoc.
      throw new IllegalArgumentException("Incorrect String representation.", e);
    }
  }

  @Override
  public String serialize() {
    return "spherical"
        + "|" + this.phi
        + "|" + this.theta
        + "|" + this.radius;
  }

  public double getPhi() {
    return phi;
  }

  public double getTheta() {
    return theta;
  }

  public double getRadius() {
    return radius;
  }
}
