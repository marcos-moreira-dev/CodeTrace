// Tema:
// Una clase interna no estática puede utilizar el parámetro de tipo de su instancia exterior y
// declarar parámetros propios. El tipo completo conserva ambos argumentos.
// Compilar: javac --release 21 -encoding UTF-8 -d . CajaExterior.java GenericasAnidadas.java
// Ejecutar: java GenericasAnidadas
// Comandos desde la carpeta de la unidad.

public class GenericasAnidadas {
    public static void main(String[] args) {
        CajaExterior<String> caja = new CajaExterior<>();
        caja.dato = "texto";
        CajaExterior<String>.Interior<Integer> interior = caja.new Interior<>(8);
        interior.mostrar();
    }
}

// Salida esperada:
// texto 8
