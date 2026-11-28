// Tema:
// Si la anotación contiene un elemento value y no se especifican otros elementos obligatorios,
// puede usarse la forma abreviada sin escribir value=.
// Compilar: javac --release 21 -encoding UTF-8 -d . Etiqueta.java AnotacionValue.java
// Ejecutar: java AnotacionValue
// Comandos desde la carpeta de la unidad.

@Etiqueta("práctica")
public class AnotacionValue {
    public static void main(String[] args) { System.out.println("value implícito"); }
}

// Salida esperada:
// value implícito
