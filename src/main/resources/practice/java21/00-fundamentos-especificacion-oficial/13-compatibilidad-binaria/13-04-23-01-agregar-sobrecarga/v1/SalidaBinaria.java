// Tema:
// Una sobrecarga agregada después de compilar un cliente no modifica la firma elegida por el
// compilador anterior. Al recompilar, la resolución puede seleccionar la nueva sobrecarga.
// Esta es la versión inicial; se compila junto al cliente antes de sustituirla.
// Compilar: seguir las tres etapas indicadas al final.
// Ejecutar: java -cp salida ClienteBinario
// Comandos desde la carpeta de la unidad.

class SalidaBinaria { static void mostrar(long n) { System.out.println("long"); } }

// Salida esperada:
// long
// long
// int
// Etapas:
// javac --release 21 -d salida v1/SalidaBinaria.java ClienteBinario.java
// java -cp salida ClienteBinario
// javac --release 21 -cp salida -d salida v2/SalidaBinaria.java
// java -cp salida ClienteBinario
// javac --release 21 -cp salida -d salida ClienteBinario.java
// java -cp salida ClienteBinario
