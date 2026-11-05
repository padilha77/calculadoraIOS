package com.jota.calculadoraIOS.model;

import java.math.BigDecimal;
import java.math.MathContext;

/** Estado da calculadora, independente dos componentes JavaFX. */
public class CalculadoraEstado {
    private static final MathContext PRECISAO = new MathContext(14);
    private String visor = "0";
    private BigDecimal acumulado = BigDecimal.ZERO;
    private String operacao = "";
    private String ultimaOperacao = "";
    private BigDecimal ultimoNumero;
    private boolean novaEntrada = true;

    public String getVisor() { return visor; }
    public String getOperacaoSelecionada() { return novaEntrada ? operacao : ""; }

    public void pressionar(String tecla) {
        if (tecla.equals("AC")) { limpar(); return; }
        if (visor.equals("Erro")) {
            limpar();
            if (!tecla.matches("[0-9,]")) return;
        }
        try {
            if (tecla.matches("[0-9]")) {
                if (novaEntrada) { visor = "0"; novaEntrada = false; }
                if (visor.replace("-", "").replace(",", "").length() >= 14) return;
                visor = visor.equals("0") ? tecla : visor.equals("-0") ? "-" + tecla : visor + tecla;
            } else if (tecla.equals(",")) {
                if (novaEntrada) { visor = "0"; novaEntrada = false; }
                if (!visor.contains(",")) visor += ",";
            } else if (tecla.equals("⌫")) {
                if (novaEntrada) return;
                visor = visor.length() > 1 ? visor.substring(0, visor.length() - 1) : "0";
                if (visor.equals("-")) visor = "0";
            } else if (tecla.equals("±")) {
                visor = visor.startsWith("-") ? visor.substring(1) : "-" + visor;
                novaEntrada = false;
            } else if (tecla.equals("%")) {
                BigDecimal valor = numero().movePointLeft(2);
                if (operacao.equals("+") || operacao.equals("−")) valor = acumulado.multiply(valor, PRECISAO);
                mostrar(valor);
                novaEntrada = false;
            } else if (tecla.matches("[+−×÷]")) {
                if (!operacao.isEmpty() && !novaEntrada) mostrar(calcular(acumulado, numero(), operacao));
                acumulado = numero();
                operacao = tecla;
                ultimaOperacao = "";
                novaEntrada = true;
            } else if (tecla.equals("=")) {
                if (!operacao.isEmpty()) {
                    ultimoNumero = numero();
                    ultimaOperacao = operacao;
                    mostrar(calcular(acumulado, ultimoNumero, operacao));
                    operacao = "";
                } else if (!ultimaOperacao.isEmpty()) {
                    mostrar(calcular(numero(), ultimoNumero, ultimaOperacao));
                }
                novaEntrada = true;
            }
        } catch (ArithmeticException | NumberFormatException e) {
            limpar();
            visor = "Erro";
        }
    }

    private BigDecimal numero() { return new BigDecimal(visor.replace(',', '.')); }

    private BigDecimal calcular(BigDecimal a, BigDecimal b, String operador) {
        return switch (operador) {
            case "+" -> a.add(b, PRECISAO);
            case "−" -> a.subtract(b, PRECISAO);
            case "×" -> a.multiply(b, PRECISAO);
            case "÷" -> a.divide(b, PRECISAO);
            default -> b;
        };
    }

    private void mostrar(BigDecimal valor) {
        valor = valor.round(PRECISAO).stripTrailingZeros();
        String texto = valor.toPlainString();
        visor = (texto.length() > 16 ? valor.toString() : texto).replace('.', ',');
    }

    private void limpar() {
        visor = "0";
        acumulado = BigDecimal.ZERO;
        operacao = "";
        ultimaOperacao = "";
        ultimoNumero = null;
        novaEntrada = true;
    }
}
