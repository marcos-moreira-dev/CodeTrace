// Tema:
// Una tabla de rutas elige la coincidencia de prefijo más largo, no necesariamente la primera fila. El modelo incluye ruta por defecto; no configura el sistema ni implementa protocolos dinámicos.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Ruta.java TablaRutas.java MainPrefijoMasLargo.java
// Ejecutar: java MainPrefijoMasLargo

public class TablaRutas {
    private final Ruta[] rutas;
    public TablaRutas(Ruta... rutas) { this.rutas = rutas.clone(); }
    public Ruta elegir(int destino) {
        Ruta mejor = null;
        for (Ruta r : rutas)
            if (r.coincide(destino) && (mejor == null || r.prefijo() > mejor.prefijo()))
                mejor = r;
        return mejor;
    }
}

// Salida esperada:
// /26 -> R2
// /24 -> R1
// /0 -> R0
