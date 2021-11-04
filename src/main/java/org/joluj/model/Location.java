package org.joluj.model;

public class Location {
  public Coordinate coordinate;

  /**
   * @throws IllegalArgumentException if coordinate is null
   */
  public Location(Coordinate coordinate) {
    if (coordinate == null) {
      throw new IllegalArgumentException("Coordinate must not be null");
    }
    this.coordinate = coordinate;
  }

  /**
   * Creates a Location object from a string.
   * The string must follow {@link Coordinate#toString() this scheme}.
   */
  public static Location FromString(String location) {
    return new Location(Coordinate.FromString(location));
  }

  public String toString() {
    return this.coordinate.toString();
  }
}
