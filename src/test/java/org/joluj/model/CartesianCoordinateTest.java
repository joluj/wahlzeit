package org.joluj.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CartesianCoordinateTest {

  static double DELTA = 0.000001;

  @Test
  public void creationFromString() {
    var expected = new CartesianCoordinate(1, 2.3, 4.5);
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
    var a = new CartesianCoordinate(1, 2, 3);
    var b = new CartesianCoordinate(1, 2, 3);
    assertTrue(a.isEqual(b));
    assertTrue(b.isEqual(a));

    assertTrue(a.isEqual(a));
  }

  @Test
  public void isNotEqual() {
    var a = new CartesianCoordinate(1, 2, 3);
    var b = new CartesianCoordinate(1, 2, 4);
    assertFalse(a.isEqual(b));
    assertFalse(b.isEqual(a));
  }

  @SuppressWarnings("SimplifiableAssertion")
  @Test
  public void equals() {
    var a = new CartesianCoordinate(1, 2, 3);
    var b = new CartesianCoordinate(1, 2, 3);
    assertTrue(a.equals(b));
    assertTrue(b.equals(a));

    assertTrue(a.equals(a));
  }

  @SuppressWarnings("SimplifiableAssertion")
  @Test
  public void notEquals() {
    var a = new CartesianCoordinate(1, 2, 3);
    var b = new CartesianCoordinate(1, 2, 4);
    assertFalse(a.equals(b));
    assertFalse(b.equals(a));
  }

  @SuppressWarnings({"SimplifiableAssertion", "EqualsBetweenInconvertibleTypes"})
  @Test
  public void notEqualsWrongType() {
    var a = new CartesianCoordinate(1, 2, 3);
    var b = new Location(new CartesianCoordinate(1, 2, 4));
    assertFalse(a.equals(b));
    assertFalse(b.equals(a));
  }

  @Test
  public void getDistance1() {
    var a = new CartesianCoordinate(1, 2, 3);
    var b = new CartesianCoordinate(1, 2, 3);
    assertEquals(a.getCartesianDistance(b), 0, DELTA);
  }

  @Test
  public void getDistance2() {
    var a = new CartesianCoordinate(0, 0, 0);
    var b = new CartesianCoordinate(1, 0, 0);
    var c = new CartesianCoordinate(0, 1, 0);
    var d = new CartesianCoordinate(0, 0, 1);
    assertEquals(a.getCartesianDistance(b), 1, DELTA);
    assertEquals(a.getCartesianDistance(c), 1, DELTA);
    assertEquals(a.getCartesianDistance(d), 1, DELTA);
  }
}
