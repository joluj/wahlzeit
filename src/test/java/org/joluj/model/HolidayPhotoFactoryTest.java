package org.joluj.model;

import org.junit.Test;
import org.wahlzeit.model.PhotoFactory;

import static org.junit.Assert.assertTrue;

public class HolidayPhotoFactoryTest {

  @Test
  public void genericFactoryInstanceOfHolidayPhotoFactory() {
    var factory = PhotoFactory.getInstance();
    assertTrue(factory instanceof HolidayPhotoFactory);
  }

  @Test
  public void genericFactoryReturnsHolidayPhoto() {
    var photo = PhotoFactory.getInstance().createPhoto();
    assertTrue(photo instanceof HolidayPhoto);
  }

}
