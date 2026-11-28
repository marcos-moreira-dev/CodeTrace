// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §13.4.4; ejemplo 13.4.4-2
// Compilar: seguir las etapas al final
// Ejecutar: java -cp salida Cliente
// Comandos desde la carpeta de la unidad.

class ErrorA extends Exception {}

// Salida esperada:
// Etapa 1: javac --release 21 -cp salida -d salida ErrorA.java v1/ErrorB.java Cliente.java
// java -cp salida Cliente
// Resultado: capturado
// Etapa 2: javac --release 21 -cp salida -d salida v2/ErrorB.java
// java -cp salida Cliente
// Resultado: capturado
// Etapa 3: javac --release 21 -cp salida -d salida Cliente.java
// Rechazo esperado: Alternatives in a multi-catch statement cannot be related
