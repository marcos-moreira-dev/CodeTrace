// Tema:
// Dos instancias independientes pueden ser iguales por sus componentes. == compara
// identidad, equals compara valores y hashCode es consistente con equals.
// Compilar: javac --release 21 -encoding UTF-8 -d . Coordenada.java IgualdadRecord.java
// Ejecutar: java IgualdadRecord
// Comandos desde la carpeta de la unidad.

public class IgualdadRecord {
    public static void main(String[] args) {
        Coordenada a = new Coordenada(2, 3);
        Coordenada b = new Coordenada(2, 3);
        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println(a.hashCode() == b.hashCode());
        System.out.println(a);
    }
}

// Salida esperada:
// false
// true
// true
// Coordenada[x=2, y=3]
