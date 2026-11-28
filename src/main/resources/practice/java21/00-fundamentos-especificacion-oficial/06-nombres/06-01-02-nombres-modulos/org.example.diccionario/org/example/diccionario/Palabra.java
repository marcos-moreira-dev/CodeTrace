// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.1; ejemplo 6.1-2
// Compilar: javac --release 21 --module-source-path . -d salida org.example.diccionario/module-info.java org.example.diccionario/org/example/diccionario/Palabra.java
// Ejecutar: java --module-path salida -m org.example.diccionario/org.example.diccionario.Palabra
// Comandos desde la carpeta de la unidad.

package org.example.diccionario;
public class Palabra {
    public static void main(String[] args) {
        System.out.println(Palabra.class.getModule().getName());
    }
}

// Salida esperada:
// org.example.diccionario
