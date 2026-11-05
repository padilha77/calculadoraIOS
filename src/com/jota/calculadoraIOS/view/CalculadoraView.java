package com.jota.calculadoraIOS.view;

import com.jota.calculadoraIOS.model.CalculadoraEstado;
import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class CalculadoraView extends Application {
    private final CalculadoraEstado estado = new CalculadoraEstado();
    private final Label visor = new Label("0");
    private final Map<String, Button> operadores = new HashMap<>();
    private static final PseudoClass SELECIONADO = PseudoClass.getPseudoClass("selected");

    @Override
    public void start(Stage stage) {
        VBox layout = new VBox(22);
        layout.getStyleClass().add("calculadora");
        layout.setPadding(new Insets(24, 20, 24, 20));
        Region espacador = new Region();

        VBox.setVgrow(espacador, Priority.ALWAYS);
        visor.getStyleClass().add("visor");
        visor.setAlignment(Pos.CENTER_RIGHT);
        visor.setMaxWidth(Double.MAX_VALUE);
        visor.setMinHeight(100);
        visor.setPadding(new Insets(0, 8, 0, 0));
        visor.setAccessibleText("Visor da calculadora");

        GridPane grade = new GridPane();
        grade.setHgap(12);
        grade.setVgap(12);
        for (int i = 0; i < 4; i++) {
            ColumnConstraints coluna = new ColumnConstraints();
            coluna.setPercentWidth(25);
            coluna.setHgrow(Priority.ALWAYS);
            grade.getColumnConstraints().add(coluna);
        }
        String[][] teclas = {
            {"AC", "±", "%", "÷"}, {"7", "8", "9", "×"},
            {"4", "5", "6", "−"}, {"1", "2", "3", "+"}, {"0", "", ",", "="}
        };
        for (int linha = 0; linha < teclas.length; linha++) {
            for (int coluna = 0; coluna < 4; coluna++) {
                String tecla = teclas[linha][coluna];
                if (tecla.isEmpty()) continue;
                Button botao = new Button(tecla);
                botao.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                botao.setMinSize(0, 0);
                botao.prefHeightProperty().bind(grade.widthProperty().subtract(36).divide(4));
                botao.getStyleClass().add(coluna == 3 ? "botao-operador" : linha == 0 ? "botao-funcao" : "botao-numero");
                if (tecla.equals("0")) botao.getStyleClass().add("botao-zero");
                if ("+−×÷".contains(tecla)) operadores.put(tecla, botao);
                botao.setOnAction(event -> pressionar(tecla));
                grade.add(botao, coluna, linha, tecla.equals("0") ? 2 : 1, 1);
            }
        }
        layout.getChildren().addAll(espacador, visor, grade);
        Scene scene = new Scene(layout, 390, 650);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            String tecla = event.getText().equals("+") ? "+" : switch (event.getCode()) {
                case ENTER, EQUALS -> "=";
                case BACK_SPACE -> "⌫";
                case ESCAPE, DELETE -> "AC";
                default -> switch (event.getText()) {
                    case ".", "," -> ",";
                    case "*" -> "×";
                    case "/" -> "÷";
                    case "-" -> "−";
                    default -> event.getText();
                };
            };
            if (tecla.matches("[0-9,+−×÷%=]") || tecla.equals("AC") || tecla.equals("⌫")) {
                pressionar(tecla);
                event.consume();
            }
        });
        stage.setTitle("Calculadora");
        stage.setMinWidth(350);
        stage.setMinHeight(610);
        stage.setScene(scene);
        stage.show();
        layout.requestFocus();
    }

    private void pressionar(String tecla) {
        estado.pressionar(tecla);
        visor.setText(estado.getVisor());
        int tamanho = estado.getVisor().length();
        visor.setStyle("-fx-font-size: " + (tamanho > 12 ? 32 : tamanho > 9 ? 42 : tamanho > 6 ? 56 : 80) + "px;");
        operadores.forEach((simbolo, botao) -> botao.pseudoClassStateChanged(SELECIONADO,
                simbolo.equals(estado.getOperacaoSelecionada())));
    }
}

