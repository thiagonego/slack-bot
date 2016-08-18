
package us.sampaio.slack.models;

@lombok.Data
public class Ramal{

     public String nome = "";

     public String ramal = "";

     public String setor = "";
     
     public String toString(){
          return String.format(" %s - %s \n", nome, ramal);
     }
}