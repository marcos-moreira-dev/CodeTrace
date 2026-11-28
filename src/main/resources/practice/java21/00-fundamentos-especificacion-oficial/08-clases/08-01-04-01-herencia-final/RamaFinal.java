// Tema:
// Una clase final puede heredar de otra clase, pero no puede tener subclases. Intentar
// extenderla debe producir un error de compilación.
// Compilar: javac --release 21 -encoding UTF-8 -d . RaizFinal.java RamaFinal.java HojaInvalida.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

final class RamaFinal extends RaizFinal {}

// Salida esperada:
// Error: cannot inherit from final
