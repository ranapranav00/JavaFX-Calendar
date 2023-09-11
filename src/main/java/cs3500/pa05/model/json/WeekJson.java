package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Day;
import java.util.List;

/**
 * Represents a weekJson
 *
 * @param name the name
 * @param maxEvents the max Events
 * @param maxTasks the max tasks
 * @param startsWith the startsWith day
 * @param categories the categories list
 * @param password the password
 * @param sunday the sunday dayJson
 * @param monday the monday dayJson
 * @param tuesday the tuesday dayJson
 * @param wednesday the wednesday dayJson
 * @param thursday the thursday dayJson
 * @param friday the friday dayJson
 * @param saturday the saturday dayJson
 */
public record WeekJson(
    @JsonProperty("name") String name,
    @JsonProperty("max-events") int maxEvents,
    @JsonProperty("max-tasks") int maxTasks,
    @JsonProperty("starts-with") Day startsWith,
    @JsonProperty("all-categories") List<CategoryJson> categories,
    @JsonProperty("password") String password,
    @JsonProperty("sunday") DayJson sunday,
    @JsonProperty("monday") DayJson monday,
    @JsonProperty("tuesday") DayJson tuesday,
    @JsonProperty("wednesday") DayJson wednesday,
    @JsonProperty("thursday") DayJson thursday,
    @JsonProperty("friday") DayJson friday,
    @JsonProperty("saturday") DayJson saturday
) {
}
