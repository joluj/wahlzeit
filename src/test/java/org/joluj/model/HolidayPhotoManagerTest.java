package org.joluj.model;

import org.junit.Test;
import org.wahlzeit.model.PhotoManager;

import static org.junit.Assert.assertTrue;

public class HolidayPhotoManagerTest {

  @Test
  public void genericManagerInstanceOfHolidayPhotoManager() {
    var manager = PhotoManager.getInstance();
    assertTrue(manager instanceof HolidayPhotoManager);
  }

}
