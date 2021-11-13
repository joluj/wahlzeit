package org.joluj.model;

import org.wahlzeit.model.PhotoFactory;
import org.wahlzeit.model.PhotoId;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A singleton instance of this class exists in {@link PhotoFactory#instance}.
 */
public class HolidayPhotoFactory extends PhotoFactory {

  /**
   * Public singleton access method.
   */
  public static synchronized HolidayPhotoFactory getInstance() {
    PhotoFactory instance = PhotoFactory.getInstance();

    if (!(instance instanceof HolidayPhotoFactory)) {
      throw new IllegalStateException("PhotoFactory is no instance of HolidayPhotoFactory");
    }

    return (HolidayPhotoFactory) instance;
  }

  /**
   * @methodtype factory
   */
  public HolidayPhoto createPhoto() {
    return new HolidayPhoto();
  }

  /**
   * @methodtype factory
   */
  public HolidayPhoto createPhoto(PhotoId id) {
    return new HolidayPhoto(id);
  }

  /**
   * @methodtype factory
   */
  public HolidayPhoto createPhoto(ResultSet rs) throws SQLException {
    return new HolidayPhoto(rs);
  }
}
