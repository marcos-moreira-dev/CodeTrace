// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.8.2; ejemplo 8.4.8.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . OrigenMetodo.java DerivadoMetodo.java OcultacionMetodos.java
// Ejecutar: java OcultacionMetodos
// Comandos desde la carpeta de la unidad.

public class OcultacionMetodos {
    public static void main(String[] args) {
        OrigenMetodo ref = new DerivadoMetodo();
        System.out.println(ref.clase());
        System.out.println(ref.instancia());
    }
}

// Salida esperada:
// origen
// derivado
