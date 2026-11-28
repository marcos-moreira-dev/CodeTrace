// Tema:
// Una tabla de rutas elige la coincidencia de prefijo más largo, no necesariamente la primera fila. El modelo incluye ruta por defecto; no configura el sistema ni implementa protocolos dinámicos.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Ruta.java TablaRutas.java MainPrefijoMasLargo.java
// Ejecutar: java MainPrefijoMasLargo

public record Ruta(int red, int prefijo, String siguiente) {
    public Ruta {
        if (prefijo < 0 || prefijo > 32) throw new IllegalArgumentException("Prefijo");
    }
    public boolean coincide(int destino) {
        int m = prefijo == 0 ? 0 : -1 << (32 - prefijo);
        return (red & m) == (destino & m);
    }
}

// Salida esperada:
// /26 -> R2
// /24 -> R1
// /0 -> R0
