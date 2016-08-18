
package us.sampaio.slack.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class RichMessage{

     private String username;

     @JsonProperty("icon_emoji")
     private String iconEmoji;

     private String channel;

     private String text;

     @JsonProperty("response_type")
     private String responseType;

     private Attachment[] attachments;

     public RichMessage(){
     }

     public RichMessage(String text){
          this.text = text;
     }

     public RichMessage encodedMessage() {

          this.setText(this.getText().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")); 
          return this;
     }

}