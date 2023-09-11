package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Meridian;

/**
 * The time json
 *
 * @param hour the hour
 * @param minute the minute
 * @param meridian the meridian (AM/PM)
 */
public record TimeJson(
    @JsonProperty("hour") int hour,
    @JsonProperty("minute") int minute,
    @JsonProperty("meridian") Meridian meridian
) {
}
