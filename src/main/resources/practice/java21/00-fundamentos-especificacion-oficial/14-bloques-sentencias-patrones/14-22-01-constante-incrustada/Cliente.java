// Tema:
// Las constantes de compilación pueden quedar incrustadas en el bytecode del cliente.
// Reemplazar la clase que las declara no cambia el valor del cliente sin recompilarlo.
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
