package projeto.es;

import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            List<Propriedade> propriedades = CSVLoader.LoadPropriedades("Madeira");
            System.out.println(propriedades.get(0).toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
