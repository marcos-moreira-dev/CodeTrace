// Tema:
// import paquete.* permite resolver los nombres simples de tipos accesibles del paquete. No
// importa recursivamente los tipos de subpaquetes.
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
