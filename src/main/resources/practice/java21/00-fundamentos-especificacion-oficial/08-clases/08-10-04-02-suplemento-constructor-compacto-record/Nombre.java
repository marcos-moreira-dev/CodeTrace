// Tema:
// Un constructor compacto permite validar o normalizar los parámetros implícitos. Al
// terminar normalmente, Java asigna los parámetros a los campos de componentes.
// Compilar: javac --release 21 -encoding UTF-8 -d . Nombre.java ConstructorCompacto.java
// Ejecutar: java ConstructorCompacto
// Comandos desde la carpeta de la unidad.

record Nombre(String texto) {
    Nombre {
        texto = texto.strip();
        if (texto.isEmpty()) throw new IllegalArgumentException("vacío");
    }
}

// Salida esperada:
// [Ana]
// vacío
