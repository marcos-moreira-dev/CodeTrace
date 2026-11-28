// Tema:
// Una sobrescritura puede devolver un subtipo del retorno declarado por el método original. El
// cliente de la subclase puede aprovechar ese tipo más específico.
// Compilar: javac --release 21 -encoding UTF-8 -d . Generador.java GeneradorEntero.java RetornoCovariante.java
// Ejecutar: java RetornoCovariante
// Comandos desde la carpeta de la unidad.

public class RetornoCovariante {
    public static void main(String[] args) {
        Integer n = new GeneradorEntero().obtener();
        System.out.println(n);
    }
}

// Salida esperada:
// 7
