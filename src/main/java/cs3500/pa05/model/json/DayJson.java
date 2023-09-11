package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * A dayJson
 *
 * @param events a list of eventJsons
 * @param tasks a list of taskJsons
 */
public record DayJson(
    @JsonProperty("events") List<EventJson> events,
    @JsonProperty("tasks") List<TaskJson> tasks
) {
}
