package org.joluj.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CoordinateTest {

  @Test
  public void testDeserializationCartesian() {
    var coordinate = Coordinate.Deserialize("cartesian|1|1|1");
    assertTrue(coordinate instanceof CartesianCoordinate);
  }

  @Test
  public void testDeserializationSpheric() {
    var coordinate = Coordinate.Deserialize("spherical|1|1|1");
    assertTrue(coordinate instanceof SphericCoordinate);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeserializationUnknown() {
    Coordinate.Deserialize("unknown|1|1|1");
  }
}
