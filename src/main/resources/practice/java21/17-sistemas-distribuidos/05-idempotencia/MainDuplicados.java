// Tema:
// Un identificador estable permite deduplicar reintentos. El servidor guarda petición y respuesta; una reutilización de id con contenido distinto se rechaza. La deduplicación es local y en memoria: no garantiza exactly-once ante reinicios.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 Peticion.java ServidorIdempotente.java MainDuplicados.java
// Ejecutar: java MainDuplicados

public class MainDuplicados {
    public static void main(String[] args) {
        ServidorIdempotente servidor = new ServidorIdempotente();
        Peticion p = new Peticion("p1", 3);
        System.out.println(servidor.recibir(p));
        System.out.println(servidor.recibir(p));
        System.out.println(servidor.recibir(new Peticion("p2", 2)));
        System.out.println("Total: " + servidor.total());
        try { servidor.recibir(new Peticion("p1", 99)); }
        catch (IllegalArgumentException e) { System.out.println("ID conflictivo"); }
    }
}

// Salida esperada:
// 3
// 3
// 5
// Total: 5
// ID conflictivo
