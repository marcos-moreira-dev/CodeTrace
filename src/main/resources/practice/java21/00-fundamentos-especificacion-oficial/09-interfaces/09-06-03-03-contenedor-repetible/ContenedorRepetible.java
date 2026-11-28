// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.6.3; ejemplo 9.6.3-3
// Compilar: javac --release 21 -encoding UTF-8 -d . Marca.java Marcas.java Grupos.java ContenedorRepetible.java
// Ejecutar: java ContenedorRepetible
// Comandos desde la carpeta de la unidad.

@Marcas({@Marca(1)}) @Marcas({@Marca(2)})
public class ContenedorRepetible {
    public static void main(String[] args) { System.out.println("válido"); }
}

// Salida esperada:
// válido
