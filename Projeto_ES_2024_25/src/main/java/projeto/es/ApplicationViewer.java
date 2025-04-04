package projeto.es;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ApplicationViewer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        GraphViewer graphViewer = new GraphViewer(CSVLoader.LoadPropriedades("Madeira"));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(graphViewer.drawGraphVizinhos());
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        StackPane root = new StackPane(scrollPane);
        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Grafo Não Direcionado");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}