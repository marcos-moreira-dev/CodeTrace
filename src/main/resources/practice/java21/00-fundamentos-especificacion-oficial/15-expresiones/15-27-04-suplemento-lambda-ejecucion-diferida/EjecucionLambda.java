// Tema:
// Evaluar una lambda produce una referencia a una interfaz funcional. Su cuerpo no se
// ejecuta en ese momento: se ejecuta cuando se invoca el método funcional.
// Compilar: javac --release 21 -encoding UTF-8 -d . EjecucionLambda.java
// Ejecutar: java EjecucionLambda
// Comandos desde la carpeta de la unidad.

public class EjecucionLambda {
    public static void main(String[] args) {
        Runnable tarea = () -> System.out.println("cuerpo");
        System.out.println("creada");
        tarea.run();
        tarea.run();
    }
}

// Salida esperada:
// creada
// cuerpo
// cuerpo
