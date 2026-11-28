// Tema:
// La creación de una instancia establece primero valores predeterminados. Se ejecutan
// constructores e inicializadores de la jerarquía de superclase a subclase.
// Compilar: javac --release 21 -encoding UTF-8 -d . PuntoInicial.java PuntoExtendido.java CrearInstancia.java
// Ejecutar: java CrearInstancia
// Comandos desde la carpeta de la unidad.

class PuntoExtendido extends PuntoInicial { int color = 8; }

// Salida esperada:
// 2 3 8
