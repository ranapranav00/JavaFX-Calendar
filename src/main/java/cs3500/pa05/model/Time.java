package cs3500.pa05.model;

/**
 * The time object
 */
public class Time {
  private int hour;
  private int minute;
  private Meridian meridian;

  /**
   * A constructor for the time object
   *
   * @param hour the hour
   * @param minute the minute
   * @param meridian the meridian
   */
  public Time(int hour, int minute, Meridian meridian) {
    this.hour = hour;
    this.minute = minute;
    this.meridian = meridian;
  }

  /**
   * Gets the hour
   *
   * @return the hour
   */
  public int getHour() {
    return hour;
  }

  /**
   * Gets the minute
   *
   * @return the minute
   */
  public int getMinute() {
    return minute;
  }

  /**
   * Gets the meridian
   *
   * @return the meridian
   */
  public Meridian getMeridian() {
    return meridian;
  }
}
