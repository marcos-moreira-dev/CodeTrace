// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.3.2; ejemplo 8.3.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Coordenada.java InicializarCampos.java
// Ejecutar: java InicializarCampos
// Comandos desde la carpeta de la unidad.

public class InicializarCampos {
    public static void main(String[] args) {
        Coordenada a = new Coordenada();
        System.out.println(a.fila + " " + a.columna);
    }
}

// Salida esperada:
// 2 7
