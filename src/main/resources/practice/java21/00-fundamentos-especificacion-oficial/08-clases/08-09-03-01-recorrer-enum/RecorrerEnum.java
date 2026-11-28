// Tema:
// El método values() generado para un enum devuelve sus constantes en el orden de declaración.
// Puede recorrerse con un for mejorado.
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
