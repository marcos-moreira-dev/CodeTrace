// Tema:
// Un record no puede declarar campos de instancia adicionales a los componentes de su
// cabecera. Los miembros static sí pueden declararse.
// Compilar: javac --release 21 -encoding UTF-8 -d . RecordInvalido.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

record RecordInvalido(int dato) {
    int adicional = 2;
}

// Salida esperada:
// Error: field declaration must be static
