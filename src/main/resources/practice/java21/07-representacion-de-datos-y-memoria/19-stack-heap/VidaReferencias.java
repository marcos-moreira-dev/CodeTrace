// Tema:
// Una referencia local puede dejar de existir al retornar un método, pero un objeto sigue
// accesible si otra referencia lo conserva. No se fuerza ni se predice el momento del GC.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 VidaReferencias.java
// Ejecutar: java VidaReferencias

public class VidaReferencias {
static int[] crear() {
        int[] local = {12};
        return local;
    }

    public static void main(String[] args) {
        int[] datos = crear();
        System.out.println(datos[0]);
        datos = null;
        System.out.println(datos == null);
    }
}

// Salida esperada:
// 12
// true
