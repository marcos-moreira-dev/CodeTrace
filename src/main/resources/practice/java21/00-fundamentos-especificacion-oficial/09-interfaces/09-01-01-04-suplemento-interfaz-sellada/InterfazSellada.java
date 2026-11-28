// Tema:
// El switch sobre una jerarquía sellada puede cubrir todas las alternativas conocidas sin
// default.
// Compilar: javac --release 21 -encoding UTF-8 -d . Resultado.java Exito.java Fallo.java InterfazSellada.java
// Ejecutar: java InterfazSellada
// Comandos desde la carpeta de la unidad.

public class InterfazSellada {
    static String mostrar(Resultado resultado) {
        return switch (resultado) {
            case Exito e -> "valor " + e.valor();
            case Fallo f -> f.mensaje();
        };
    }
    public static void main(String[] args) {
        System.out.println(mostrar(new Exito(4)));
        System.out.println(mostrar(new Fallo("error")));
    }
}

// Salida esperada:
// valor 4
// error
