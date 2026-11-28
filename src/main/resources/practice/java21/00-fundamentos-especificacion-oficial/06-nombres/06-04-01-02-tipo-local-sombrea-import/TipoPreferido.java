// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.4.1; ejemplo 6.4.1-2
// Compilar: javac --release 21 -encoding UTF-8 -d . Date.java TipoPreferido.java
// Ejecutar: java TipoPreferido
// Comandos desde la carpeta de la unidad.

import java.util.*;
public class TipoPreferido {
    public static void main(String[] args) {
        Date fecha = new Date();
        System.out.println(fecha.dia);
    }
}

// Salida esperada:
// 12
