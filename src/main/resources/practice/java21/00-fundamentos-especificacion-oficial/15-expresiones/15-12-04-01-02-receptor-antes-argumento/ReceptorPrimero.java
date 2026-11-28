// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.12.4.1; ejemplo 15.12.4.1-2
// Compilar: javac --release 21 -encoding UTF-8 -d . ReceptorPrimero.java
// Ejecutar: java ReceptorPrimero
// Comandos desde la carpeta de la unidad.

public class ReceptorPrimero {
    public static void main(String[] args) {
        String texto = "sol";
        System.out.println(texto.startsWith(texto = "mar"));
        System.out.println(texto);
    }
}

// Salida esperada:
// false
// mar
