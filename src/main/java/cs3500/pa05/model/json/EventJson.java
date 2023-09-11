package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Category;
import cs3500.pa05.model.Day;

/**
 * Represents an eventJson
 *
 * @param name the name
 * @param description the description
 * @param day the day
 * @param category the category
 * @param startTime the startTime
 * @param duration the duration
 */
public record EventJson(
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("day-of-week") Day day,
    @JsonProperty("category") CategoryJson category,
    @JsonProperty("start-time") TimeJson startTime,
    @JsonProperty("duration") int duration
) {
}