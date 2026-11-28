// Tema:
// Un record declara sus componentes en la cabecera. El compilador genera campos privados
// finales, constructor canónico y accesores del mismo nombre.
// Compilar: javac --release 21 -encoding UTF-8 -d . Persona.java ComponentesRecord.java
// Ejecutar: java ComponentesRecord
// Comandos desde la carpeta de la unidad.

record Persona(String nombre, int edad) {}

// Salida esperada:
// Ana
// 21
