// Tema:
// Los parámetros de tipo suelen usar letras breves que indican su papel, como E para elemento,
// K para clave, V para valor y T para tipo.
// Compilar: javac --release 21 -encoding UTF-8 -d . Contenedor.java Par.java Calculo.java NombresGenericos.java
// Ejecutar: java NombresGenericos
// Comandos desde la carpeta de la unidad.

record Par<K,V>(K clave, V valor) {}

// Salida esperada:
// texto
// clave 7
