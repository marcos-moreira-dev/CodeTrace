// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.1.2; ejemplo 8.1.2-2
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
