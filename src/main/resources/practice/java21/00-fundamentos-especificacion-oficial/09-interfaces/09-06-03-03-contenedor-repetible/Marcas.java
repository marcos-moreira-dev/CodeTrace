// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.6.3; ejemplo 9.6.3-3
// Compilar: javac --release 21 -encoding UTF-8 -d . Marca.java Marcas.java Grupos.java ContenedorRepetible.java
// Ejecutar: java ContenedorRepetible
// Comandos desde la carpeta de la unidad.

import java.lang.annotation.*;
@Repeatable(Grupos.class)
@interface Marcas { Marca[] value(); }

// Salida esperada:
// válido
