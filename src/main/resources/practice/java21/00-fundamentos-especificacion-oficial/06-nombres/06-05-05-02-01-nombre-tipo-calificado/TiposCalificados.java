// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.5.5.2; ejemplo 6.5.5.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . tipos/Exterior.java TiposCalificados.java
// Ejecutar: java TiposCalificados
// Comandos desde la carpeta de la unidad.

public class TiposCalificados {
    public static void main(String[] args) {
        tipos.Exterior.Interior dato = new tipos.Exterior.Interior();
        System.out.println(dato.getClass().getSimpleName());
    }
}

// Salida esperada:
// Interior
