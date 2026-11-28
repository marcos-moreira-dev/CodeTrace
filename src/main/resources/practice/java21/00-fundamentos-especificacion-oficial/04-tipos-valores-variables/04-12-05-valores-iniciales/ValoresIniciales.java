// Tema:
// Los campos y componentes de arreglo reciben valores predeterminados: cero, false o null
// según su tipo. Las variables locales deben asignarse antes de leerse.
// Compilar: javac --release 21 -encoding UTF-8 -d . ValoresIniciales.java Registro.java
// Ejecutar: java ValoresIniciales
// Comandos desde la carpeta de la unidad.

public class ValoresIniciales {
    public static void main(String[] args) {
        Registro r = new Registro();
        System.out.println(Registro.total);
        System.out.println(r.numero);
        System.out.println(r.activo);
        System.out.println(r.siguiente);
    }
}

// Salida esperada:
// 0
// 0
// false
// null
