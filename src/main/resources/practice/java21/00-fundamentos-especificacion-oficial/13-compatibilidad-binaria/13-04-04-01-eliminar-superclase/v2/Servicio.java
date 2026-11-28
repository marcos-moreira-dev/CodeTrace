// Tema:
// Un cliente compilado suponiendo una relación de herencia puede dejar de ser verificable si
// se reemplaza la clase por una versión que elimina su superclase.
// Esta versión sustituye a la inicial sin recompilar primero el cliente.
// Compilar: seguir las etapas al final, desde la unidad.
// Ejecutar: java -cp salida Cliente
// Comandos desde la carpeta de la unidad.

class Servicio { int otro = 4; }

// Salida esperada:
// Etapa 1: javac --release 21 -cp salida -d salida Raiz.java v1/Servicio.java Cliente.java
// java -cp salida Cliente
// Resultado: 7
// Etapa 2: javac --release 21 -cp salida -d salida v2/Servicio.java
// java -cp salida Cliente
// Resultado: VerifyError
