package org.joluj.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LocationTest {

  static double DELTA = 0.000001;

  @Test
  public void testSuccessfulCartesianCreationDeserializing() {
    var actual = Location.Deserialize("cartesian|1|2.3|4.5");

    var coordinate = actual.getCoordinate();
    // assert 1: type
    assertTrue(coordinate instanceof CartesianCoordinate);
    var cartesian = coordinate.asCartesianCoordinate();

    // assert 2: values
    assertEquals(1, cartesian.getX(), DELTA);
    assertEquals(2.3, cartesian.getY(), DELTA);
    assertEquals(4.5, cartesian.getZ(), DELTA);
  }

  @Test
  public void testSerializeMethod() {
    var actual = Location.Deserialize("cartesian|1|2.3|4.5");
    assertEquals("cartesian|1.0|2.3|4.5", actual.serialize());
  }

}
