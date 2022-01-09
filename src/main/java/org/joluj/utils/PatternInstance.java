package org.joluj.utils;

import java.lang.annotation.Repeatable;

/**
 * Describes a Design Pattern.
 */
@Repeatable(PatternInstances.class)
public @interface PatternInstance {
  String patternName();

  String[] participants() default {};

  String description() default "";
}
