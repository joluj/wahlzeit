package org.joluj.model;

import org.junit.Before;
import org.junit.Test;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

public class CartesianCoordinateTest {

  static double DELTA = 0.000001;

  /**
   * Clears the cache before each test run
   */
  @Before
  public void clearCache() {
    CoordinateTest.WaitForEmptyCoordinateCache();
  }

  @Test
  public void creationFromString() {
    var expected = CartesianCoordinate.FromXYZ(1, 2.3, 4.5);
    var actual = CartesianCoordinate.Deserialize("cartesian|1|2.3|4.5");
    assertEquals(expected.getX(), actual.getX(), DELTA);
    assertEquals(expected.getY(), actual.getY(), DELTA);
    assertEquals(expected.getZ(), actual.getZ(), DELTA);
  }

  @Test(expected = IllegalArgumentException.class)
  public void creationFromString_TooManyCoordinated() {
    CartesianCoordinate.Deserialize("cartesian|1|2.3|4.5|6.7");
  }

  @Test(expected = IllegalArgumentException.class)
  public void creationFromString_TooViewCoordinated() {
    CartesianCoordinate.Deserialize("cartesian|1|2.3");
  }

  @Test(expected = IllegalArgumentException.class)
  public void creationFromString_WrongCoordinateType() {
    CartesianCoordinate.Deserialize("wrong|1|2.3|4.5");
  }

  @Test
  public void toStringMethod() {
    var actual = CartesianCoordinate.Deserialize("cartesian|1|2.3|4.5");
    assertEquals("cartesian|1.0|2.3|4.5", actual.serialize());
  }

  @Test
  public void isEqual() {
    var a = CartesianCoordinate.FromXYZ(1, 2, 3);
    var b = CartesianCoordinate.FromXYZ(1, 2, 3);
    assertTrue(a.isEqual(b));
    assertTrue(b.isEqual(a));

    assertTrue(a.isEqual(a));
  }

  @Test
  public void isNotEqual() {
    var a = CartesianCoordinate.FromXYZ(1, 2, 3);
    var b = CartesianCoordinate.FromXYZ(1, 2, 4);
    assertFalse(a.isEqual(b));
    assertFalse(b.isEqual(a));
  }

  @SuppressWarnings("SimplifiableAssertion")
  @Test
  public void equals() {
    var a = CartesianCoordinate.FromXYZ(1, 2, 3);
    var b = CartesianCoordinate.FromXYZ(1, 2, 3);
    assertTrue(a.equals(b));
    assertTrue(b.equals(a));

    assertTrue(a.equals(a));
  }

  @SuppressWarnings("SimplifiableAssertion")
  @Test
  public void notEquals() {
    var a = CartesianCoordinate.FromXYZ(1, 2, 3);
    var b = CartesianCoordinate.FromXYZ(1, 2, 4);
    assertFalse(a.equals(b));
    assertFalse(b.equals(a));
  }

  @SuppressWarnings({"SimplifiableAssertion", "EqualsBetweenInconvertibleTypes"})
  @Test
  public void notEqualsWrongType() {
    var a = CartesianCoordinate.FromXYZ(1, 2, 3);
    var b = new Location(CartesianCoordinate.FromXYZ(1, 2, 4));
    assertFalse(a.equals(b));
    assertFalse(b.equals(a));
  }

  @Test
  public void getDistance1() {
    var a = CartesianCoordinate.FromXYZ(1, 2, 3);
    var b = CartesianCoordinate.FromXYZ(1, 2, 3);
    assertEquals(a.getCartesianDistance(b), 0, DELTA);
  }

  @Test
  public void getDistance2() {
    var a = CartesianCoordinate.FromXYZ(0, 0, 0);
    var b = CartesianCoordinate.FromXYZ(1, 0, 0);
    var c = CartesianCoordinate.FromXYZ(0, 1, 0);
    var d = CartesianCoordinate.FromXYZ(0, 0, 1);
    assertEquals(a.getCartesianDistance(b), 1, DELTA);
    assertEquals(a.getCartesianDistance(c), 1, DELTA);
    assertEquals(a.getCartesianDistance(d), 1, DELTA);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getDistanceNull() {
    var a = CartesianCoordinate.FromXYZ(0, 0, 0);
    a.getCartesianDistance(null);
  }

  @Test
  public void testAsSpheric() {
    var cartesian = CartesianCoordinate.FromXYZ(0.12825, 0.46195, 0.87758);
    var spherical = SphericCoordinate.FromPhiThetaRadius(0.5, 1.3, 1);

    assertTrue(cartesian.asSphericCoordinate().isEqual(spherical));
    assertTrue(cartesian.isEqual(spherical));
  }

  /**
   * Asserts that multiple "new" coordinates are actually the same object,
   * not just equal.
   */
  @Test
  public void testSameObject() {
    var a = CartesianCoordinate.FromXYZ(1, 1, 1);
    var b = CartesianCoordinate.FromXYZ(1, 1, 1);

    assertSame(a, b);
  }

  /**
   * Asserts that the garbage collector does not throw away still used things
   */
  @Test
  public void testSameObject_AfterGarbageCollector() {
    var a = CartesianCoordinate.FromXYZ(1, 1, 1);
    var b = CartesianCoordinate.FromXYZ(10, 10, 10);
    assertNotEquals(a, b); // Just to make sure that these are different objects
    assertEquals(CartesianCoordinate.instances.size(), 2);

    // remove ref to b
    b = null;

    // Clear garbage
    System.gc();
    // Assert that there is exactly one instance left
    await().atMost(10, TimeUnit.SECONDS).until(() -> CartesianCoordinate.instances.size() == 1);
    // Assert that this instance is a
    assertSame(a, ((WeakReference<CartesianCoordinate>) CartesianCoordinate.instances.values().toArray()[0]).get());
  }
}
