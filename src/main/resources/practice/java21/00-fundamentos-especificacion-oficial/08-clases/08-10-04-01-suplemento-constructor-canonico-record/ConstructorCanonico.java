// Tema:
// La validación del constructor impide crear intervalos con límites invertidos; una
// instancia válida conserva los dos componentes.
// Compilar: javac --release 21 -encoding UTF-8 -d . Intervalo.java ConstructorCanonico.java
// Ejecutar: java ConstructorCanonico
// Comandos desde la carpeta de la unidad.

public class ConstructorCanonico {
    public static void main(String[] args) {
        Intervalo i = new Intervalo(2, 7);
        System.out.println(i.fin() - i.inicio());
        try { new Intervalo(7, 2); }
        catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
    }
}

// Salida esperada:
// 5
// orden
