// Tema:
// Un nombre calificado se interpreta por segmentos. Un segmento puede identificar un paquete,
// otro un tipo y los siguientes campos o expresiones.
// Compilar: javac --release 21 -encoding UTF-8 -d . biblioteca/Sonido.java cliente/Reclasificar.java
// Ejecutar: java cliente.Reclasificar
// Comandos desde la carpeta de la unidad.

package cliente;
public class Reclasificar {
    public static void main(String[] args) {
        // biblioteca: paquete; Sonido: tipo; NOTAS y length: expresiones.
        System.out.println(biblioteca.Sonido.NOTAS.length);
    }
}

// Salida esperada:
// 4
