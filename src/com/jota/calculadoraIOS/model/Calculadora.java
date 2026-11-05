package com.jota.calculadoraIOS.model;

public class Calculadora {

    public double somar(double a, double b){
        double soma = a + b;
        return soma;
    }

    public double subtrair(double a, double b){
        double sub = a - b;
        return sub;
    }

    public double multiplicar(double a, double b){
        double multiplica = a * b;
        return multiplica;
    }

    public  double dividir(double a, double b){
        if (b == 0){
            throw  new ArithmeticException("Inválido");
        }
        double dividir = a / b;
        return dividir;
    }

}
