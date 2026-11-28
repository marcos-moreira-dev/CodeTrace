// Tema:
// Un elemento de anotación es una declaración de método sin parámetros cuyo tipo restringe los
// valores permitidos en la anotación.
// Compilar: javac --release 21 -encoding UTF-8 -d . Identificador.java ElementoAnotacion.java
// Ejecutar: java ElementoAnotacion
// Comandos desde la carpeta de la unidad.

@interface Identificador { String value(); }

// Salida esperada:
// prueba
