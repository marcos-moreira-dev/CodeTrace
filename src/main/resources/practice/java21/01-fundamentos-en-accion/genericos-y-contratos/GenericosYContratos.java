// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete, PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' } | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" GenericosYContratos

import java.util.ArrayList;
import java.util.List;

/**
 * Cliente de genéricos, límites y comodines.
 *
 * <p>T limita los valores aceptados y elimina conversiones innecesarias.
 * Un productor puede exponerse como List<? extends Number> para lectura;
 * un receptor List<? super Integer> admite insertar Integer. Esto
 * ejemplifica PECS (Producer Extends, Consumer Super), sin desarrollar
 * una jerarquía que no ayude al ejemplo.
 */
public class GenericosYContratos {
    static double sumar(List<? extends Number> fuente) {
        double total = 0;
        for (Number n : fuente) total += n.doubleValue();
        return total;
    }

    static void agregarEnteros(List<? super Integer> destino, int... valores) {
        for (int valor : valores) destino.add(valor);
    }

    public static void main(String[] args) {
        List<Number> numeros = new ArrayList<>();
        agregarEnteros(numeros, 2, 3, 5);
        System.out.println("Total: " + sumar(numeros));
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
 *   java -cp $out GenericosYContratos
 * Salida esperada:
 *   Total: 10.0
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
