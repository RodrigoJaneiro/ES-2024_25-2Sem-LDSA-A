package projeto.es;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The type Application viewer.
 */
public class ApplicationViewer extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showMainMenu();
    }

    private void showMainMenu() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        for (int i = 2; i <= 7; i++) {
            Button btn = new Button("Ex" + i);
            btn.setMinWidth(100);
            btn.setMinHeight(100);
            final int exerciseNumber = i;

            btn.setOnAction(e -> {
                StackPane contentPane = new StackPane();

                Stage exerciseStage = new Stage();
                exerciseStage.setTitle("Exercício " + exerciseNumber);

                ProgressIndicator progress = new ProgressIndicator();
                contentPane.getChildren().add(progress);

                Scene scene = new Scene(contentPane, 800, 600);
                exerciseStage.setScene(scene);
                exerciseStage.show();

                Task<ScrollPane> task = new Task<ScrollPane>() {
                    @Override
                    protected ScrollPane call() throws Exception {
                        GraphViewer graphViewer = new GraphViewer(CSVLoader.LoadPropriedades("Madeira"));
                        ScrollPane scrollPane = new ScrollPane();

                        if (exerciseNumber == 2) {
                            scrollPane.setContent(graphViewer.drawGraphVizinhos());
                        }

                        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

                        return scrollPane;
                    }
                };

                task.setOnSucceeded(event -> {
                    contentPane.getChildren().clear();

                    ScrollPane scrollPane = task.getValue();
                    contentPane.getChildren().add(scrollPane);
                });

                task.setOnFailed(event -> {
                    contentPane.getChildren().clear();
                    contentPane.getChildren().add(new Label("Erro ao carregar: " +
                            task.getException().getMessage()));
                });

                new Thread(task).start();
            });

            grid.add(btn, (i - 2) % 3, (i - 2) / 3);
        }

        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setTitle("Menu Principal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}