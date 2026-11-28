// Tema:
// Validar longitud antes de reservar memoria evita que mensajes inválidos provoquen asignaciones enormes. El límite se expresa en bytes y se acepta únicamente el rango previsto.
// Modalidad: ejecución real con datos de prueba.
// Compilar: javac --release 21 -encoding UTF-8 ValidarLongitud.java
// Ejecutar: java ValidarLongitud

public class ValidarLongitud {
    static boolean valida(int longitud) {
        return longitud >= 0 && longitud <= 1024;
    }

    public static void main(String[] args) throws Exception {
        for (int n : new int[]{0, 32, -1, 1025})
            System.out.println(n + ": " + valida(n));
    }
}

// Salida esperada:
// 0: true
// 32: true
// -1: false
// 1025: false
