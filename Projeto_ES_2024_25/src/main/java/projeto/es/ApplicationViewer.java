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

import java.util.List;
import java.util.stream.Collectors;

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
                        else if (exerciseNumber == 4 || exerciseNumber == 5) {
                            scrollPane.setContent(createAreaCalculationUI(graphViewer, exerciseNumber == 5));
                        }

                        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                        return scrollPane;
                    }
                };

                task.setOnSucceeded(event -> contentPane.getChildren().setAll(task.getValue()));
                task.setOnFailed(event -> contentPane.getChildren().setAll(
                        new Label("Erro: " + task.getException().getMessage())));

                new Thread(task).start();
            });
            grid.add(btn, (i - 2) % 3, (i - 2) / 3);
        }

        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setTitle("Menu Principal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createAreaCalculationUI(GraphViewer graphViewer, boolean groupAdjacent) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        ComboBox<String> tipoAreaCombo = new ComboBox<>();
        tipoAreaCombo.getItems().addAll("Freguesia", "Municipio", "Ilha");
        tipoAreaCombo.setPromptText("Selecione o tipo de área");

        ComboBox<String> nomeAreaCombo = new ComboBox<>();
        nomeAreaCombo.setPromptText("Selecione a área");

        tipoAreaCombo.setOnAction(event -> updateAreaCombo(tipoAreaCombo.getValue(), nomeAreaCombo, graphViewer));

        Button calcularBtn = new Button(groupAdjacent ?
                "Calcular Área Média (Agrupando Adjacentes)" : "Calcular Área Média");
        Label resultadoLabel = new Label();

        calcularBtn.setOnAction(event -> calculateArea(
                tipoAreaCombo.getValue(),
                nomeAreaCombo.getValue(),
                graphViewer,
                resultadoLabel,
                groupAdjacent
        ));

        vbox.getChildren().addAll(
                new Label("Selecione a área geográfica:"),
                tipoAreaCombo,
                nomeAreaCombo,
                calcularBtn,
                resultadoLabel
        );

        return vbox;
    }

    private void updateAreaCombo(String tipoSelecionado, ComboBox<String> nomeAreaCombo, GraphViewer graphViewer) {
        nomeAreaCombo.getItems().clear();
        if (tipoSelecionado != null) {
            nomeAreaCombo.getItems().addAll(
                    graphViewer.getPropriedades().stream()
                            .map(prop -> {
                                switch (tipoSelecionado) {
                                    case "Freguesia": return prop.getFreguesia();
                                    case "Municipio": return prop.getMunicipio();
                                    case "Ilha": return prop.getIlha();
                                    default: return "";
                                }
                            })
                            .distinct()
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toList())
            );
        }
    }

    private void calculateArea(String tipoArea, String nomeArea, GraphViewer graphViewer,
                               Label resultadoLabel, boolean groupAdjacent) {
        try {
            if (tipoArea == null || nomeArea == null) {
                resultadoLabel.setText("Selecione o tipo e o nome da área.");
                return;
            }

            double areaMedia;
            int originalCount, processedCount;

            if (groupAdjacent) {
                List<Propriedade> agrupadas = Calculations.agruparPropriedadesAdjacentes(
                        graphViewer.getPropriedades().stream()
                                .filter(prop -> AreaCalculator.filtraPorArea(prop, tipoArea, nomeArea))
                                .collect(Collectors.toList())
                );
                originalCount = (int) graphViewer.getPropriedades().stream()
                        .filter(prop -> AreaCalculator.filtraPorArea(prop, tipoArea, nomeArea))
                        .count();
                processedCount = agrupadas.size();
                areaMedia = agrupadas.stream()
                        .mapToDouble(Propriedade::getShape_area)
                        .average()
                        .orElse(0);
            } else {
                areaMedia = AreaCalculator.calcularAreaMedia(
                        graphViewer.getPropriedades(),
                        tipoArea,
                        nomeArea
                );
                originalCount = (int) graphViewer.getPropriedades().stream()
                        .filter(prop -> AreaCalculator.filtraPorArea(prop, tipoArea, nomeArea))
                        .count();
                processedCount = originalCount;
            }

            resultadoLabel.setText(String.format(
                    "Área média %s em %s (%s): %.2f m² (%d propriedades%s)",
                    groupAdjacent ? "(agrupando adjacentes)" : "",
                    nomeArea,
                    tipoArea,
                    areaMedia,
                    originalCount,
                    groupAdjacent ? " -> " + processedCount + " agrupadas" : ""
            ));
        } catch (Exception ex) {
            resultadoLabel.setText("Erro: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}