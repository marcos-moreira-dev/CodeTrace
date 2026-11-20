// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practice" codetrace.practica.patrones.abstractfactory.MainAbstractFactory

package codetrace.practica.patrones.abstractfactory;

import java.util.List;

/**
 * Cliente Abstract Factory: familias de componentes de un mismo tema.
 *
 * <p>La aplicación consume Boton y Dialogo sin conocer sus variantes
 * concretas. Cambiar la fábrica intercambia la familia COMPLETA.
 * En un programa de consola "pintar" devuelve texto en vez de abrir
 * JavaFX. El ejemplo enseña familias compatibles sin forzar MVC.
 */
public final class MainAbstractFactory {
    private static String pantalla(FamiliaInterfaz.Fabrica fabrica) {
        return fabrica.boton().pintar()+" / "+fabrica.dialogo().pintar();
    }
    public static void main(String[] args) {
        var resultados=List.of(
            pantalla(new FamiliaInterfaz.Claro()),
            pantalla(new FamiliaInterfaz.Oscuro()));
        if(resultados.get(0).equals(resultados.get(1)))
            throw new AssertionError("Fábricas indistinguibles");
        resultados.forEach(System.out::println);
    }
}

/*
 * Salida esperada de consola:
 *   [ OK ] / Ventana clara
 *   < OK > / Ventana oscura
 * Las dos fábricas producen familias coherentes y distinguibles.
 */
