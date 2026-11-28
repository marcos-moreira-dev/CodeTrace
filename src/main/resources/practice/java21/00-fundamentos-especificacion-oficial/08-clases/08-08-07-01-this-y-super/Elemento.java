// Tema:
// this(...) delega en otro constructor de la misma clase; super(...) inicializa la parte de la
// superclase. La cadena debe llegar a un constructor de superclase válido.
// Compilar: javac --release 21 -encoding UTF-8 -d . Elemento.java ElementoMarcado.java ConstructoresEncadenados.java
// Ejecutar: java ConstructoresEncadenados
// Comandos desde la carpeta de la unidad.

class Elemento {
    int numero;
    Elemento(int numero) { this.numero = numero; }
}

// Salida esperada:
// 5 normal
