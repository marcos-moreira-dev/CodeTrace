// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.18.1; ejemplo 15.18.1-2
// Compilar: javac --release 21 -encoding UTF-8 -d . ContarObjetos.java
// Ejecutar: java ContarObjetos
// Comandos desde la carpeta de la unidad.

class ContarObjetos {
    static void contar(Object objeto, int n) {
        while (n > 0) {
            String plural = n == 1 ? "" : "s";
            System.out.println(n + " caja" + plural + " de " + objeto);
            n--;
        }
        System.out.println("No quedan cajas");
    }
    public static void main(String[] args) { contar("papel", 3); }
}

// Salida esperada:
// 3 cajas de papel
// 2 cajas de papel
// 1 caja de papel
// No quedan cajas
