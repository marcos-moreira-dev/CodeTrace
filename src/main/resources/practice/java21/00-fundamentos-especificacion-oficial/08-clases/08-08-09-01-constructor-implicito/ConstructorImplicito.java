// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.8.9; ejemplo 8.8.9-1
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
