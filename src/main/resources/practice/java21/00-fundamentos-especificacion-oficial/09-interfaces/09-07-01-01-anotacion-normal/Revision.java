// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.7.1; ejemplo 9.7.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Revision.java AnotacionNormal.java
// Ejecutar: java AnotacionNormal
// Comandos desde la carpeta de la unidad.

import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@interface Revision { int codigo(); String descripcion(); String autor() default "equipo"; }

// Salida esperada:
// 5
// prueba
// equipo
