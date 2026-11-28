// Tema:
// var permite inferir el tipo estático de una variable local a partir de su inicializador. No
// significa que la variable cambie dinámicamente de tipo.
// Compilar: javac --release 21 -encoding UTF-8 -d . TipoInferidoVar.java
// Ejecutar: java TipoInferidoVar
// Comandos desde la carpeta de la unidad.

import java.util.List;
public class TipoInferidoVar {
    public static void main(String[] args) {
        var entero = 7;
        var lista = List.of(2, 3);
        var tipo = "sol".getClass();
        var anonimo = new Object() { int valor = 4; };
        var texto = (CharSequence & Comparable<String>) "luz";
        System.out.println(entero + lista.get(0));
        System.out.println(tipo.getSimpleName());
        System.out.println(anonimo.valor);
        System.out.println(texto.length());
    }
}

// Salida esperada:
// 9
// String
// 4
// 3
