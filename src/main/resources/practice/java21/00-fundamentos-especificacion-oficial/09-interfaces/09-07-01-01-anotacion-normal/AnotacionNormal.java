// Tema:
// Una anotación normal proporciona pares nombre-valor. Los elementos sin default son
// obligatorios y los restantes pueden omitirse.
// Compilar: javac --release 21 -encoding UTF-8 -d . Revision.java AnotacionNormal.java
// Ejecutar: java AnotacionNormal
// Comandos desde la carpeta de la unidad.

@Revision(codigo = 5, descripcion = "prueba")
public class AnotacionNormal {
    public static void main(String[] args) {
        Revision r = AnotacionNormal.class.getAnnotation(Revision.class);
        System.out.println(r.codigo());
        System.out.println(r.descripcion());
        System.out.println(r.autor());
    }
}

// Salida esperada:
// 5
// prueba
// equipo
