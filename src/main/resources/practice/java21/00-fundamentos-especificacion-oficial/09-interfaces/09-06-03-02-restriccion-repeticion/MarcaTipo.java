// Tema:
// La anotación contenedora debe ser aplicable a los contextos permitidos para la repetición.
// Un conjunto de destinos incompatible impide declarar o usar esa repetición.
// Compilar: javac --release 21 -encoding UTF-8 -d . MarcaTipo.java MarcasTipo.java ClaseMarcada.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

import java.lang.annotation.*;
@Target(ElementType.TYPE)
@Repeatable(MarcasTipo.class)
@interface MarcaTipo {}

// Salida esperada:
// Error: container
