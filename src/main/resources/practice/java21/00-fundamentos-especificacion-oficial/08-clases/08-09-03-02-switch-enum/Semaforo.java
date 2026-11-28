// Tema:
// Un switch sobre un enum selecciona según la constante. Las etiquetas pueden utilizar los
// nombres simples de las constantes del tipo seleccionado.
// Compilar: javac --release 21 -encoding UTF-8 -d . Semaforo.java SwitchEnum.java
// Ejecutar: java SwitchEnum
// Comandos desde la carpeta de la unidad.

enum Semaforo { ROJO, AMARILLO, VERDE }

// Salida esperada:
// parar
// esperar
// avanzar
