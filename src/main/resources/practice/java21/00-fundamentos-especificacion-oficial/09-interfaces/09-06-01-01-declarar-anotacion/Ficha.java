// Tema:
// Una interfaz de anotación se declara con @interface. Sus elementos definen los nombres y
// tipos de los valores que pueden especificarse al aplicar la anotación.
// Compilar: javac --release 21 -encoding UTF-8 -d . Ficha.java AnotacionDeclarada.java
// Ejecutar: java AnotacionDeclarada
// Comandos desde la carpeta de la unidad.

@interface Ficha {
    int codigo();
    String descripcion();
    String autor();
}

// Salida esperada:
// Anotación declarada
