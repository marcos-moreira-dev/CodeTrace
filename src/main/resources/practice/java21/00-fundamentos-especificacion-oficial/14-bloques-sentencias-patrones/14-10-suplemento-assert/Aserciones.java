// Tema:
// assert comprueba una condición de depuración cuando las aserciones están habilitadas. No
// sustituye la validación de argumentos que debe ejecutarse siempre.
// Compilar: javac --release 21 -encoding UTF-8 -d . Aserciones.java
// Ejecutar: java -ea Aserciones
// Comandos desde la carpeta de la unidad.

public class Aserciones {
    public static void main(String[] args) {
        int dato = 3;
        assert dato > 0 : "debe ser positivo";
        System.out.println(dato);
    }
}

// Salida esperada:
// 3
