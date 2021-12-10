package org.joluj.model;

import org.jetbrains.annotations.NotNull;
import org.wahlzeit.model.PhotoManager;
import org.wahlzeit.services.SysLog;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A singleton instance of this class exists in {@link PhotoManager#instance}.
 */
public class HolidayPhotoManager extends PhotoManager {

  public HolidayPhotoManager() {
    super();
    SysLog.logSysInfo("HolidayPhotoManager created");
    photoTagCollector = HolidayPhotoFactory.getInstance().createPhotoTagCollector();
  }

  @Override
  protected HolidayPhoto createObject(@NotNull ResultSet rset) throws SQLException {
    ExceptionHelper.AssertNotNull(rset);
    return HolidayPhotoFactory.getInstance().createPhoto(rset);
  }
}
