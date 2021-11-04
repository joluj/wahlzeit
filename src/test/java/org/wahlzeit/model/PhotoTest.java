package org.wahlzeit.model;

import org.joluj.model.Coordinate;
import org.joluj.model.Location;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

public class PhotoTest {

  @Test
  public void location() {
    var location = new Location(new Coordinate(1, 2.3, 4.5));
    var photo = new Photo();

    assertNull(photo.getLocation());
    photo.setLocation(location);
    assertEquals(photo.getLocation(), location);
  }
}
