package us.sampaio.slack.command;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import br.com.twsoftware.alfred.data.Data;
import br.com.twsoftware.alfred.object.Objeto;
import lombok.extern.slf4j.Slf4j;
import us.sampaio.slack.models.Attachment;
import us.sampaio.slack.models.Ramal;
import us.sampaio.slack.models.RichMessage;
import us.sampaio.slack.utils.ReadCSV;

@RestController
@Slf4j
@SuppressWarnings("rawtypes")
public class RamalSlackCommand {

     @Value("${slack.commands.ramal.token}")
     private String slackToken;

     @Value("${slack.commands.ramal.user-names}")
     private String usersNames;
     
     private List<Ramal> ramais;
     
     public RamalSlackCommand(){
          read();
     }

     @RequestMapping(value = "/echo", method = RequestMethod.GET)
     public String echo() {

          return String.format("ECHO at: %s", Data.getDataFormatada(new Date(), "dd/MM/yyyy HH:mm:ss.SSS"));
     }
     
     @RequestMapping(value = "/clear", method = RequestMethod.GET)
     public ResponseEntity clear() {
          
          read();
          return ResponseEntity.ok(ramais);
          
     }

    @RequestMapping(value = "/ramal",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RichMessage onReceiveSlashCommand(@RequestParam("token") String token,
                                             @RequestParam("team_id") String teamId,
                                             @RequestParam("team_domain") String teamDomain,
                                             @RequestParam("channel_id") String channelId,
                                             @RequestParam("channel_name") String channelName,
                                             @RequestParam("user_id") String userId,
                                             @RequestParam("user_name") String userName,
                                             @RequestParam("command") String command,
                                             @RequestParam("text") String text,
                                             @RequestParam("response_url") String responseUrl) {
         
         List<String> users = Lists.newArrayList(Splitter.on(";").omitEmptyStrings().split(usersNames));
        
        if (!token.equals(slackToken) || Objeto.isBlank(users) || !users.contains(userName)) {
            return new RichMessage("Desculpe! Você não tem permissão para utilizar esse comando.");
        }
        
        if(Objeto.isBlank(ramais)){
             return new RichMessage("Desculpe! Não foi possível carregar nenhum número.");
        }
        
        List<Ramal> result = ramais.stream()
                                      .filter(r -> r.getSetor().contains(text) || r.getRamal().contains(text) || r.getNome().contains(text))
                                      .collect(Collectors.toList());
        
        if(Objeto.isBlank(result)){
             return new RichMessage("Desculpe! Nada foi encontrado");
        }
        
        RichMessage richMessage = new RichMessage("Encontrei os seguintes números:");
        richMessage.setResponseType("in_channel");
        
        Attachment[] attachments = new Attachment[1];
        attachments[0] = new Attachment();
        attachments[0].setText(toString(result));
        richMessage.setAttachments(attachments);
        
        if (log.isDebugEnabled()) {
            try {
                log.debug("Reply (RichMessage): {}", new ObjectMapper().writeValueAsString(richMessage));
            } catch (JsonProcessingException e) {
                log.debug("Error parsing RichMessage: ", e);
            }
        }
        
        return richMessage.encodedMessage(); 
        
    }
    
     private String toString(List<Ramal> r) {

          StringBuilder s = new StringBuilder();
          if (Objeto.notBlank(r)) {
               s.append(r.toString());
          }
          return s.toString();
     }
    
     public void read() {
          
          ramais = Lists.newArrayList();
          try {
               
               URL resource = ReadCSV.class.getClassLoader().getResource("o.csv");
               List<String> lines = Files.readLines(new File(resource.getFile()), Charsets.UTF_8);

               if (Objeto.notBlank(lines)) {

                    for (String line : lines) {

                         String[] split = line.split(";");
                         Ramal r = new Ramal();
                         r.setNome(split[0]);
                         r.setSetor(split[1]);
                         r.setRamal(split[2]);
                         ramais.add(r);

                    }

               }

          } catch (Exception e) {
               log.error("Erro ao carregar o CSV", e);
          }

     }  
    
}