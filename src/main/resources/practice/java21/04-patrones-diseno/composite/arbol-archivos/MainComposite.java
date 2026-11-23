// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Árbol de archivos de mapas y recursos del videojuego.
// Patrón composite: Tratar hojas y contenedores con una misma interfaz.
// Glosario: Mapa: escenario virtual; recurso: archivo usado por el juego.
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practice" codetrace.practica.patrones.composite.MainComposite

package codetrace.practica.patrones.composite;

/**
 * Cliente Composite: archivos y carpetas son Elemento.
 *
 * <p>El cliente calcula el tamaño total sin condicionales por tipo,
 * porque hojas y compuestos exponen el mismo contrato. Una carpeta
 * puede contener otra carpeta; es un árbol, NO un grafo cíclico.
 * Es responsabilidad del dominio impedir introducir ancestros,
 * omitida aquí salvo el caso directo para mantener el ejemplo breve.
 */
public final class MainComposite {
    public static void main(String[] args) {
        var raiz=new Carpeta("proyecto");
        var fuentes=new Carpeta("src");
        fuentes.agregar(new Archivo("Main.java",120));
        fuentes.agregar(new Archivo("App.java",80));
        raiz.agregar(fuentes);raiz.agregar(new Archivo("README.md",20));
        if(raiz.bytes()!=220)throw new AssertionError("Composite");
        System.out.println("Bytes: "+raiz.bytes());
    }
}

/*
 * Salida esperada de consola:
 *   Bytes: 220
 * La suma recursiva incluye Main.java (120), App.java (80) y README.md (20).
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.patrones.composite.MainComposite
 * Salida esperada:
 *   Bytes: 220
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
