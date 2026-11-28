// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §5.2; ejemplo 5.2-2
// Compilar: javac --release 21 -encoding UTF-8 -d . AsignacionReferencias.java
// Ejecutar: java AsignacionReferencias
// Comandos desde la carpeta de la unidad.

public class AsignacionReferencias {
    public static void main(String[] args) {
        String texto = "Java";
        Object objeto = texto;
        CharSequence secuencia = texto;
        System.out.println(objeto == secuencia);
        System.out.println(secuencia.length());
    }
}

// Salida esperada:
// true
// 4
