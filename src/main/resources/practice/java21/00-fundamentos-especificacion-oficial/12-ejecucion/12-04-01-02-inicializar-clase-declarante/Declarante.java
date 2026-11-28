// Tema:
// Acceder a un campo static no constante inicializa la clase que lo declara, aunque se escriba
// el nombre de una subclase para acceder a él.
// Compilar: javac --release 21 -encoding UTF-8 -d . Declarante.java Heredera.java InicializarDeclarante.java
// Ejecutar: java InicializarDeclarante
// Comandos desde la carpeta de la unidad.

class Declarante { static int numero = 43; }

// Salida esperada:
// 43
