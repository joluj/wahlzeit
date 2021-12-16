package org.joluj.model;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertTrue;

public class CoordinateTest {

  /**
   * Runs the garbage collector and returns after the cache of the coordinates is clear.
   *
   * @throws org.awaitility.core.ConditionTimeoutException – If the cache cannot be cleared in a reasonable time.
   */
  public static void WaitForEmptyCoordinateCache() {
    System.gc();
    await().atMost(10, TimeUnit.SECONDS).until(CartesianCoordinate.instances::isEmpty);
  }

  /**
   * Clears the cache before each test run
   */
  @Before
  public void clearCache() {
    CoordinateTest.WaitForEmptyCoordinateCache();
  }

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
