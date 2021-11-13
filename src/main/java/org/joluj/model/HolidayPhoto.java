package org.joluj.model;

import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoId;
import org.wahlzeit.services.SysLog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HolidayPhoto extends Photo {

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


  public String getCountry() {
    return country;
  }

  protected void setCountry(String country) {
    this.country = country;
    incWriteCount();
  }

  @Override
  public void readFrom(ResultSet rset) throws SQLException {
    SysLog.logSysInfo("HolidayPhoto#readFrom");

    super.readFrom(rset);

    // New attributes for *Holiday*Photo
    country = rset.getString("country");
  }

  @Override
  public void writeOn(ResultSet rset) throws SQLException {
    SysLog.logSysInfo("HolidayPhoto#writeOn");
    super.writeOn(rset);

    // New attributes for *Holiday*Photo
    rset.updateString("country", country);
  }
}
