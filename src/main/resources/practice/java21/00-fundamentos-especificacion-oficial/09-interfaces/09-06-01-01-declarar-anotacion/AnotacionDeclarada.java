// Tema:
// Una interfaz de anotación se declara con @interface. Sus elementos definen los nombres y
// tipos de los valores que pueden especificarse al aplicar la anotación.
// Compilar: javac --release 21 -encoding UTF-8 -d . Ficha.java AnotacionDeclarada.java
// Ejecutar: java AnotacionDeclarada
// Comandos desde la carpeta de la unidad.

@Ficha(codigo = 3, descripcion = "prueba", autor = "equipo")
public class AnotacionDeclarada {
    public static void main(String[] args) { System.out.println("Anotación declarada"); }
}

// Salida esperada:
// Anotación declarada
