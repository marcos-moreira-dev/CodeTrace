// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.4.1; ejemplo 6.4.1-1
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
