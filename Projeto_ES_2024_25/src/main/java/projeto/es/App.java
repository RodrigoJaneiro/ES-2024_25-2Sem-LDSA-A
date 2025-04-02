package projeto.es;
import javafx.application.Application;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
      Application.launch(ApplicationViewer.class, args);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha o nível administrativo (freguesia, concelho, distrito): ");
        String nivel = scanner.nextLine();

        System.out.println("Digite o nome da área geográfica: ");
        String nomeArea = scanner.nextLine();

        try {
            List<Propriedade> propriedades = CSVLoader.LoadPropriedades(nivel, nomeArea);

            if (propriedades.isEmpty()) {
                System.out.println("Nenhuma propriedade encontrada para a área especificada.");
            } else {
                double areaMedia = calcularAreaMedia(propriedades);
                System.out.println("Área média das propriedades em " + nomeArea + ": " + areaMedia);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar propriedades: " + e.getMessage());
        }

        scanner.close();
    }

    private static double calcularAreaMedia(List<Propriedade> propriedades) {
        if (propriedades.isEmpty()) return 0;

        double somaAreas = 0;
        for (Propriedade prop : propriedades) {
            somaAreas += prop.getShape_area();
        }

        return somaAreas / propriedades.size();
    }
}