// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.9.3; ejemplo 8.9.3-2
// Compilar: javac --release 21 -encoding UTF-8 -d . Semaforo.java SwitchEnum.java
// Ejecutar: java SwitchEnum
// Comandos desde la carpeta de la unidad.

public class SwitchEnum {
    static String accion(Semaforo estado) {
        switch (estado) {
            case ROJO: return "parar";
            case AMARILLO: return "esperar";
            case VERDE: return "avanzar";
            default: throw new AssertionError(estado);
        }
    }
    public static void main(String[] args) {
        for (Semaforo e : Semaforo.values()) System.out.println(accion(e));
    }
}

// Salida esperada:
// parar
// esperar
// avanzar
