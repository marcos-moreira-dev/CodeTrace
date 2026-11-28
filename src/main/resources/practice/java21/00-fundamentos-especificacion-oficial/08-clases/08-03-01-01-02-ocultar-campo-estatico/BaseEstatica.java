// Tema:
// Un campo static declarado en una subclase puede ocultar un campo heredado del mismo nombre.
// La selección depende del tipo usado, no del despacho dinámico.
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseEstatica.java OcultarEstatico.java
// Ejecutar: java OcultarEstatico
// Comandos desde la carpeta de la unidad.

class BaseEstatica { static int dato = 3; }

// Salida esperada:
// 6.5 3
