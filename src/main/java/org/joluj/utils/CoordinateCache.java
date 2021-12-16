package org.joluj.utils;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * Cache for coordinates, which gets cleared up regularly through the garbage collector.
 * I.e., if there is no reference to an instance of the coordinate (except in the cache),
 * the coordinate will be cleared automatically.
 * <p>
 * Uses the #equals and #hashMap method.
 */
public class CoordinateCache<T> {
  private final WeakHashMap<T, WeakReference<T>> instances = new WeakHashMap<>();

  /**
   * If there is a coordinate present, that equals the parameter, then the cached coordinate is returned. <br/>
   * If not, then the parameter is put into the cache and is returned.
   */
  @NotNull
  public T getOrSet(@NotNull T coordinate) {
    // this will return the currently cached coordinate, or the new value if it does not already exist.
    var weakRef = instances.get(coordinate);
    if (weakRef == null) {
      weakRef = new WeakReference<>(coordinate);
      instances.put(coordinate, weakRef);
    }

    // post-condition
    var returnValue = weakRef.get();
    assert returnValue != null;

    return returnValue;
  }

  /**
   * Returns true iff the cache contains the given key.
   */
  public boolean containsKey(@NotNull T coordinate) {
    return this.instances.containsKey(coordinate);
  }

  /**
   * Returns true if there are no items in the cache.
   */
  public boolean isEmpty() {
    return this.instances.isEmpty();
  }

  /**
   * Returns the number of elements in the cache.
   */
  public int size() {
    return this.instances.size();
  }
}
