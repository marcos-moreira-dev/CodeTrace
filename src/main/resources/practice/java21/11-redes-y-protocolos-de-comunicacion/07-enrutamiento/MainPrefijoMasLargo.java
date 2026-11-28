// Tema:
// Una tabla de rutas elige la coincidencia de prefijo más largo, no necesariamente la primera fila. El modelo incluye ruta por defecto; no configura el sistema ni implementa protocolos dinámicos.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Ruta.java TablaRutas.java MainPrefijoMasLargo.java
// Ejecutar: java MainPrefijoMasLargo

public class MainPrefijoMasLargo {
    public static void main(String[] args) throws Exception {
        TablaRutas tabla = new TablaRutas(
            new Ruta(0, 0, "R0"),
            new Ruta(0xc0000200, 24, "R1"),
            new Ruta(0xc0000240, 26, "R2"));
        for (int destino : new int[]{0xc0000246, 0xc000020a, 0xc6336401}) {
            Ruta r = tabla.elegir(destino);
            System.out.println("/" + r.prefijo() + " -> " + r.siguiente());
        }
    }
}

// Salida esperada:
// /26 -> R2
// /24 -> R1
// /0 -> R0
