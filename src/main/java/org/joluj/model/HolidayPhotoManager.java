package org.joluj.model;

import org.wahlzeit.model.PhotoManager;
import org.wahlzeit.services.SysLog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HolidayPhotoManager extends PhotoManager {

  public HolidayPhotoManager() {
    super();
    SysLog.logSysInfo("HolidayPhotoManager created");
    photoTagCollector = HolidayPhotoFactory.getInstance().createPhotoTagCollector();
  }


  protected HolidayPhoto createObject(ResultSet rset) throws SQLException {
    return HolidayPhotoFactory.getInstance().createPhoto(rset);
  }
}
