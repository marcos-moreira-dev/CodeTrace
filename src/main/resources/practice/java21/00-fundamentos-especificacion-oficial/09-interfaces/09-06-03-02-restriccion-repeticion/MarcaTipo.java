// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.6.3; ejemplo 9.6.3-2
// Compilar: javac --release 21 -encoding UTF-8 -d . MarcaTipo.java MarcasTipo.java ClaseMarcada.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

import java.lang.annotation.*;
@Target(ElementType.TYPE)
@Repeatable(MarcasTipo.class)
@interface MarcaTipo {}

// Salida esperada:
// Error: container
