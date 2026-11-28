// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.6.1; ejemplo 9.6.1-3
// Compilar: javac --release 21 -encoding UTF-8 -d . Identificador.java ElementoAnotacion.java
// Ejecutar: java ElementoAnotacion
// Comandos desde la carpeta de la unidad.

@Identificador("prueba")
public class ElementoAnotacion {
    public static void main(String[] args) { System.out.println("prueba"); }
}

// Salida esperada:
// prueba
