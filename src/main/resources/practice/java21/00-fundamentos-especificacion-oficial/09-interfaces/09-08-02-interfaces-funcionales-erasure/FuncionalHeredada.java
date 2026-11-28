// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.8; ejemplo 9.8-2
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
