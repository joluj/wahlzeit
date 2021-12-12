package org.joluj.model;

import org.jetbrains.annotations.NotNull;
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
  @NotNull
  public static synchronized HolidayPhotoFactory getInstance() {
    PhotoFactory instance = PhotoFactory.getInstance();

    if (!(instance instanceof HolidayPhotoFactory)) {
      // Since I have modified the PhotoFactory, this case cannot
      // happen. Thus, it's not added to the contract.
      throw new IllegalStateException("PhotoFactory is no instance of HolidayPhotoFactory");
    }

    return (HolidayPhotoFactory) instance;
  }

  /**
   * @methodtype factory
   */
  @Override
  @NotNull
  public HolidayPhoto createPhoto() {
    return new HolidayPhoto();
  }

  /**
   * @throws IllegalArgumentException iff parameter is null
   * @methodtype factory
   */
  @Override
  @NotNull
  public HolidayPhoto createPhoto(@NotNull PhotoId id) {
    AssertionHelper.AssertNotNull(id);
    return new HolidayPhoto(id);
  }

  /**
   * @throws IllegalArgumentException iff parameter is null
   * @methodtype factory
   */
  @Override
  @NotNull
  public HolidayPhoto createPhoto(@NotNull ResultSet rs) throws SQLException {
    AssertionHelper.AssertNotNull(rs);
    return new HolidayPhoto(rs);
  }
}
