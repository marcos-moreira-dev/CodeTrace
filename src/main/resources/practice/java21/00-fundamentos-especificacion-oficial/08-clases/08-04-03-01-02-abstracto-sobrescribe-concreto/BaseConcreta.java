// Tema:
// Una subclase abstracta puede volver abstracto un método concreto heredado, obligando a las
// clases concretas posteriores a definir una nueva implementación.
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseConcreta.java Obligatoria.java AbstractoConcreto.java
// Ejecutar: java AbstractoConcreto
// Comandos desde la carpeta de la unidad.

class BaseConcreta { String dato() { return "base"; } }

// Salida esperada:
// nuevo
