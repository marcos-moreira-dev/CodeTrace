// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §13.5.7; ejemplo 13.5.7-1
// Compilar: seguir las etapas al final, desde la unidad.
// Ejecutar: java -cp salida Cliente
// Comandos desde la carpeta de la unidad.

class Cliente implements Servicio, Original {
    public static void main(String[] args) { new Cliente().mostrar(); }
}

// Salida esperada:
// Etapa 1: javac --release 21 -cp salida -d salida Original.java v1/Servicio.java Cliente.java
// java -cp salida Cliente
// Resultado: original
// Etapa 2: javac --release 21 -cp salida -d salida v2/Servicio.java
// java -cp salida Cliente
// Resultado: IncompatibleClassChangeError
