// Tema:
// El método default se invoca sobre la instancia, mientras que el método static se
// selecciona con el nombre de la interfaz.
// Compilar: javac --release 21 -encoding UTF-8 -d . Saludo.java Persona.java MetodoDefault.java
// Ejecutar: java MetodoDefault
// Comandos desde la carpeta de la unidad.

public class MetodoDefault {
    public static void main(String[] args) {
        Saludo s = new Persona();
        System.out.println(s.mensaje());
        System.out.println(Saludo.idioma());
    }
}

// Salida esperada:
// hola
// es
