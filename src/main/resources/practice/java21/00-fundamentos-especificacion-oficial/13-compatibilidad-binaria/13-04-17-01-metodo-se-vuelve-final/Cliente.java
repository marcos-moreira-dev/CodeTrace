// Tema:
// Convertir un método en final puede romper la carga de una subclase previamente compilada que
// lo sobrescribe.
// Compilar: seguir las etapas al final, desde la unidad.
// Ejecutar: java -cp salida Cliente
// Comandos desde la carpeta de la unidad.

class Cliente extends Servicio {
    void mostrar() { super.mostrar(); }
    public static void main(String[] args) { new Cliente().mostrar(); }
}

// Salida esperada:
// Etapa 1: javac --release 21 -cp salida -d salida v1/Servicio.java Cliente.java
// java -cp salida Cliente
// Resultado: base
// Etapa 2: javac --release 21 -cp salida -d salida v2/Servicio.java
// java -cp salida Cliente
// Resultado: IncompatibleClassChangeError
