// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §16; ejemplo 16-1
// Compilar: javac --release 21 -encoding UTF-8 -d . AsignacionDefinida.java
// Ejecutar: java AsignacionDefinida
// Comandos desde la carpeta de la unidad.

public class AsignacionDefinida {
    public static void main(String[] args) {
        int valor;
        int n = 2;
        while (true) {
            valor = n;
            if (valor >= 5) break;
            n = 6;
        }
        System.out.println(valor);
    }
}

// Salida esperada:
// 6
