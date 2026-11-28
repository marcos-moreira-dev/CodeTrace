// Tema:
// this identifica el objeto receptor del método de instancia. Comparar this con otra
// referencia permite comprobar primero si ambas representan el mismo objeto.
// Compilar: javac --release 21 -encoding UTF-8 -d . VectorIdentidad.java
// Ejecutar: java VectorIdentidad
// Comandos desde la carpeta de la unidad.

class VectorIdentidad {
    int[] valores;
    VectorIdentidad(int... valores) { this.valores = valores; }
    boolean iguales(VectorIdentidad otro) {
        if (this == otro) return true;
        return java.util.Arrays.equals(valores, otro.valores);
    }
    public static void main(String[] args) {
        VectorIdentidad a = new VectorIdentidad(1, 2);
        System.out.println(a.iguales(a));
        System.out.println(a.iguales(new VectorIdentidad(1, 2)));
        System.out.println(a.iguales(new VectorIdentidad(2, 1)));
    }
}

// Salida esperada:
// true
// true
// false
