package org.joluj.utils;

/**
 * Allows multiple {@link PatternInstance} annotations
 * at a single class
 */
public @interface PatternInstances {

  PatternInstance[] value();

}
