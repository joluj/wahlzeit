package org.joluj.model;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public class SphericCoordinate extends AbstractCoordinate {
  /**
   * Contains all instances of the {@link SphericCoordinate}.
   * Made protected only for testing.
   */
  protected static final WeakHashMap<SphericCoordinate, WeakReference<SphericCoordinate>> instances = new WeakHashMap<>();

  /**
   * 0 <= phi <= 2*PI
   */
  private final double phi;
  /**
   * 0 <= theta <= PI
   */
  private final double theta;
  /**
   * 0 <= radius
   */
  private final double radius;

  private SphericCoordinate(double phi, double theta, double radius) {
    if (radius < 0) {
      throw new IllegalArgumentException("Radius must be >=0");
    }
    if (!Double.isFinite(phi)) throw new IllegalArgumentException("Parameter phi must be finite");
    if (!Double.isFinite(theta)) throw new IllegalArgumentException("Parameter theta must be finite");
    if (!Double.isFinite(radius)) throw new IllegalArgumentException("Parameter radius must be finite");

    this.phi = phi % (2 * Math.PI);
    this.theta = theta % (Math.PI);
    this.radius = radius;

    this.assertClassInvariants();
  }

  public static SphericCoordinate FromPhiThetaRadius(double phi, double theta, double radius) {
    // create a new coordinate object
    var coordinate = new SphericCoordinate(phi, theta, radius);

    // this will return the currently cached coordinate, or the new value if it does not already exist.
    var weakRef = instances.get(coordinate);
    if (weakRef == null) {
      weakRef = new WeakReference<>(coordinate);
      instances.put(coordinate, weakRef);
    }

    // post-condition
    var returnValue = weakRef.get();
    assert returnValue != null;

    return returnValue;
  }


  @NotNull
  @Override
  public CartesianCoordinate asCartesianCoordinate() {
    double x = radius * Math.sin(theta) * Math.cos(phi);
    double y = radius * Math.sin(theta) * Math.sin(phi);
    double z = radius * Math.cos(theta);

    return CartesianCoordinate.FromXYZ(x, y, z);
  }

  @NotNull
  @Override
  public SphericCoordinate asSphericCoordinate() {
    return this;
  }

  @Override
  protected double doGetCentralAngle(@NotNull SphericCoordinate other) {
    if (isEqual(other)) return 0;

    double dPhi = this.phi - other.phi;
    double thetaA = Math.PI / 2 - theta;
    double thetaB = Math.PI / 2 - other.theta;

    return Math.acos(
        Math.sin(thetaA) * Math.sin(thetaB)
            + Math.cos(thetaA) * Math.cos(thetaB) * Math.cos(Math.abs(dPhi))
    );
  }

  public static SphericCoordinate Deserialize(String str) {
    if (str == null || str.isEmpty()) {
      // Coordinate is empty / not initialized
      return SphericCoordinate.FromPhiThetaRadius(0, 0, 0);
    }

    String[] parts = str.split("\\|");

    if (parts.length != 4 || !"spherical".equals(parts[0])) {
      throw new IllegalArgumentException("Incorrect String representation.");
    }

    try {
      double phi = Double.parseDouble(parts[1]);
      double theta = Double.parseDouble(parts[2]);
      double radius = Double.parseDouble(parts[3]);

      return SphericCoordinate.FromPhiThetaRadius(phi, theta, radius);
    } catch (NullPointerException | NumberFormatException e) {
      // Catch Exceptions and rethrow as IllegalArgumentException to match JavaDoc.
      throw new IllegalArgumentException("Incorrect String representation.", e);
    }
  }

  @NotNull
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

  @Override
  protected void assertClassInvariants() {
    assert 0 <= this.phi && this.phi <= 2 * Math.PI;
    assert 0 <= this.theta && this.theta <= Math.PI;
    assert 0 <= this.radius;
    // In a real world app this would also check if the coordinate is on earth
  }
}
