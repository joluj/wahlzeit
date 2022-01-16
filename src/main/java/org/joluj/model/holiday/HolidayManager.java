package org.joluj.model.holiday;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joluj.utils.CoordinateCache;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HolidayManager {

  private static final HolidayManager instance = new HolidayManager();

  protected static final CoordinateCache<Holiday> holidays = new CoordinateCache<>();

  protected static final CoordinateCache<HolidayType> holidayTypes = new CoordinateCache<>();

  public static HolidayManager getInstance() {
    return instance;
  }

  @Nullable
  public Holiday getOrCreateHoliday(ResultSet resultSet) throws SQLException {
    synchronized (holidays) {
      var holiday = Holiday.ReadFrom(resultSet);
      if (holiday == null) return null;
      return holidays.getOrSet(holiday);
    }
  }

  @NotNull
  public HolidayType getOrCreateHolidayType(@NotNull String type) {
    synchronized (holidayTypes) {
      return holidayTypes.getOrSet(new HolidayType(type));
    }
  }

}
