// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.6.2; ejemplo 9.6.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Tarea.java AnotacionDefault.java
// Ejecutar: java AnotacionDefault
// Comandos desde la carpeta de la unidad.

@interface Tarea {
    int codigo();
    String responsable() default "pendiente";
}

// Salida esperada:
// pendiente
