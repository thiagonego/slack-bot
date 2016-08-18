
package us.sampaio.slack.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Field{

     private String title;

     private String value;

     @JsonProperty("short_enough")
     private boolean shortEnough;

}