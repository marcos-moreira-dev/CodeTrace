// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.7.2; ejemplo 9.7.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Preliminar.java AnotacionMarca.java
// Ejecutar: java AnotacionMarca
// Comandos desde la carpeta de la unidad.

import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@interface Preliminar {}

// Salida esperada:
// true
