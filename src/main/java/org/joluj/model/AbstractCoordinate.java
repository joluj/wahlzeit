package org.joluj.model;

import org.jetbrains.annotations.NotNull;

/**
 * Provides default delegations of several methods by converting the object to a
 * {@link CartesianCoordinate} first.
 * For coordinate-type specific methods like {@link #getCentralAngle}, the object
 * gets converted to the respective special implementation.
 */
public abstract class AbstractCoordinate implements Coordinate {

  @Override
  public double getCartesianDistance(@NotNull Coordinate other) {
    // Precondition
    if (other == null) throw new IllegalArgumentException("Argument is null");
    // Invariant
    this.assertClassInvariants();

    // Action
    var distance = this.doGetCartesianDistance(other.asCartesianCoordinate());

    // Postcondition
    assert distance >= 0;
    // Invariant
    this.assertClassInvariants();

    return distance;
  }

  /**
   * @param other not null
   */
  protected double doGetCartesianDistance(@NotNull CartesianCoordinate other) {
    return this.asCartesianCoordinate().getCartesianDistance(other);
  }

  @Override
  public double getCentralAngle(@NotNull Coordinate other) {
    if (other == null) throw new IllegalArgumentException("Argument is null");
    this.assertClassInvariants();

    var angle = this.doGetCentralAngle(other.asSphericCoordinate());

    assert angle >= 0;
    this.assertClassInvariants();

    return angle;
  }

  /**
   * @param other not null
   */
  protected double doGetCentralAngle(@NotNull SphericCoordinate other) {
    return this.asSphericCoordinate().doGetCentralAngle(other);
  }

  @NotNull
  @Override
  public SphericCoordinate asSphericCoordinate() {
    return this.asCartesianCoordinate().asSphericCoordinate();
  }

  @Override
  public boolean isEqual(Coordinate other) {
    if (other == null) return false;
    return this.doIsEqual(other);
  }

  /**
   * @param other not null
   */
  protected boolean doIsEqual(@NotNull Coordinate other) {
    return this.asCartesianCoordinate().isEqual(other);
  }

  /**
   * Default implementation of the serialization.
   * Defaults to {@link CartesianCoordinate#serialize()}.
   * <p>
   * If overwritten, remember to register the deserialization in {@link Coordinate#Deserialize}.
   */
  @NotNull
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

  /**
   * Throws error if the class invariants are not fulfilled
   */
  protected abstract void assertClassInvariants();
}
