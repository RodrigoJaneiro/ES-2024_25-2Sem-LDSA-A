package projeto.es;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class GraphViewer {
    private static final String PATH = "src/main/resources/graphs/";
    private List <Propriedade> propriedades;

    public GraphViewer(List <Propriedade> propriedades) {
        this.propriedades = propriedades;
    }

    public List <Propriedade> getPropriedades() {
        return propriedades;
    }

    public void setPropriedades(List <Propriedade> propriedades) {
        this.propriedades = propriedades;
    }

    public ImageView drawGraphVizinhos() throws IOException {
        List<List<Integer>> vizinhos = Calculations.encontrarVizinhos(propriedades);

        // Define o grafo DOT
        String dotGraph = "graph G {";
        for (List<Integer> par : vizinhos) {
            dotGraph += " " + par.get(0) + " -- " + par.get(1) + ";";
        }
        dotGraph += " }";
        
        // Cria o diretório se não existir
        File outputDir = new File(PATH);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Arquivo de saída
        File outputFile = new File(PATH + "ex2.png");
 
        // Renderiza e salva o gráfico
        Graphviz.fromString(dotGraph)
                .render(Format.PNG)
                .toFile(outputFile);

        // Lê o arquivo salvo para exibir no JavaFX
        byte[] imageBytes = Files.readAllBytes(outputFile.toPath());
        return new ImageView(new Image(new ByteArrayInputStream(imageBytes)));
    }
}