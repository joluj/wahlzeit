package org.joluj.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SphericCoordinateTest {

  static double EPSILON = 0.0001;

  @Test
  public void testCreationFromString() {
    var expected = new SphericCoordinate(1, 2.3, 4.5);
    var actual = SphericCoordinate.Deserialize("spherical|1|2.3|4.5");
    assertEquals(expected.getPhi(), actual.getPhi(), EPSILON);
    assertEquals(expected.getTheta(), actual.getTheta(), EPSILON);
    assertEquals(expected.getRadius(), actual.getRadius(), EPSILON);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreationFromString_TooManyCoordinates() {
    SphericCoordinate.Deserialize("spherical|1|2.3|4.5|6.7");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreationFromString_TooViewCoordinates() {
    SphericCoordinate.Deserialize("spherical|1|2.3");
  }

  @Test
  public void testSerialization() {
    var actual = SphericCoordinate.Deserialize("spherical|1|2.3|4.5");
    assertEquals("spherical|1.0|2.3|4.5", actual.serialize());
  }

  @Test
  public void testIsEqualSameObject() {
    var a = new SphericCoordinate(1, 2, 3);
    var b = new SphericCoordinate(1, 2, 3);
    assertTrue(a.isEqual(b));
    assertTrue(b.isEqual(a));

    assertTrue(a.isEqual(a));
  }

  @Test
  public void testIsEqualAngleModulo() {
    var a = new SphericCoordinate(1.2, 1 + Math.PI, 1);
    var b = new SphericCoordinate(1.2 + 2 * Math.PI, 1, 1);
    assertTrue(a.isEqual(b));
  }

  @Test
  public void testIsNotEqual() {
    var a = new SphericCoordinate(1, 2, 3);
    var b = new SphericCoordinate(1, 2, 4);
    assertFalse(a.isEqual(b));
    assertFalse(b.isEqual(a));
  }

  @Test
  public void testCentralAngleSameCoords() {
    var a = new SphericCoordinate(1, 2, 3);
    var b = new SphericCoordinate(1, 2, 3);
    assertEquals(0, a.getCentralAngle(b), EPSILON);
  }

  @Test
  public void testCentralAngleOtherCoords1() {
    var a = new SphericCoordinate(1, 2, 5);
    var b = new SphericCoordinate(1, 3, 5);
    assertEquals(1, b.getCentralAngle(a), EPSILON);
  }

  @Test
  public void testCentralAngleOtherCoords2() {
    var a = new SphericCoordinate(1.234, 2.345, 5);
    var b = new SphericCoordinate(0.543, 0.543, 5);
    assertEquals(1.890, b.getCentralAngle(a), EPSILON);
  }

  @Test
  public void testAsCartesian() {
    var spherical = new SphericCoordinate(0.5, 1.3, 1);
    var cartesian = new CartesianCoordinate(0.12825, 0.46195, 0.87758);

    assertTrue(spherical.isEqual(cartesian));
    assertTrue(spherical.asCartesianCoordinate().isEqual(cartesian));
  }

  @Test
  public void testEqualsMethod() {
    // Checks interchangeability of different coordinate types
    var spherical = new SphericCoordinate(0.5, 1.3, 1);
    var cartesian = new CartesianCoordinate(0.12825, 0.46195, 0.87758);

    //noinspection AssertBetweenInconvertibleTypes
    assertEquals(spherical, cartesian);
    //noinspection AssertBetweenInconvertibleTypes
    assertEquals(cartesian, spherical);
  }

  @Test
  public void testHashCodeMethod() {
    // Checks interchangeability of different coordinate types
    var spherical = new SphericCoordinate(0.5, 1.3, 1);
    var cartesian = new CartesianCoordinate(0.12825, 0.46195, 0.87758); // Same Point

    assertEquals(spherical.hashCode(), cartesian.hashCode());
  }
}
