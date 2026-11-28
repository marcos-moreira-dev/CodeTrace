// Tema:
// Un tipo declarado en el paquete puede tener prioridad sobre un nombre ofrecido por una
// importación bajo demanda. El nombre calificado permite desambiguar.
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
