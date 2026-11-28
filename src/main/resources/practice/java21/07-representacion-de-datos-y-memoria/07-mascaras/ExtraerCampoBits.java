// Tema:
// Desplazar un campo hacia la derecha y aplicar una máscara elimina todos los bits ajenos a
// él.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 ExtraerCampoBits.java
// Ejecutar: java ExtraerCampoBits

public class ExtraerCampoBits {
    public static void main(String[] args) {
        int dato = 0b11010110;
        int campo = (dato >>> 2) & 0b111;
        System.out.println(campo);
    }
}

// Salida esperada:
// 5
