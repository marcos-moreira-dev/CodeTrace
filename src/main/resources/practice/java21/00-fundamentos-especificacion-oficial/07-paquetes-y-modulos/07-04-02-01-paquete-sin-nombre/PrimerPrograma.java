// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §7.4.2; ejemplo 7.4.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . PrimerPrograma.java
// Ejecutar: java PrimerPrograma
// Comandos desde la carpeta de la unidad.

public class PrimerPrograma {
    public static void main(String[] args) {
        // No hay declaración package: esta clase pertenece al paquete sin nombre.
        System.out.println("Primer programa");
        System.out.println(PrimerPrograma.class.getPackageName().isEmpty());
    }
}

// Salida esperada:
// Primer programa
// true
