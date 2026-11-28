// Tema:
// new crea una instancia y ejecuta su constructor. La inicialización de campos estáticos, los
// inicializadores de arreglo y la reflexión también pueden producir objetos; cada creación
// genera una instancia distinta.
// Compilar: javac --release 21 -encoding UTF-8 -d . CreacionObjetos.java Ubicacion.java
// Ejecutar: java CreacionObjetos
// Comandos desde la carpeta de la unidad.

public class CreacionObjetos {
    public static void main(String[] args) throws ReflectiveOperationException {
        Ubicacion a = Ubicacion.class.getDeclaredConstructor().newInstance();
        Ubicacion[] b = { new Ubicacion(2), new Ubicacion(4) };
        String[] letras = new String[2];
        letras[0] = "a";
        letras[1] = "b";
        System.out.println(Ubicacion.ORIGEN);
        System.out.println(a);
        System.out.println(b[0] + " " + b[1]);
        System.out.println(letras[0] + letras[1]);
    }
}

// Salida esperada:
// [0]
// [1]
// [2] [4]
// ab
