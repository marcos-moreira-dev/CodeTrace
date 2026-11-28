// Tema:
// Un campo static de una clase genérica pertenece a la clase, no a una parametrización. Se
// selecciona con el nombre de la declaración genérica sin argumentos de tipo.
// Compilar: javac --release 21 -encoding UTF-8 -d . CampoGenerico.java
// Ejecutar: java CampoGenerico

public class CampoGenerico<T> {
    static int total = 4;
    public static void main(String[] args) {
        CampoGenerico<String> a = new CampoGenerico<>();
        CampoGenerico<Integer> b = new CampoGenerico<>();
        CampoGenerico.total++;
        System.out.println(a.total + " " + b.total);
    }
}

// Salida esperada:
// 5 5
