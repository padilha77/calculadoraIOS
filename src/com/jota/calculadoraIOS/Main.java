package com.jota.calculadoraIOS;

import com.jota.calculadoraIOS.model.Calculadora;
import com.jota.calculadoraIOS.view.CalculadoraView;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {

        Calculadora calculadora = new Calculadora();

        double resultadoSoma = calculadora.somar(65, 2);
        double resultadoSubtracao = calculadora.subtrair(65, 2);
        double resultadoMultiplicacao = calculadora.multiplicar(65, 2);

        try {
            double resultadoDivisao = calculadora.dividir(65, 0);
            System.out.println(resultadoDivisao);
        }catch (ArithmeticException e){
            System.out.println(e.getMessage());
        }

        System.out.println(resultadoSoma);
        System.out.println(resultadoSubtracao);
        System.out.println(resultadoMultiplicacao);

        Application.launch(CalculadoraView.class);
    }

}
