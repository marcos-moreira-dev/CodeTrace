// Tema:
// Una variable final sin inicializador puede asignarse en ramas mutuamente excluyentes,
// siempre que en cada ruta se asigne como máximo una vez antes de utilizarse.
// Compilar: javac --release 21 -encoding UTF-8 -d . FinalRamas.java
// Ejecutar: java FinalRamas
// Comandos desde la carpeta de la unidad.

public class FinalRamas {
    static void mostrar(boolean condicion) {
        final int valor;
        if (condicion) valor = 3;
        else valor = 7;
        System.out.println(valor);
    }
    public static void main(String[] args) {
        mostrar(true);
        mostrar(false);
    }
}

// Salida esperada:
// 3
// 7
