
package us.sampaio.slack.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Base64Utils;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import br.com.twsoftware.alfred.net.WorldWideWeb;

public class Base64{

     public static File projectDir() throws IOException {

          ClassPathResource pathfileRes = new ClassPathResource("application.yml");

          File projectDir = pathfileRes.getFile().getParentFile().getParentFile().getParentFile();
          if (!projectDir.exists()) {
               throw new RuntimeException("Não foi possível encontrar a raiz do projeto");
          }

          return projectDir;
     }

     public static File getArquivoFinal() throws IOException {

          File projectDir = projectDir();
          File arquivoFinal = new File(projectDir, "src/main/resources/f.csv");
          
          if (!arquivoFinal.exists()) {
               throw new RuntimeException("Não foi possível encontrar o arquivo final com os ramais");
          } else {
               Files.touch(arquivoFinal);
          }

          return arquivoFinal;

     }

     public static File getArquivoOrigem() throws IOException {

          File projectDir = projectDir();
          File arquivoOrigem = new File(projectDir, "o.csv");
          if (!arquivoOrigem.exists()) {
               throw new RuntimeException("Não foi possível encontrar o arquivo de origem com os ramais");
          }

          return arquivoOrigem;
     }

     public static void encode() throws IOException {

          File arquivoOrigem = getArquivoOrigem();
          File arquivoFinal = getArquivoFinal();

          String encoded = Base64Utils.encodeToString(Files.toString(arquivoOrigem, Charsets.UTF_8).getBytes());
          Files.write(encoded, arquivoFinal, Charsets.UTF_8);

     }

     public static List<String> decode(String csvHerokuPath) throws IOException {
          
          String conteudoSite = IOUtils.toString(WorldWideWeb.getConteudoArquivo(csvHerokuPath));
          String decoded = new String(Base64Utils.decodeFromString(conteudoSite));
          return Lists.newArrayList(Splitter.on("\n").omitEmptyStrings().split(decoded));

     }

     public static void delete() throws IOException {

          File arquivoOrigem = getArquivoOrigem();
          if (arquivoOrigem.exists()) {
               arquivoOrigem.delete();
          }
          
     }
     
     public static void main(String[] args) throws IOException {

          Base64.encode();
          Base64.delete();
          
     }

}
