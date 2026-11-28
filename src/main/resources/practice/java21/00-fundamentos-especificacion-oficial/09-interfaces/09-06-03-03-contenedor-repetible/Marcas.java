// Tema:
// Una anotación contenedora también puede ser repetible si satisface las reglas de su propio
// contenedor. Esto permite declarar varias agrupaciones.
// Compilar: javac --release 21 -encoding UTF-8 -d . Marca.java Marcas.java Grupos.java ContenedorRepetible.java
// Ejecutar: java ContenedorRepetible
// Comandos desde la carpeta de la unidad.

import java.lang.annotation.*;
@Repeatable(Grupos.class)
@interface Marcas { Marca[] value(); }

// Salida esperada:
// válido
