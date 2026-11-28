// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §13.4.4; ejemplo 13.4.4-1
// Compilar: seguir las etapas al final, desde la unidad.
// Ejecutar: java -cp salida Cliente
// Comandos desde la carpeta de la unidad.

class Cliente {
    static void mostrar(Raiz r) { System.out.println(r.valor); }
    public static void main(String[] args) { mostrar(new Servicio()); }
}

// Salida esperada:
// Etapa 1: javac --release 21 -cp salida -d salida Raiz.java v1/Servicio.java Cliente.java
// java -cp salida Cliente
// Resultado: 7
// Etapa 2: javac --release 21 -cp salida -d salida v2/Servicio.java
// java -cp salida Cliente
// Resultado: VerifyError
