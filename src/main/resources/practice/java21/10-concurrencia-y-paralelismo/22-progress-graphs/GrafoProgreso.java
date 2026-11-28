// Tema:
// Grafo de progreso discreto: cada coordenada indica pasos completados (0: antes de adquirir, 1: dentro, 2: liberado). Enumeramos las seis intercalaciones y marcamos las que cruzan la zona prohibida (1,1).
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Intercalacion.java GrafoProgreso.java MainGrafoProgreso.java
// Ejecutar: java MainGrafoProgreso

public class GrafoProgreso {
    // resultado[0]: trayectorias seguras, resultado[1]: inseguras.
    public static int[] contar() {
        int[] resultado = new int[2];
        recorrer(new Intercalacion(0, 0), false, resultado);
        return resultado;
    }

    private static void recorrer(Intercalacion p, boolean insegura, int[] resultado) {
        insegura |= p.insegura();
        if (p.a() == 2 && p.b() == 2) {
            resultado[insegura ? 1 : 0]++;
            return;
        }
        if (p.a() < 2) recorrer(new Intercalacion(p.a() + 1, p.b()), insegura, resultado);
        if (p.b() < 2) recorrer(new Intercalacion(p.a(), p.b() + 1), insegura, resultado);
    }
}

// Salida esperada:
// Seguras: 2
// Inseguras: 4
