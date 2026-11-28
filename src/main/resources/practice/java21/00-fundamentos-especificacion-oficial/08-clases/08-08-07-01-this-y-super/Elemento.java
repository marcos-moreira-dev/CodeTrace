// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.8.7; ejemplo 8.8.7-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Elemento.java ElementoMarcado.java ConstructoresEncadenados.java
// Ejecutar: java ConstructoresEncadenados
// Comandos desde la carpeta de la unidad.

class Elemento {
    int numero;
    Elemento(int numero) { this.numero = numero; }
}

// Salida esperada:
// 5 normal
