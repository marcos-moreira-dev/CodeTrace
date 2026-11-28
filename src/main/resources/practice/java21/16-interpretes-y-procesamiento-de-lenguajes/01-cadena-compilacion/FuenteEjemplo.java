// Tema:
// javac transforma la fuente .java en un archivo .class con bytecode. java carga y ejecuta la clase mediante la JVM; el fuente no es el ejecutable nativo.
// Modalidad: ejecución real con el JDK.
// Compilar: javac --release 21 -encoding UTF-8 FuenteEjemplo.java
// Ejecutar: java FuenteEjemplo
// Comprobar: observar FuenteEjemplo.class después de compilar; no es un archivo fuente.

public class FuenteEjemplo {
    public static void main(String[] args) throws Exception {
        int cajas = 3;
        int unidades = 4;
        System.out.println(cajas * unidades);
    }
}

// Salida esperada:
// 12
