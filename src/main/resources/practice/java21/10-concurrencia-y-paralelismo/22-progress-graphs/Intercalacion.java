// Tema:
// Grafo de progreso discreto: cada coordenada indica pasos completados (0: antes de adquirir, 1: dentro, 2: liberado). Enumeramos las seis intercalaciones y marcamos las que cruzan la zona prohibida (1,1).
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Intercalacion.java GrafoProgreso.java MainGrafoProgreso.java
// Ejecutar: java MainGrafoProgreso

public record Intercalacion(int a, int b) {
    public boolean insegura() { return a == 1 && b == 1; }
}

// Salida esperada:
// Seguras: 2
// Inseguras: 4
