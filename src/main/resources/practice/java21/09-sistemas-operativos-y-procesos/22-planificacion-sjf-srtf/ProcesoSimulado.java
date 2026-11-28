// Tema:
// SJF elige el trabajo listo más corto y lo ejecuta completo; SRTF reevalúa el tiempo restante cada unidad y puede expulsar. Duraciones enteras positivas y llegadas no negativas; empate por orden del arreglo.
// Modalidad: modelo teórico; no reproduce el kernel real.
// Compilar: javac --release 21 -encoding UTF-8 ProcesoSimulado.java SJF.java SRTF.java MainPlanificacion.java
// Ejecutar: java MainPlanificacion

public record ProcesoSimulado(String id, int llegada, int duracion) { }

// Salida esperada:
// SJF: AAAACBB
// SRTF: ABBCAAA
