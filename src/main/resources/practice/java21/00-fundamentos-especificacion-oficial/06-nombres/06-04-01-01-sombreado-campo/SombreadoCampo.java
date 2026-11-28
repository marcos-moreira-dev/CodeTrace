// Tema:
// Una variable local puede ocultar un campo del mismo nombre. El nombre simple elige la local;
// this permite seleccionar el campo de la instancia.
// Compilar: javac --release 21 -encoding UTF-8 -d . SombreadoCampo.java
// Ejecutar: java SombreadoCampo
// Comandos desde la carpeta de la unidad.

public class SombreadoCampo {
    int valor = 2;
    void mostrar() {
        int valor = 9;
        System.out.println(valor);
        System.out.println(this.valor);
    }
    public static void main(String[] args) { new SombreadoCampo().mostrar(); }
}

// Salida esperada:
// 9
// 2
