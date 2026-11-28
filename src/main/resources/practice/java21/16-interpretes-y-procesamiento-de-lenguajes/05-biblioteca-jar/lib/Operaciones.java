// Tema:
// Un JAR empaqueta clases y recursos. La biblioteca se compila y empaqueta por separado; el cliente usa su API pública por classpath. No requiere Maven ni Gradle.
// Modalidad: ejecución real con el JDK.
// Compilar: javac --release 21 -d build/lib lib/Operaciones.java
// Ejecutar: java -cp "biblioteca.jar:build/app" app.Cliente (Windows: separar con ;)
// Luego: jar --create --file biblioteca.jar -C build/lib . ; javac --release 21 -cp biblioteca.jar -d build/app app/Cliente.java (ejecutar por separado).

package lib;

public class Operaciones {
    public static int sumar(int a, int b) { return a + b; }
}

// Salida esperada:
// 12
