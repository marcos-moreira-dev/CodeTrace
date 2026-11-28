// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §14.7; ejemplo 14.7-1
// Compilar: javac --release 21 -encoding UTF-8 -d . EtiquetaVariable.java
// Ejecutar: java EtiquetaVariable
// Comandos desde la carpeta de la unidad.

public class EtiquetaVariable {
    public static void main(String[] args) {
        int indice = 0;
        indice: for (int n = 0; n < 4; n++) {
            if (n == 2) break indice;
            indice++;
        }
        System.out.println(indice);
    }
}

// Salida esperada:
// 2
