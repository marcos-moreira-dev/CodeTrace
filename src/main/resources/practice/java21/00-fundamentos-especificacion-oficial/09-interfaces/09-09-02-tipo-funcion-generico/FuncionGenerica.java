// Tema:
// El método abstracto de una interfaz funcional puede declarar sus propios parámetros de tipo.
// Una referencia a método genérico puede satisfacer ese contrato.
// Compilar: javac --release 21 -encoding UTF-8 -d . FuncionA.java FuncionB.java FuncionAB.java FuncionGenerica.java
// Ejecutar: java FuncionGenerica
// Comandos desde la carpeta de la unidad.

public class FuncionGenerica {
    static <E extends Exception> String texto() throws E { return "genérico"; }
    public static void main(String[] args) {
        FuncionAB f = FuncionGenerica::texto;
        System.out.println(f.<RuntimeException>llamar());
    }
}

// Salida esperada:
// genérico
