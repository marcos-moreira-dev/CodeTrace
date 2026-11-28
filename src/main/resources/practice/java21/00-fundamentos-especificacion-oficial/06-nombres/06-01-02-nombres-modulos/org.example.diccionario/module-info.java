// Tema:
// Un módulo tiene un nombre independiente del paquete. module-info.java declara el módulo y
// exports expone los paquetes que otros módulos pueden utilizar.
// Compilar: javac --release 21 --module-source-path . -d salida org.example.diccionario/module-info.java org.example.diccionario/org/example/diccionario/Palabra.java
// Ejecutar: java --module-path salida -m org.example.diccionario/org.example.diccionario.Palabra
// Comandos desde la carpeta de la unidad.

module org.example.diccionario { exports org.example.diccionario; }

// Salida esperada:
// org.example.diccionario
