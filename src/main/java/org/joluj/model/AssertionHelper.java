package org.joluj.model;

public class AssertionHelper {

  /**
   * Asserts that obj is not null
   *
   * @throws IllegalArgumentException iff obj is null
   */
  public static void AssertNotNull(Object obj) {
    if (obj == null) throw new IllegalArgumentException("Argument is null");
  }
}
