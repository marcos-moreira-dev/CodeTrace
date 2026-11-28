// Tema:
// Planificación no expulsiva: un valor mayor indica prioridad mayor. Aging aumenta la prioridad por espera; puede reducir inanición, no garantizar ausencia bajo cualquier carga.
// Modalidad: modelo teórico; no reproduce el kernel real.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoSimulado.java Prioridades.java Aging.java MainPrioridades.java
// Ejecutar: java MainPrioridades

public record ProcesoSimulado(String id, int llegada, int duracion, int prioridad) { }

// Salida esperada:
// Fija: ACB
// Aging: ABC
