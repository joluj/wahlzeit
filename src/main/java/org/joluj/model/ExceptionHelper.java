package org.joluj.model;

public class ExceptionHelper {

  /**
   * Asserts that obj is not null
   *
   * @throws IllegalArgumentException iff obj is null
   */
  static void AssertNotNull(Object obj) {
    if (obj == null) throw new IllegalArgumentException("Argument is null");
  }
}
