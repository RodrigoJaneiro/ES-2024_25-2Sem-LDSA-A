package projeto.es;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*; // Import genérico para ListView
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ApplicationViewer extends Application {
    private Stage primaryStage;
    private List<Propriedade> loadedProperties; // Cache das propriedades carregadas

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        // Carregar dados uma vez para evitar recarregamentos
        try {
            this.loadedProperties = CSVLoader.LoadPropriedades("Madeira"); // ou o nome do arquivo que você usa
        } catch (Exception e) {
            // Lidar com erro de carregamento inicial, talvez mostrar um alerta e sair
            System.err.println("Falha ao carregar dados iniciais: " + e.getMessage());
            e.printStackTrace();
            // Mostrar um alerta para o usuário seria bom aqui
            return;
        }
        showMainMenu();
    }

    private void showMainMenu() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        for (int i = 2; i <= 7; i++) {
            String textoBotao = getTextoBotao(i);
            Button btn = new Button(textoBotao);
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

                Task<Node> task = new Task<Node>() { // Alterado para Node para flexibilidade
                    @Override
                    protected Node call() throws Exception {
                        // Usar as propriedades já carregadas
                        if (loadedProperties == null || loadedProperties.isEmpty()) {
                            throw new IllegalStateException("Dados de propriedades não carregados.");
                        }
                        GraphViewer graphViewer = new GraphViewer(loadedProperties);
                        ScrollPane scrollPane = new ScrollPane();
                        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

                        switch (exerciseNumber) {
                            case 2:
                                scrollPane.setContent(graphViewer.drawGraphVizinhos());
                                break;
                            case 3:
                                scrollPane.setContent(graphViewer.drawGraphVizinhosPropreietarios());
                                break;
                            case 4:
                            case 5:
                                scrollPane.setContent(createAreaCalculationUI(graphViewer, exerciseNumber == 5));
                                break;
                            case 6:
                            case 7:
                                return createSwapSuggestionsUI(loadedProperties, exerciseNumber); // Retorna Node diretamente

                        }
                        return scrollPane; // Retorna ScrollPane para os casos antigos
                    }
                };

                task.setOnSucceeded(event -> contentPane.getChildren().setAll(task.getValue()));
                task.setOnFailed(event -> {
                    task.getException().printStackTrace(); // Bom para debugging
                    contentPane.getChildren().setAll(
                            new Label("Erro ao processar Exercício " + exerciseNumber + ": " + task.getException().getMessage()));
                });
                new Thread(task).start();
            });
            grid.add(btn, (i - 2) % 2, (i - 2) / 2);
        }

        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setTitle("Menu Principal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String getTextoBotao(int numExercicio) {
        String textoBotao = "";
        switch (numExercicio) {
            case 2:
                textoBotao = "Grafo Cadastro Propriedades";
                break;
            case 3:
                textoBotao = "Grafo Cadastro Proprietários";
                break;
            case 4:
                textoBotao = "Área Média das Propriedades";
                break;
            case 5:
                textoBotao = "Área Média com Propriedades Adjacentes";
                break;
            case 6:
                textoBotao = "Sugestão de Troca por m2";
                break;
            case 7:
                textoBotao = "Sugestão de Troca por m2 e Diferentes Zonas";
                break;
        }
        return textoBotao + " (Ex" + numExercicio + ")";
    }

    private VBox createAreaCalculationUI(GraphViewer graphViewer, boolean groupAdjacent) {
        // ... (seu código existente, sem alterações necessárias aqui)
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        ComboBox<String> tipoAreaCombo = new ComboBox<>();
        tipoAreaCombo.getItems().addAll("Freguesia", "Municipio", "Ilha");
        tipoAreaCombo.setPromptText("Selecione o tipo de área");

        ComboBox<String> nomeAreaCombo = new ComboBox<>();
        nomeAreaCombo.setPromptText("Selecione a área");

        // Usar as propriedades carregadas para popular os combos
        tipoAreaCombo.setOnAction(event -> updateAreaCombo(tipoAreaCombo.getValue(), nomeAreaCombo, this.loadedProperties));

        Button calcularBtn = new Button(groupAdjacent ?
                "Calcular Área Média (Agrupando Adjacentes)" : "Calcular Área Média");
        Label resultadoLabel = new Label();

        calcularBtn.setOnAction(event -> calculateArea(
                tipoAreaCombo.getValue(),
                nomeAreaCombo.getValue(),
                this.loadedProperties, // Passar a lista de propriedades
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

    // Modificar updateAreaCombo e calculateArea para aceitar List<Propriedade> diretamente
    private void updateAreaCombo(String tipoSelecionado, ComboBox<String> nomeAreaCombo, List<Propriedade> propriedades) {
        nomeAreaCombo.getItems().clear();
        if (tipoSelecionado != null) {
            nomeAreaCombo.getItems().addAll(
                    propriedades.stream() // Usar a lista de propriedades fornecida
                            .map(prop -> {
                                switch (tipoSelecionado) {
                                    case "Freguesia": return prop.getFreguesia();
                                    case "Municipio": return prop.getMunicipio();
                                    case "Ilha": return prop.getIlha();
                                    default: return "";
                                }
                            })
                            .distinct()
                            .filter(s -> s != null && !s.isEmpty())
                            .collect(Collectors.toList())
            );
        }
    }

    private void calculateArea(String tipoArea, String nomeArea, List<Propriedade> todasPropriedades,
                               Label resultadoLabel, boolean groupAdjacent) {
        try {
            if (tipoArea == null || nomeArea == null) {
                resultadoLabel.setText("Selecione o tipo e o nome da área.");
                return;
            }

            List<Propriedade> propriedadesFiltradasPorAreaGeo = todasPropriedades.stream()
                    .filter(prop -> AreaCalculator.filtraPorArea(prop, tipoArea, nomeArea))
                    .collect(Collectors.toList());

            if (propriedadesFiltradasPorAreaGeo.isEmpty()) {
                resultadoLabel.setText("Nenhuma propriedade encontrada para a área especificada.");
                return;
            }

            double areaMedia;
            int originalCount = propriedadesFiltradasPorAreaGeo.size();
            int processedCount;

            if (groupAdjacent) {
                List<Propriedade> agrupadas = Calculations.agruparPropriedadesAdjacentes(propriedadesFiltradasPorAreaGeo);
                processedCount = agrupadas.size();
                areaMedia = agrupadas.stream()
                        .mapToDouble(Propriedade::getShape_area)
                        .average()
                        .orElse(0);
            } else {
                areaMedia = propriedadesFiltradasPorAreaGeo.stream()
                        .mapToDouble(Propriedade::getShape_area)
                        .average()
                        .orElse(0);
                processedCount = originalCount;
            }

            resultadoLabel.setText(String.format(
                    "Área média %s em %s (%s): %.2f m² (%d propriedades%s)",
                    groupAdjacent ? "(agrupando adjacentes)" : "",
                    nomeArea,
                    tipoArea,
                    areaMedia,
                    originalCount,
                    groupAdjacent && originalCount > 0 ? " -> " + processedCount + " agrupadas" : ""
            ));
        } catch (Exception ex) {
            ex.printStackTrace();
            resultadoLabel.setText("Erro ao calcular área: " + ex.getMessage());
        }
    }


    private Node createSwapSuggestionsUI(List<Propriedade> allProperties, int exerciseNumber) {
        VBox container = new VBox(10);
        container.setPadding(new Insets(15));
        container.setAlignment(Pos.CENTER);

        Label title = new Label("Sugestões de Troca de Propriedades");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ListView<String> suggestionsListView = new ListView<>();

        // Obter sugestões (isto pode demorar, idealmente seria feito em background, mas já está no Task)
        List<SwapSuggestion> suggestions = PropertySwapSuggester.generateSuggestions(allProperties, exerciseNumber);

        if (suggestions.isEmpty()) {
            suggestionsListView.getItems().add("Nenhuma sugestão de troca benéfica encontrada.");
        } else {
            for (SwapSuggestion suggestion : suggestions) {
                suggestionsListView.getItems().add(suggestion.toString());
            }
        }

        ScrollPane scrollPane = new ScrollPane(suggestionsListView);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        container.getChildren().addAll(title, scrollPane);
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS); // Fazer o scrollPane crescer

        return container;
    }

    public static void main(String[] args) {
        launch(args);
    }
}