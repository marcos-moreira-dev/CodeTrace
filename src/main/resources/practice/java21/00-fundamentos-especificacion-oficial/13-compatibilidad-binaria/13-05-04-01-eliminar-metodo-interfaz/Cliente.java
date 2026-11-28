// Tema:
// Eliminar un método de una interfaz puede causar NoSuchMethodError en un cliente compilado
// que todavía contiene una referencia a ese método.
// Compilar: seguir las etapas al final, desde la unidad.
// Ejecutar: java -cp salida Cliente
// Comandos desde la carpeta de la unidad.

class Cliente implements Servicio {
    public void mostrar() { System.out.println("hola"); }
    public static void main(String[] args) {
        Servicio s = new Cliente();
        s.mostrar();
    }
}

// Salida esperada:
// Etapa 1: javac --release 21 -cp salida -d salida v1/Servicio.java Cliente.java
// java -cp salida Cliente
// Resultado: hola
// Etapa 2: javac --release 21 -cp salida -d salida v2/Servicio.java
// java -cp salida Cliente
// Resultado: NoSuchMethodError
