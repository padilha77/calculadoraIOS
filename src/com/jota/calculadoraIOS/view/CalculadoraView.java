package com.jota.calculadoraIOS.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalculadoraView extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // layout principal
        stage.setTitle("Calculadora");
        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #000000;");

        // visor da calculadora
        Label visor = new Label("0");
        visor.setStyle("-fx-text-fill: white; -fx-font-size: 48px; -fx-font-weight: bold;");
        visor.setAlignment(Pos.CENTER_RIGHT);
        visor.setMaxWidth(Double.MAX_VALUE);

        // botoes(tamannho, cor, espaçamento entre outras conf do botao)
        // setHgap/setVgap configuram o espaçamento dos elementos da grade
        GridPane grade = new GridPane();
        grade.setHgap(10);
        grade.setVgap(10);
        Button botao7 = new Button("7");

        // adicionando tudo a calculadora
        grade.add(botao7, 0, 1);
        layout.getChildren().add(visor);
        layout.getChildren().add(grade);

        Scene scene = new Scene(layout, 350, 550);
        stage.setScene(scene);
        stage.show();

    }
}
