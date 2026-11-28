// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.12.5; ejemplo 4.12.5-1
// Compilar: javac --release 21 -encoding UTF-8 -d . ValoresIniciales.java Registro.java
// Ejecutar: java ValoresIniciales
// Tema JLS:
// Los campos de clase, los de instancia y los elementos de arreglos reciben valores
// predeterminados. Los números parten de cero, boolean de false y las referencias de null.
// Las variables locales deben asignarse antes de leerse.
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
