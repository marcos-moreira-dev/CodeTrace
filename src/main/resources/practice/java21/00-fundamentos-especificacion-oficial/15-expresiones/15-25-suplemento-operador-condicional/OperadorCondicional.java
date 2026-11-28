// Tema:
// El operador ternario evalúa primero la condición y luego solamente una de las dos
// alternativas. El tipo de la expresión se determina mediante las reglas de los operandos y
// el contexto.
// Compilar: javac --release 21 -encoding UTF-8 -d . OperadorCondicional.java
// Ejecutar: java OperadorCondicional
// Comandos desde la carpeta de la unidad.

public class OperadorCondicional {
    public static void main(String[] args) {
        int n = 7;
        String tipo = n % 2 == 0 ? "par" : "impar";
        int contador = 0;
        int resultado = true ? ++contador : --contador;
        System.out.println(tipo);
        System.out.println(resultado + " " + contador);
    }
}

// Salida esperada:
// impar
// 1 1
