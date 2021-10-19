package org.joluj.model;

public class Location {
  public Coordinate coordinate;

  public Location(Coordinate coordinate) {
    this.coordinate = coordinate;
  }

  public Location(String location) {
    this.coordinate = new Coordinate(location);
  }

  public String toString() {
    return this.coordinate.toString();
  }
}
