// Tema:
// Una etiqueta y una variable pueden compartir nombre porque pertenecen a categorías de
// nombres diferentes. break con etiqueta sale de la sentencia etiquetada.
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
