// Tema:
// Una dirección virtual se descompone en número de página y desplazamiento según el tamaño
// fijo de página.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 DireccionVirtual.java TablaPaginas.java MainTraduccion.java
// Ejecutar: java MainTraduccion

class DireccionVirtual {
    final int pagina;
    final int desplazamiento;

    DireccionVirtual(int direccion, int tamano) {
        pagina = direccion / tamano;
        desplazamiento = direccion % tamano;
    }
}

// Salida esperada:
// Pagina: 2
// Desplazamiento: 3
// Fisica: 43
// Fallo de pagina
