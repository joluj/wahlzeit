package org.joluj.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocationTest {

  static double DELTA = 0.000001;

  @Test
  public void testSuccessfulCreationFromString() {
    var actual = Location.FromString("cartesian|1|2.3|4.5");
    assertEquals(1, actual.getCoordinate().getX(), DELTA);
    assertEquals(2.3, actual.getCoordinate().getY(), DELTA);
    assertEquals(4.5, actual.getCoordinate().getZ(), DELTA);
  }

  @Test
  public void toStringMethod() {
    var actual = Location.FromString("cartesian|1|2.3|4.5");
    assertEquals("cartesian|1.0|2.3|4.5", actual.toString());
  }

}
