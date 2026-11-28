// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.6.1; ejemplo 9.6.1-1
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
