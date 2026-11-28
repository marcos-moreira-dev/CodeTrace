// Tema:
// Un identificador estable permite deduplicar reintentos. El servidor guarda petición y respuesta; una reutilización de id con contenido distinto se rechaza. La deduplicación es local y en memoria: no garantiza exactly-once ante reinicios.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 Peticion.java ServidorIdempotente.java MainDuplicados.java
// Ejecutar: java MainDuplicados

public record Peticion(String id, int cantidad) {
    public Peticion {
        if (id == null || id.isBlank() || cantidad <= 0) throw new IllegalArgumentException("Peticion");
    }
}

// Salida esperada:
// 3
// 3
// 5
// Total: 5
// ID conflictivo
