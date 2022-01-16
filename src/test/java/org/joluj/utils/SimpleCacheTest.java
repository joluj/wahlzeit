package org.joluj.utils;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

public class SimpleCacheTest {
  /**
   * Asserts that multiple "new" coordinates are actually the same object,
   * not just equal.
   */
  @Test
  public void testSameObject() {
    var cache = new SimpleCache<ExampleObject>();

    // just for showcase: they equal, but are not the same
    assertEquals(new ExampleObject(1), new ExampleObject(1));
    assertNotSame(new ExampleObject(1), new ExampleObject(1));

    var a = cache.getOrSet(new ExampleObject(1));
    var b = cache.getOrSet(new ExampleObject(1));

    assertSame(a, b);
  }

  /**
   * Asserts that the garbage collector does not throw away still used objects
   */
  @Test
  public void testSameObject_AfterGarbageCollector() {
    var cache = new SimpleCache<>();
    var a = cache.getOrSet(new ExampleObject(1));
    var b = cache.getOrSet(new ExampleObject(2));
    assertNotEquals(a, b); // Just to make sure that these are different objects
    assertEquals(cache.size(), 2);

    // remove ref to b
    // noinspection UnusedAssignment
    b = null;

    // Clear garbage
    System.gc();
    // Assert that there is exactly one instance left
    await().atMost(30, TimeUnit.SECONDS).until(() -> cache.size() == 1);

    // Assert that it still contains the right object
    assertFalse(cache.containsKey(new ExampleObject(2)));
    assertTrue(cache.containsKey(new ExampleObject(1)));
  }


  /**
   * Simple Object that can stores a value and overrides #equals and #hashCode, so
   * the store can be tested
   */
  private static class ExampleObject {
    private final int value;

    ExampleObject(int value) {
      this.value = value;
    }

    @Override
    public int hashCode() {
      return value;
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof ExampleObject && ((ExampleObject) obj).value == this.value;
    }
  }
}
