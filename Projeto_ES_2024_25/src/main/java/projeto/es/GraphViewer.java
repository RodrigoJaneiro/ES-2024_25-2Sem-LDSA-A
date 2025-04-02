package projeto.es;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GraphViewer {
    private static final String PATH = "src/main/resources/graphs/";

    public static ImageView drawGraphVizinhos() throws IOException {
        // Define o grafo DOT
        String dotGraph = "graph G { 1 -- 2; 2 -- 3; 3 -- 1; }";

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