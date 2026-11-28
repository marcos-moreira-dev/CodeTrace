// Tema:
// this(...) delega en otro constructor de la misma clase; super(...) inicializa la parte de la
// superclase. La cadena debe llegar a un constructor de superclase válido.
// Compilar: javac --release 21 -encoding UTF-8 -d . Elemento.java ElementoMarcado.java ConstructoresEncadenados.java
// Ejecutar: java ConstructoresEncadenados
// Comandos desde la carpeta de la unidad.

class ElementoMarcado extends Elemento {
    String marca;
    ElementoMarcado(int numero) { this(numero, "normal"); }
    ElementoMarcado(int numero, String marca) {
        super(numero);
        this.marca = marca;
    }
}

// Salida esperada:
// 5 normal
