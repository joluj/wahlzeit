package org.joluj.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoId;
import org.wahlzeit.services.SysLog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HolidayPhoto extends Photo {

  @Nullable
  private String country;

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
  public String getCountry() {
    return country;
  }

  protected void setCountry(@Nullable String country) {
    this.country = country;
    incWriteCount();
  }

  @Override
  public void readFrom(@NotNull ResultSet rset) throws SQLException {
    SysLog.logSysInfo("HolidayPhoto#readFrom");

    super.readFrom(rset);

    // New attributes for *Holiday*Photo
    country = rset.getString("country");
  }

  @Override
  public void writeOn(@NotNull ResultSet rset) throws SQLException {
    SysLog.logSysInfo("HolidayPhoto#writeOn");
    super.writeOn(rset);

    // New attributes for *Holiday*Photo
    rset.updateString("country", country);
  }
}
