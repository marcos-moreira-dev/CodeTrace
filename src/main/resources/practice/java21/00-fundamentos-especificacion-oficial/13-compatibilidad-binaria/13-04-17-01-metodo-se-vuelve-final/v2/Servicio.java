// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §13.4.17; ejemplo 13.4.17-1
// Compilar: seguir las etapas al final, desde la unidad.
// Ejecutar: java -cp salida Cliente
// Comandos desde la carpeta de la unidad.

class Servicio { final void mostrar() {} }

// Salida esperada:
// Etapa 1: javac --release 21 -cp salida -d salida v1/Servicio.java Cliente.java
// java -cp salida Cliente
// Resultado: base
// Etapa 2: javac --release 21 -cp salida -d salida v2/Servicio.java
// java -cp salida Cliente
// Resultado: IncompatibleClassChangeError
