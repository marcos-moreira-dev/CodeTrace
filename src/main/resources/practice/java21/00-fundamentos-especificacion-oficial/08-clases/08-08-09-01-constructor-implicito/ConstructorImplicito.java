// Tema:
// Si una clase no declara constructores, el compilador proporciona uno predeterminado con
// acceso apropiado. Este invoca el constructor sin argumentos de la superclase.
// Compilar: javac --release 21 -encoding UTF-8 -d . RectanguloImplicit.java ConstructorImplicito.java
// Ejecutar: java ConstructorImplicito
// Comandos desde la carpeta de la unidad.

public class ConstructorImplicito {
    public static void main(String[] args) throws ReflectiveOperationException {
        RectanguloImplicit r = new RectanguloImplicit();
        System.out.println(r.ancho);
        System.out.println(java.lang.reflect.Modifier.isPublic(RectanguloImplicit.class.getConstructor().getModifiers()));
    }
}

// Salida esperada:
// 0
// true
