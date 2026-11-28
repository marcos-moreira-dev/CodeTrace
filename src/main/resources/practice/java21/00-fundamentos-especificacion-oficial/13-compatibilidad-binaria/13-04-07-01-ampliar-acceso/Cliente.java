// Tema:
// Ampliar el acceso de un método puede conservar la compatibilidad de binarios anteriores
// aunque una nueva compilación exija revisar los modificadores de una sobrescritura.
// Compilar: seguir las etapas al final, desde la unidad.
// Ejecutar: java -cp salida Cliente
// Comandos desde la carpeta de la unidad.

class Cliente extends Servicio {
    protected void mostrar() { System.out.println("cliente"); }
    public static void main(String[] args) { new Cliente().mostrar(); }
}

// Salida esperada:
// Etapa 1: javac --release 21 -cp salida -d salida v1/Servicio.java Cliente.java
// java -cp salida Cliente
// Resultado: cliente
// Etapa 2: javac --release 21 -cp salida -d salida v2/Servicio.java
// java -cp salida Cliente
// Resultado: cliente
