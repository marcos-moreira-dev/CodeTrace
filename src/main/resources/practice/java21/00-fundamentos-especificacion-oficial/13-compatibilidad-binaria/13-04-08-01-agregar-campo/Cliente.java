// Tema:
// Agregar un campo que oculta otro heredado no cambia necesariamente el campo que seleccionaba
// un cliente previamente compilado. La referencia binaria conserva el tipo y descriptor
// originales.
// Compilar: seguir las etapas al final, desde la unidad.
// Ejecutar: java -cp salida Cliente
// Comandos desde la carpeta de la unidad.

class Cliente {
    public static void main(String[] args) { System.out.println(new Servicio().dato); }
}

// Salida esperada:
// Etapa 1: javac --release 21 -cp salida -d salida Raiz.java v1/Servicio.java Cliente.java
// java -cp salida Cliente
// Resultado: original
// Etapa 2: javac --release 21 -cp salida -d salida v2/Servicio.java
// java -cp salida Cliente
// Resultado: original
