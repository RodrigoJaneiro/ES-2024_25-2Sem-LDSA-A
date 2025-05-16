package projeto.es;

import java.util.HashMap;

public class FreguesiaScore {

     static final HashMap<String, Integer> freguesias = new HashMap<>();

     public static int getScore(String freguesia) {
          freguesias.put("Água de Pena", 3);
          freguesias.put("Água de São Jorge", 2);
          freguesias.put("Arco da Calheta", 4);
          freguesias.put("Arco de São Jorge", 2);
          freguesias.put("Calheta", 5);
          freguesias.put("Camacha", 3);
          freguesias.put("Campanário", 3);
          freguesias.put("Caniçal", 3);
          freguesias.put("Caniço", 4);
          freguesias.put("Curral das Freiras", 2);
          freguesias.put("Estreito da Calheta", 4);
          freguesias.put("Estreito de Câmara de Lobos", 3);
          freguesias.put("Fajã da Ovelha", 2);
          freguesias.put("Fajã do Penedo", 1);
          freguesias.put("Faial", 2);
          freguesias.put("Fonte do Bispo", 2);
          freguesias.put("Gaula", 3);
          freguesias.put("Jardim da Serra", 2);
          freguesias.put("Jardim do Mar", 2);
          freguesias.put("Machico", 3);
          freguesias.put("Madalena do Mar", 2);
          freguesias.put("Ponta Delgada", 2);
          freguesias.put("Ponta do Pargo", 2);
          freguesias.put("Ponta do Sol", 4);
          freguesias.put("Porto da Cruz", 3);
          freguesias.put("Porto Moniz", 3);
          freguesias.put("Prazeres", 3);
          freguesias.put("Ribeira Brava", 4);
          freguesias.put("Ribeira das Tainhas", 1);
          freguesias.put("Ribeira Funda", 1);
          freguesias.put("Ribeira Quente", 1);
          freguesias.put("Ribeira Seca", 2);
          freguesias.put("Ribeirinha", 1);
          freguesias.put("Santa Cruz", 3);
          freguesias.put("Santa Luzia", 4);
          freguesias.put("Santa Maria Maior", 5);
          freguesias.put("Santana", 2);
          freguesias.put("Santo António", 4);
          freguesias.put("Santo da Serra", 3);
          freguesias.put("São Gonçalo", 4);
          freguesias.put("São Jorge", 2);
          freguesias.put("São Martinho", 5);
          freguesias.put("São Pedro", 5);
          freguesias.put("São Roque", 3);
          freguesias.put("São Vicente", 3);
          freguesias.put("Se", 5);
          freguesias.put("Serra de Água", 3);
          freguesias.put("Tabua", 3);
          freguesias.put("Terra Chã", 1);
          freguesias.put("Vila Baleira", 4);
          freguesias.put("Imaculado Coração de Maria", 4);
          freguesias.put("Boa Ventura", 2);
          freguesias.put("Canhas", 3);
          freguesias.put("Paul do Mar", 2);
          freguesias.put("Sao João", 2);

          try{
               return freguesias.get(freguesia);
          } catch (NullPointerException e) {
               return 0;
          }
     }
}
