// Tema:
// La tabla asigna páginas virtuales a marcos físicos del modelo. -1 representa una página
// no presente; no se manipula la MMU real.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 DireccionVirtual.java TablaPaginas.java MainTraduccion.java
// Ejecutar: java MainTraduccion

class TablaPaginas {
    final int[] marcos;

    TablaPaginas(int[] marcos) {
        this.marcos = marcos;
    }

    int traducir(DireccionVirtual d, int tamano) {
        int marco = marcos[d.pagina];
        if (marco < 0) return -1;
        return marco * tamano + d.desplazamiento;
    }
}

// Salida esperada:
// Pagina: 2
// Desplazamiento: 3
// Fisica: 43
// Fallo de pagina
