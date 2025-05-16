package projeto.es;

import java.util.HashMap;

public class MunicipiosScore {

     static final HashMap<String, Integer> municipios = new HashMap<>();

     public static int getScore(String municipio) {
          municipios.put("Funchal", 5);
          municipios.put("Calheta", 5);
          municipios.put("Ponta do Sol", 4);
          municipios.put("Ribeira Brava", 4);
          municipios.put("Câmara de Lobos", 3);
          municipios.put("Santa Cruz", 3);
          municipios.put("Machico", 3);
          municipios.put("São Vicente", 2);
          municipios.put("Santana", 1);

          try{
               return municipios.get(municipio);
          } catch (NullPointerException e) {
               return 0;
          }
     }
}
