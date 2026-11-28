// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.8.7.1; ejemplo 8.8.7.1-2
// Compilar: javac --release 21 -encoding UTF-8 -d . Contenedor.java DerivadaInterna.java SuperCalificado.java
// Ejecutar: java SuperCalificado
// Comandos desde la carpeta de la unidad.

class DerivadaInterna extends Contenedor.Interna {
    DerivadaInterna(Contenedor exterior) { exterior.super(); }
}

// Salida esperada:
// 5
