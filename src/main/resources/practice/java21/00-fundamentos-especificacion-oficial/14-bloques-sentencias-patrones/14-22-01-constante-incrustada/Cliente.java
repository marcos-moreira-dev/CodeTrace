// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §14.22; ejemplo 14.22-1
// Compilar: seguir las etapas al final, desde la unidad.
// Ejecutar: java -cp salida Cliente
// Comandos desde la carpeta de la unidad.

class Cliente {
    public static void main(String[] args) {
        if (Servicio.DEBUG) System.out.println("depuración");
    }
}

// Salida esperada:
// Etapa 1: javac --release 21 -cp salida -d salida v1/Servicio.java Cliente.java
// java -cp salida Cliente
// Resultado: depuración
// Etapa 2: javac --release 21 -cp salida -d salida v2/Servicio.java
// java -cp salida Cliente
// Resultado: depuración
