package projeto.es;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;

import java.io.File;

import static guru.nidi.graphviz.model.Factory.*;

public class GraphvizExample {

    public static void main(String[] args) {
        try {
            generateGraph();
        } catch (Exception e) {
            System.out.println("Erro ao gerar o grafo: " + e.getMessage());
        }
    }

    private static void generateGraph() throws Exception {

        MutableGraph g = mutGraph("example").setDirected(false)
                .add(mutNode("Property1").addLink("Property2"))
                .add(mutNode("Property2").addLink("Property3"));

        Graphviz.fromGraph(g).width(800).render(Format.PNG).toFile(new File("src/main/resources/graphs/example.png"));
        System.out.println("Grafo gerado com sucesso!");
    }
}
