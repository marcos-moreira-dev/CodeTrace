// Tema:
// Con páginas de ocho unidades, la dirección virtual 19 es página 2 y desplazamiento 3. El
// marco 5 se traduce a dirección 43.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 DireccionVirtual.java TablaPaginas.java MainTraduccion.java
// Ejecutar: java MainTraduccion

public class MainTraduccion {
    public static void main(String[] args) {
        TablaPaginas tabla = new TablaPaginas(new int[]{3, -1, 5});
        DireccionVirtual d = new DireccionVirtual(19, 8);
        System.out.println("Pagina: " + d.pagina);
        System.out.println("Desplazamiento: " + d.desplazamiento);
        System.out.println("Fisica: " + tabla.traducir(d, 8));
        if (tabla.traducir(new DireccionVirtual(9, 8), 8) == -1) {
            System.out.println("Fallo de pagina");
        }
    }
}

// Salida esperada:
// Pagina: 2
// Desplazamiento: 3
// Fisica: 43
// Fallo de pagina
