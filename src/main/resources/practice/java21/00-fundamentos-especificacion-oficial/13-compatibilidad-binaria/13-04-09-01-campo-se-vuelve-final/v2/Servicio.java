// Tema:
// Si un campo antes modificable se vuelve final, un cliente que conserva una instrucción de
// escritura compilada anteriormente puede fallar en ejecución con IllegalAccessError.
// Esta versión sustituye a la inicial sin recompilar primero el cliente.
// Compilar: seguir las etapas al final, desde la unidad.
// Ejecutar: java -cp salida Cliente
// Comandos desde la carpeta de la unidad.

class Servicio { final char letra = 'Z'; }

// Salida esperada:
// Etapa 1: javac --release 21 -cp salida -d salida v1/Servicio.java Cliente.java
// java -cp salida Cliente
// Resultado: A
// Etapa 2: javac --release 21 -cp salida -d salida v2/Servicio.java
// java -cp salida Cliente
// Resultado: IllegalAccessError
