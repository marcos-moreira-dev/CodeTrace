// Tema:
// Un método default proporciona una implementación de instancia que las clases pueden
// heredar o sobrescribir.
// Compilar: javac --release 21 -encoding UTF-8 -d . Saludo.java Persona.java MetodoDefault.java
// Ejecutar: java MetodoDefault
// Comandos desde la carpeta de la unidad.

interface Saludo {
    default String mensaje() { return "hola"; }
    static String idioma() { return "es"; }
}

// Salida esperada:
// hola
// es
