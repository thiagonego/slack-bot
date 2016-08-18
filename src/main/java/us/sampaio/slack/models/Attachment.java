
package us.sampaio.slack.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Attachment{

     private String fallback;

     private String color;

     private String pretext;

     @JsonProperty("author_name")
     private String authorName;

     @JsonProperty("author_link")
     private String authorLink;

     @JsonProperty("author_icon")
     private String authorIcon;

     private String title;

     @JsonProperty("title_link")
     private String titleLink;

     private String text;

     private Field[] fields;

     @JsonProperty("image_url")
     private String imageUrl;

     @JsonProperty("thumb_url")
     private String thumbUrl;

     private String footer;

     @JsonProperty("footer_icon")
     private String footerIcon;

     private String ts;

}
