package projeto.es;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.stream.Collectors;

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
                        } else if (exerciseNumber == 4) {
                            // Criação da interface para o exercício 4
                            VBox vbox = new VBox(10);
                            vbox.setAlignment(Pos.CENTER);
                            vbox.setPadding(new Insets(20));

                            ComboBox<String> tipoAreaCombo = new ComboBox<>();
                            tipoAreaCombo.getItems().addAll("Freguesia", "Municipio", "Ilha");
                            tipoAreaCombo.setPromptText("Selecione o tipo de área");

                            ComboBox<String> nomeAreaCombo = new ComboBox<>();
                            nomeAreaCombo.setPromptText("Selecione a área");

                            tipoAreaCombo.setOnAction(event -> {
                                String tipoSelecionado = tipoAreaCombo.getValue();
                                nomeAreaCombo.getItems().clear();
                                switch (tipoSelecionado) {
                                    case "Freguesia":
                                        nomeAreaCombo.getItems().addAll(
                                                graphViewer.getPropriedades().stream()
                                                        .map(Propriedade::getFreguesia)
                                                        .distinct()
                                                        .collect(Collectors.toList())
                                        );
                                        break;
                                    case "Municipio":
                                        nomeAreaCombo.getItems().addAll(
                                                graphViewer.getPropriedades().stream()
                                                        .map(Propriedade::getMunicipio)
                                                        .distinct()
                                                        .collect(Collectors.toList())
                                        );
                                        break;
                                    case "Ilha":
                                        nomeAreaCombo.getItems().addAll(
                                                graphViewer.getPropriedades().stream()
                                                        .map(Propriedade::getIlha)
                                                        .distinct()
                                                        .collect(Collectors.toList())
                                        );
                                        break;
                                }
                            });

                            Button calcularBtn = new Button("Calcular Área Média");
                            Label resultadoLabel = new Label();

                            calcularBtn.setOnAction(event -> {
                                try {
                                    String tipoArea = tipoAreaCombo.getValue();
                                    String nomeArea = nomeAreaCombo.getValue();
                                    if (tipoArea == null || nomeArea == null) {
                                        resultadoLabel.setText("Selecione o tipo e o nome da área.");
                                        return;
                                    }

                                    double areaMedia = AreaCalculator.calcularAreaMedia(
                                            graphViewer.getPropriedades(),
                                            tipoArea,
                                            nomeArea
                                    );
                                    resultadoLabel.setText(String.format(
                                            "Área média das propriedades em %s (%s): %.2f m²",
                                            nomeArea, tipoArea, areaMedia
                                    ));
                                } catch (Exception ex) {
                                    resultadoLabel.setText("Erro: " + ex.getMessage());
                                }
                            });

                            vbox.getChildren().addAll(
                                    new Label("Selecione a área geográfica:"),
                                    tipoAreaCombo,
                                    nomeAreaCombo,
                                    calcularBtn,
                                    resultadoLabel
                            );

                            scrollPane.setContent(vbox);
                        }

                        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

                        return scrollPane;
                    }
                };

                task.setOnSucceeded(event -> {
                    contentPane.getChildren().clear();
                    contentPane.getChildren().add(task.getValue());
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