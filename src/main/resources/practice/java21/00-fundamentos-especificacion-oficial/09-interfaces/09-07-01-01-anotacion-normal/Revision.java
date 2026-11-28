// Tema:
// Una anotación normal proporciona pares nombre-valor. Los elementos sin default son
// obligatorios y los restantes pueden omitirse.
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
