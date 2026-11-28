// Tema:
// Los campos de instancia también se ocultan en lugar de sobrescribirse. El tipo estático de
// la referencia y super determinan cuál de los campos se lee.
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseInstancia.java OcultarInstancia.java
// Ejecutar: java OcultarInstancia
// Comandos desde la carpeta de la unidad.

class BaseInstancia { int dato = 3; }

// Salida esperada:
// 6.5 3
// 6.5 3
