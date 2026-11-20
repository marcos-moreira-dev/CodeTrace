// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete, PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" ConcurrenciaVirtual

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Cliente de tareas y virtual threads en Java 21.
 *
 * <p>Un ExecutorService recibe tareas; Future permite conocer el resultado
 * y observar excepciones. Los hilos virtuales facilitan concurrencia con
 * operaciones bloqueantes, pero no hacen que el cálculo sea más rápido
 * automáticamente ni garantizan paralelismo de CPU.
 *
 * <p>try-with-resources cierra el executor y espera a sus tareas.
 * La demostración usa operaciones deterministas, sin Internet.
 */
public class ConcurrenciaVirtual {
    public static void main(String[] args) throws Exception {
        try (ExecutorService tareas = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<Integer> primero = tareas.submit(() -> 20 + 22);
            Future<String> segundo = tareas.submit(() -> "tarea finalizada");
            System.out.println(primero.get());
            System.out.println(segundo.get());
        }
    }
}
