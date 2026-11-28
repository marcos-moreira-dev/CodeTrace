// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.7.3; ejemplo 9.7.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Etiqueta.java AnotacionValue.java
// Ejecutar: java AnotacionValue
// Comandos desde la carpeta de la unidad.

@interface Etiqueta { String value(); }

// Salida esperada:
// value implícito
