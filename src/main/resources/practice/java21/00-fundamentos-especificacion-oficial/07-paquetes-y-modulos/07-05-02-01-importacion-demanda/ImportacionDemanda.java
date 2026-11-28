// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §7.5.2; ejemplo 7.5.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . ImportacionDemanda.java
// Ejecutar: java ImportacionDemanda
// Comandos desde la carpeta de la unidad.

import java.util.*;
public class ImportacionDemanda {
    public static void main(String[] args) {
        List<String> lista = new ArrayList<>();
        lista.add("uno");
        System.out.println(lista.get(0));
    }
}

// Salida esperada:
// uno
