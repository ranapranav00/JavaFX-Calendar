package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Day;

/**
 * Represents the taskJson
 *
 * @param name the name
 * @param description the description
 * @param day the day
 * @param category the category
 * @param isComplete the completion status
 */
public record TaskJson(
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("day-of-week") Day day,
    @JsonProperty("category") CategoryJson category,
    @JsonProperty("is-complete") Boolean isComplete) {
}
