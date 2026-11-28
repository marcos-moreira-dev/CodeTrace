// Tema:
// El patrón de tipo comprueba la clase de una referencia y declara una variable del tipo
// comprobado. Esa variable solo está disponible donde se garantiza que el patrón coincidió.
// Compilar: javac --release 21 -encoding UTF-8 -d . PatronTipo.java
// Ejecutar: java PatronTipo
// Comandos desde la carpeta de la unidad.

public class PatronTipo {
    public static void main(String[] args) {
        Object dato = "Java";
        if (dato instanceof String texto && texto.length() > 2) {
            System.out.println(texto.toUpperCase());
        }
    }
}

// Salida esperada:
// JAVA
