
package us.sampaio.slack.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import br.com.twsoftware.alfred.object.Objeto;
import us.sampaio.slack.models.Ramal;

public class ReadCSV{

     public static List<Ramal> read() throws IOException {

          List<Ramal> ramais = Lists.newArrayList();
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

          return ramais;

     }

}
