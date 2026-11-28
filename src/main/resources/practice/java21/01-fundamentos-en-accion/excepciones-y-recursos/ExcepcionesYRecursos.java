// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete, PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" ExcepcionesYRecursos

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Objects;

/**
 * Cliente de checked exceptions y try-with-resources.
 *
 * <p>Una excepción comprobada expresa un fallo que el cliente debe
 * capturar o propagar. try-with-resources cierra un AutoCloseable aunque
 * se lance una excepción durante su uso. Aquí StringReader evita archivos
 * externos y permite estudiar el contrato sin efectos secundarios.
 */
public class ExcepcionesYRecursos {
    static int leerNumero(String texto) throws java.io.IOException {
        Objects.requireNonNull(texto);
        try (BufferedReader entrada = new BufferedReader(new StringReader(texto))) {
            return Integer.parseInt(entrada.readLine());
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("Número: " + leerNumero("25"));
        } catch (java.io.IOException | NumberFormatException ex) {
            System.out.println("Entrada inválida: " + ex.getMessage());
        }
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out ExcepcionesYRecursos
 * Salida esperada:
 *   Número: 25
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
