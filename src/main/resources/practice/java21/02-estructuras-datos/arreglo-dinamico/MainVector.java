// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete, PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.vector.MainVector

package codetrace.practica.vector;

/**
 * Cliente para distinguir longitud lógica y capacidad de un vector.
 *
 * <p>Se agregan seis valores a un almacenamiento que inicia con
 * capacidad dos. La estrategia geométrica amortiza las copias. El
 * cliente demuestra la API pública sin tocar el arreglo interno.
 *
 * <p>Roles: VectorEnteros administra memoria y valida índices;
 * MainVector prepara un caso, ejecuta operaciones y presenta resultados.
 */
public final class MainVector {
    public static void main(String[] args) {
        VectorEnteros vector = new VectorEnteros();
        for (int i = 1; i <= 6; i++) vector.agregar(i * 10);
        System.out.println("Elementos: " + vector.tamaño());
        System.out.println("Capacidad: " + vector.capacidad());
        System.out.println("Tercero: " + vector.obtener(2));
    }
}

/*
 * Salida esperada de consola:
 *   Elementos: 
 *   Capacidad: 
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
