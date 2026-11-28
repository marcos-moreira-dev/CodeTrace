// Tema:
// Modelo de caché con TTL: la respuesta se reutiliza mientras el reloj simulado sea menor a su vencimiento. No consulta ni modifica DNS real.
// Modalidad: modelo y ejecución.
// Compilar: javac --release 21 -encoding UTF-8 CacheDNSSimulada.java
// Ejecutar: java CacheDNSSimulada

public class CacheDNSSimulada {
    public static void main(String[] args) throws Exception {
        int recibido = 10, ttl = 5;
        for (int reloj : new int[]{10, 14, 15}) {
            boolean vigente = reloj < recibido + ttl;
            System.out.println(reloj + ": " + (vigente ? "Cache" : "Expirado"));
        }
    }
}

// Salida esperada:
// 10: Cache
// 14: Cache
// 15: Expirado
