// Tema:
// Eliminar una sobrescritura puede hacer que un cliente ya compilado invoque la implementación
// heredada, sin necesidad de recompilar la llamada.
// Compilar: seguir las etapas al final, desde la unidad.
// Ejecutar: java -cp salida Cliente
// Comandos desde la carpeta de la unidad.

class Raiz { void mostrar() { System.out.println("raíz"); } }

// Salida esperada:
// Etapa 1: javac --release 21 -cp salida -d salida Raiz.java v1/Servicio.java Cliente.java
// java -cp salida Cliente
// Resultado: servicio
// Etapa 2: javac --release 21 -cp salida -d salida v2/Servicio.java
// java -cp salida Cliente
// Resultado: raíz
