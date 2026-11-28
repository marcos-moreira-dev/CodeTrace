// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §10.8; ejemplo 10.8-1
// Compilar: javac --release 21 -encoding UTF-8 -d . ObjetoClassArreglo.java
// Ejecutar: java ObjetoClassArreglo
// Comandos desde la carpeta de la unidad.

public class ObjetoClassArreglo {
    public static void main(String[] args) {
        int[] valores = new int[3];
        System.out.println(valores.getClass().getName());
        System.out.println(valores.getClass().getSuperclass().getName());
        for (Class<?> tipo : valores.getClass().getInterfaces()) {
            System.out.println(tipo.getName());
        }
    }
}

// Salida esperada:
// [I
// java.lang.Object
// java.lang.Cloneable
// java.io.Serializable
