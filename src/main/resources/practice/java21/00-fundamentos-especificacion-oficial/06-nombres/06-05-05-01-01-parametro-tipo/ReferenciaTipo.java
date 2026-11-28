// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.5.5.1; ejemplo 6.5.5.1-1
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
