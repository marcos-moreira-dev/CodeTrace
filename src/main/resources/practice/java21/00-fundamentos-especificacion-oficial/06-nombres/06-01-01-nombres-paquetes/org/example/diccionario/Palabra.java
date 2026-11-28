// Tema:
// Los nombres de paquete suelen seguir un dominio invertido para reducir colisiones. El nombre
// completo distingue tipos que podrían tener el mismo nombre simple.
// Compilar: javac --release 21 -encoding UTF-8 -d . org/example/diccionario/Palabra.java
// Ejecutar: java org.example.diccionario.Palabra
// Comandos desde la carpeta de la unidad.

package org.example.diccionario;
// La convención usa el dominio invertido. example.org está reservado para ejemplos.
public class Palabra {
    public static void main(String[] args) {
        System.out.println(Palabra.class.getPackageName());
    }
}

// Salida esperada:
// org.example.diccionario
