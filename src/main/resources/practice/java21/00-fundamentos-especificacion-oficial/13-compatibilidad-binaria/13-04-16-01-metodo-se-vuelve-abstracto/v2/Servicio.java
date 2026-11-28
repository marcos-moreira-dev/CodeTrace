// Tema:
// Convertir un método concreto en abstracto puede impedir que un cliente anterior encuentre
// una implementación al invocarlo, causando AbstractMethodError.
// Esta versión sustituye a la inicial sin recompilar primero el cliente.
// Compilar: seguir las etapas al final, desde la unidad.
// Ejecutar: java -cp salida Cliente
// Comandos desde la carpeta de la unidad.

abstract class Servicio { abstract void mostrar(); }

// Salida esperada:
// Etapa 1: javac --release 21 -cp salida -d salida v1/Servicio.java Cliente.java
// java -cp salida Cliente
// Resultado: inicio / base
// Etapa 2: javac --release 21 -cp salida -d salida v2/Servicio.java
// java -cp salida Cliente
// Resultado: AbstractMethodError
