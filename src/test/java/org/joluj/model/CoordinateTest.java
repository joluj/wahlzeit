package org.joluj.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoordinateTest {

  static double DELTA = 0.000001;

  @Test
  public void testSuccessfulCreationFromString() {
    var expected = new Coordinate(1, 2.3, 4.5);
    var actual = Coordinate.FromString("cartesian|1|2.3|4.5");
    assertEquals(expected.getX(), actual.getX(), DELTA);
    assertEquals(expected.getY(), actual.getY(), DELTA);
    assertEquals(expected.getZ(), actual.getZ(), DELTA);
  }

  @Test
  public void toStringMethod() {
    var actual = Coordinate.FromString("cartesian|1|2.3|4.5");
    assertEquals("cartesian|1.0|2.3|4.5", actual.toString());
  }
}
