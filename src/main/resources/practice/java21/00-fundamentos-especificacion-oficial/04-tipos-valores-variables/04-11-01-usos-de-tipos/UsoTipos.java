// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.11; ejemplo 4.11-1
// Compilar: javac --release 21 -encoding UTF-8 -d . UsoTipos.java
// Ejecutar: java UsoTipos
// Comandos desde la carpeta de la unidad.

import java.util.ArrayList;
import java.util.List;
class UsoTipos<T extends Number> {
    int divisor;                           // Tipo de campo.
    UsoTipos(int divisor) { this.divisor = divisor; }
    float dividir(long valor) {             // Parámetro y resultado.
        try { valor /= divisor; }
        catch (ArithmeticException e) { valor = 0; } // Tipo de excepción.
        return (float) valor;               // Conversión.
    }
    List<Number> copiar(Number[] datos) {
        List<Number> lista = new ArrayList<>();
        for (Number dato : datos) lista.add(dato);
        return lista;
    }
    <S> S identidad(S dato) { return dato; }
    public static void main(String[] args) {
        UsoTipos<Integer> u = new UsoTipos<>(2);
        System.out.println(u.dividir(8));
        System.out.println(u.copiar(new Number[]{3, 4}));
        System.out.println(u.<String>identidad("sol"));
    }
}

// Salida esperada:
// 4.0
// [3, 4]
// sol
