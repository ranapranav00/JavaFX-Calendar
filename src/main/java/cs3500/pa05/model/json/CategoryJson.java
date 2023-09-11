package cs3500.pa05.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.paint.Color;

/**
 * Represents a category json
 *
 * @param name The name
 * @param redValue The red value
 * @param greenValue The green value
 * @param blueValue The blue value
 */
public record CategoryJson(
    @JsonProperty("name") String name,
    @JsonProperty("red-value") double redValue,
    @JsonProperty("green-value") double greenValue,
    @JsonProperty("blue-value") double blueValue
) {
}
