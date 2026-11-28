// Tema:
// Un reconocedor valida una gramática de comandos finita; no ejecuta comandos del sistema. Se distingue un verbo permitido de una entrada inválida.
// Modalidad: ejecución real con el JDK.
// Compilar: javac --release 21 -encoding UTF-8 ReconocerComando.java
// Ejecutar: java ReconocerComando

public class ReconocerComando {
    static boolean valido(String texto) {
        return texto.matches("ABRIR|CERRAR|GUARDAR [a-z]{1,20}");
    }

    public static void main(String[] args) throws Exception {
        for (String texto : new String[]{"ABRIR", "GUARDAR pan", "CERRAR", "BORRAR todo"})
            System.out.println(texto + ": " + valido(texto));
    }
}

// Salida esperada:
// ABRIR: true
// GUARDAR pan: true
// CERRAR: true
// BORRAR todo: false
