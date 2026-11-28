// Tema:
// Un elemento de anotación puede declarar un valor default. Al omitirse ese elemento al
// aplicar la anotación se utiliza el valor predeterminado.
// Compilar: javac --release 21 -encoding UTF-8 -d . Tarea.java AnotacionDefault.java
// Ejecutar: java AnotacionDefault
// Comandos desde la carpeta de la unidad.

@Tarea(codigo = 8)
public class AnotacionDefault {
    public static void main(String[] args) throws ReflectiveOperationException {
        System.out.println(Tarea.class.getDeclaredMethod("responsable").getDefaultValue());
    }
}

// Salida esperada:
// pendiente
