// Tema:
// Un JAR empaqueta clases y recursos. La biblioteca se compila y empaqueta por separado; el cliente usa su API pública por classpath. No requiere Maven ni Gradle.
// Modalidad: ejecución real con el JDK.
// Compilar: javac --release 21 -d build/lib lib/Operaciones.java
// Ejecutar: java -cp "biblioteca.jar:build/app" app.Cliente (Windows: separar con ;)
// Luego: jar --create --file biblioteca.jar -C build/lib . ; javac --release 21 -cp biblioteca.jar -d build/app app/Cliente.java (ejecutar por separado).

package app;

import lib.Operaciones;

public class Cliente {
    public static void main(String[] args) {
        System.out.println(Operaciones.sumar(7, 5));
    }
}

// Salida esperada:
// 12
