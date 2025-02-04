/*
 * SPDX-FileCopyrightText: 2006-2009 Dirk Riehle <dirk@riehle.org> https://dirkriehle.com
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.wahlzeit.model;

import java.sql.*;

import org.jetbrains.annotations.NotNull;
import org.joluj.model.HolidayPhotoFactory;
import org.joluj.utils.PatternInstance;
import org.wahlzeit.services.*;

/**
 * An Abstract Factory for creating photos and related objects.
 */
@PatternInstance(
    patternName = "Abstract Factory",
    participants = {"AbstractFactory", "ConcreteFactory"}
)
@PatternInstance(
    patternName = "Singleton"
)
public class PhotoFactory {

  /**
   * Hidden singleton instance; needs to be initialized from the outside.
   */
  private static PhotoFactory instance = null;

  /**
   * Public singleton access method.
   */
  @NotNull
  public static synchronized PhotoFactory getInstance() {
    if (instance == null) {
      SysLog.logSysInfo("setting app-specific HolidayPhotoFactory");
      setInstance(new HolidayPhotoFactory());
    }

    return instance;
  }

  /**
   * Method to set the singleton instance of PhotoFactory.
   *
   * @throws IllegalStateException    if it was initialized before
   * @throws IllegalArgumentException if the parameter is not an instance of {@link HolidayPhotoFactory}
   */
  protected static synchronized void setInstance(PhotoFactory photoFactory) {
    if (instance != null) {
      throw new IllegalStateException("attempt to initialize PhotoFactory twice");
    }
    if (!(photoFactory instanceof HolidayPhotoFactory)) {
      throw new IllegalStateException("PhotoFactory is no instance of HolidayPhotoFactory");
    }

    instance = photoFactory;
  }

  /**
   * Hidden singleton instance; needs to be initialized from the outside.
   */
  public static void initialize() {
    getInstance(); // drops result due to getInstance() side-effects
  }

  /**
   *
   */
  protected PhotoFactory() {
    // do nothing
  }

  /**
   * @methodtype factory
   */
  public Photo createPhoto() {
    return new Photo();
  }

  /**
   *
   */
  public Photo createPhoto(PhotoId id) {
    return new Photo(id);
  }

  /**
   *
   */
  public Photo createPhoto(ResultSet rs) throws SQLException {
    return new Photo(rs);
  }

  /**
   *
   */
  public PhotoFilter createPhotoFilter() {
    return new PhotoFilter();
  }

  /**
   *
   */
  public PhotoTagCollector createPhotoTagCollector() {
    return new PhotoTagCollector();
  }

}
