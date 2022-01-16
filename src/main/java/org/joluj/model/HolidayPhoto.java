package org.joluj.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joluj.model.holiday.Holiday;
import org.joluj.model.holiday.HolidayManager;
import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoId;
import org.wahlzeit.services.SysLog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HolidayPhoto extends Photo {

  @Nullable
  private Holiday holiday;

  /**
   * @methodtype constructor
   */
  public HolidayPhoto() {
    super();
  }

  /**
   * @methodtype constructor
   */
  public HolidayPhoto(PhotoId myId) {
    super(myId);
  }

  /**
   * @methodtype constructor
   */
  public HolidayPhoto(ResultSet rset) throws SQLException {
    super(rset);
  }

  @Nullable
  public Holiday getHoliday() {
    return holiday;
  }

  protected void setHoliday(@Nullable Holiday holiday) {
    this.holiday = holiday;
    incWriteCount();
  }

  @Override
  public void readFrom(@NotNull ResultSet rset) throws SQLException {
    SysLog.logSysInfo("HolidayPhoto#readFrom");

    super.readFrom(rset);

    // New attributes for *Holiday*Photo
    this.holiday = HolidayManager.getInstance().getOrCreateHoliday(rset);
  }

  @Override
  public void writeOn(@NotNull ResultSet rset) throws SQLException {
    SysLog.logSysInfo("HolidayPhoto#writeOn");
    super.writeOn(rset);

    // New attributes for *Holiday*Photo
    if (this.holiday != null) {
      this.holiday.writeOn(rset);
    }
  }
}
