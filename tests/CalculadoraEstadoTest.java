import com.jota.calculadoraIOS.model.CalculadoraEstado;

public class CalculadoraEstadoTest {
    private static int total;
    private static void conferir(String esperado, String... teclas) {
        CalculadoraEstado estado = new CalculadoraEstado();
        for (String tecla : teclas) estado.pressionar(tecla);
        if (!esperado.equals(estado.getVisor())) {
            throw new AssertionError(String.join(" ", teclas) + ": esperado " + esperado + ", obtido " + estado.getVisor());
        }
        total++;
    }
    public static void main(String[] args) {
        conferir("5", "2", "+", "3", "=");
        conferir("-1", "2", "−", "3", "=");
        conferir("24", "6", "×", "4", "=");
        conferir("2,5", "5", "÷", "2", "=");
        conferir("0,3", "0", ",", "1", "+", "0", ",", "2", "=");
        conferir("20", "2", "+", "3", "×", "4", "=");
        conferir("6", "2", "+", "×", "3", "=");
        conferir("8", "2", "+", "3", "=", "=");
        conferir("7", "2", "+", "3", "=", "7");
        conferir("4", "2", "+", "AC", "4", "=");
        conferir("Erro", "8", "÷", "0", "=");
        conferir("3", "8", "÷", "0", "=", "3");
        conferir("0,5", "8", "÷", "0", "=", ",", "5");
        conferir("0", "5", "±", "⌫");
        conferir("-0,5", "±", ",", "5");
        conferir("0,5", "5", "0", "%");
        conferir("220", "2", "0", "0", "+", "1", "0", "%", "=");
        conferir("180", "2", "0", "0", "−", "1", "0", "%", "=");
        conferir("20", "2", "0", "0", "×", "1", "0", "%", "=");
        conferir("2,5", "2", "+", ",", "5", "=");
        conferir("1,2", "1", ",", ",", "2");
        conferir("12345678901234", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "1", "2", "3", "4", "5");
        System.out.println(total + " testes passaram.");
    }
}
