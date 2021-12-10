package org.joluj.model;

import org.jetbrains.annotations.NotNull;

public class Location {
  private final Coordinate coordinate;

  /**
   * @throws IllegalArgumentException if coordinate is null
   */
  public Location(@NotNull Coordinate coordinate) {
    if (coordinate == null) {
      throw new IllegalArgumentException("Coordinate must not be null");
    }
    this.coordinate = coordinate;
  }

  /**
   * Creates a Location object from a string.
   * The string must follow {@link Coordinate#serialize this scheme}.
   */
  public static Location Deserialize(@NotNull String location) {
    return new Location(Coordinate.Deserialize(location));
  }

  public Coordinate getCoordinate() {
    return coordinate;
  }

  public String serialize() {
    return this.coordinate.serialize();
  }
}
