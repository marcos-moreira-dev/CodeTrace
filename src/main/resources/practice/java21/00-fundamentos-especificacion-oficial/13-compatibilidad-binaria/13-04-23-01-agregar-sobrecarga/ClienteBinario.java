// Tema:
// Una sobrecarga agregada después de compilar un cliente no modifica la firma elegida por el
// compilador anterior. Al recompilar, la resolución puede seleccionar la nueva sobrecarga.
// Compilar: seguir las tres etapas indicadas al final.
// Ejecutar: java -cp salida ClienteBinario
// Comandos desde la carpeta de la unidad.

public class ClienteBinario { public static void main(String[] args) { SalidaBinaria.mostrar(2); } }

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
