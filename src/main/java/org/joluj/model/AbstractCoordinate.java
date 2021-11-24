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
  public int hashCode() {
    return this.asCartesianCoordinate().hashCode();
  }

  @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
  @Override
  public boolean equals(Object obj) {
    return this.asCartesianCoordinate().equals(obj);
  }
}
