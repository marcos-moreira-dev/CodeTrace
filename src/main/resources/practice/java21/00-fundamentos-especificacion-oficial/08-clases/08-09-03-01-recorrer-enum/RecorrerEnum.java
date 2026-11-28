// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.9.3; ejemplo 8.9.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . RecorrerEnum.java Fase.java
// Ejecutar: java RecorrerEnum
// Comandos desde la carpeta de la unidad.

public class RecorrerEnum {
    public static void main(String[] args) {
        for (Fase f : Fase.values()) System.out.println(f);
    }
}

// Salida esperada:
// INICIO
// PROCESO
// FIN
