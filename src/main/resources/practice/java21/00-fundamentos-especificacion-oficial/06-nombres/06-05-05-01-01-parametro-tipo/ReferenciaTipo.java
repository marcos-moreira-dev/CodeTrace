// Tema:
// Un parámetro de tipo es un nombre válido dentro del alcance de su declaración genérica. El
// tipo concreto se establece mediante los argumentos de tipo.
// Compilar: javac --release 21 -encoding UTF-8 -d . ReferenciaTipo.java
// Ejecutar: java ReferenciaTipo
// Comandos desde la carpeta de la unidad.

public class ReferenciaTipo<T> {
    T dato;
    <U> U identidad(U valor) { return valor; }
    public static void main(String[] args) {
        ReferenciaTipo<String> r = new ReferenciaTipo<>();
        r.dato = "luz";
        System.out.println(r.dato);
        System.out.println(r.identidad(8));
    }
}

// Salida esperada:
// luz
// 8
