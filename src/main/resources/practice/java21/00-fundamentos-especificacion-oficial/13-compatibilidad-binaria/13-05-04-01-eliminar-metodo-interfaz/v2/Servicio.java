// Tema:
// Eliminar un método de una interfaz puede causar NoSuchMethodError en un cliente compilado
// que todavía contiene una referencia a ese método.
// Esta versión sustituye a la inicial sin recompilar primero el cliente.
// Compilar: seguir las etapas al final, desde la unidad.
// Ejecutar: java -cp salida Cliente
// Comandos desde la carpeta de la unidad.

interface Servicio {}

// Salida esperada:
// Etapa 1: javac --release 21 -cp salida -d salida v1/Servicio.java Cliente.java
// java -cp salida Cliente
// Resultado: hola
// Etapa 2: javac --release 21 -cp salida -d salida v2/Servicio.java
// java -cp salida Cliente
// Resultado: NoSuchMethodError
