// Tema:
// Un identificador estable permite deduplicar reintentos. El servidor guarda petición y respuesta; una reutilización de id con contenido distinto se rechaza. La deduplicación es local y en memoria: no garantiza exactly-once ante reinicios.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 Peticion.java ServidorIdempotente.java MainDuplicados.java
// Ejecutar: java MainDuplicados

import java.util.HashMap;
import java.util.Map;

public class ServidorIdempotente {
    private int total;
    private final Map<String,Peticion> procesadas = new HashMap<>();
    private final Map<String,Integer> respuestas = new HashMap<>();
    public int recibir(Peticion p) {
        Peticion anterior = procesadas.get(p.id());
        if (anterior != null) {
            if (!anterior.equals(p)) throw new IllegalArgumentException("ID reutilizado");
            return respuestas.get(p.id());
        }
        total = Math.addExact(total, p.cantidad());
        procesadas.put(p.id(), p);
        respuestas.put(p.id(), total);
        return total;
    }
    public int total() { return total; }
}

// Salida esperada:
// 3
// 3
// 5
// Total: 5
// ID conflictivo
