// Tema:
// Los inicializadores de campos se ejecutan durante la inicialización de la clase o la
// construcción de la instancia, según sean static o de instancia.
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
