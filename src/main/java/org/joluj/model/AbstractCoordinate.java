package org.joluj.model;

/**
 * Provides default delegations of several methods by converting the object to a
 * {@link CartesianCoordinate} first.
 * For coordinate-type specific methods like {@link #getCentralAngle}, the object
 * gets converted to the respective special implementation.
 */
public abstract class AbstractCoordinate implements Coordinate {

  @Override
  public double getCartesianDistance(Coordinate other) {
    return this.asCartesianCoordinate().getCartesianDistance(other);
  }

  @Override
  public double getCentralAngle(Coordinate other) {
    return this.asSphericCoordinate().getCentralAngle(other);
  }

  @Override
  public SphericCoordinate asSphericCoordinate() {
    return this.asCartesianCoordinate().asSphericCoordinate();
  }

  @Override
  public boolean isEqual(Coordinate other) {
    return this.asCartesianCoordinate().isEqual(other);
  }

  /**
   * Default implementation of the serialization.
   * Defaults to {@link CartesianCoordinate#serialize()}.
   * <p>
   * If overwritten, remember to register the deserialization in {@link Coordinate#Deserialize}.
   */
  @Override
  public String serialize() {
    return this.asCartesianCoordinate().serialize();
  }

  @Override
  public final int hashCode() {
    var cartesian = this.asCartesianCoordinate();

    // Custom implementation since the hashCodes should be equal for objects that are
    // equal according to #equals.
    // This implementation does have a higher hash collision that traditional approaches
    // that calculate with large primes. However, this implementation fits the purpose of this
    // class and is faster than calculating with large primes.

    int a = (int) Math.round(cartesian.getX());
    int b = (int) Math.round(cartesian.getY());
    int c = (int) Math.round(cartesian.getZ());

    return a + b + c;
  }

  @Override
  public final boolean equals(Object other) {
    if (other instanceof Coordinate) {
      return this.asCartesianCoordinate().isEqual((Coordinate) other);
    }
    return false;
  }
}
