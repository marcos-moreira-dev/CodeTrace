// Tema:
// private restringe el acceso al contexto de la clase que lo declara y sus tipos anidados
// relacionados. Una subclase independiente no puede leer directamente ese campo.
// Compilar: javac --release 21 -encoding UTF-8 -d . Privado.java AccesoPrivado.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class AccesoPrivado extends Privado { int leer() { return super.dato; } }

// Salida esperada:
// Error: dato has private access
