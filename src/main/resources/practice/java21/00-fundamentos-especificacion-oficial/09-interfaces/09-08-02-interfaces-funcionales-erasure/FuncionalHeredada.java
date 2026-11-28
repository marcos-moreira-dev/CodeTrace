// Tema:
// Varios métodos abstractos heredados pueden representar un solo contrato si sus firmas y
// retornos son compatibles. En ese caso la interfaz sigue siendo funcional.
// Compilar: javac --release 21 -encoding UTF-8 -d . PrimeraFuncion.java SegundaFuncion.java FuncionUnida.java FuncionalHeredada.java
// Ejecutar: java FuncionalHeredada
// Comandos desde la carpeta de la unidad.

public class FuncionalHeredada {
    public static void main(String[] args) {
        FuncionUnida f = dato -> { int n = 0; for (String s : dato) n += s.length(); return n; };
        System.out.println(f.medir(java.util.List.of("a", "bc")));
    }
}

// Salida esperada:
// 3
