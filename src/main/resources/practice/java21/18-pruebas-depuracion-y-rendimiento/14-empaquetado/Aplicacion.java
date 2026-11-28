// Tema:
// Un JAR ejecutable lleva Main-Class en el manifiesto. El JDK puede empaquetar una aplicación con jpackage, pero generar instaladores depende del sistema y sus herramientas; no se exige hacerlo en esta práctica.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -d build Aplicacion.java
// Ejecutar: java -jar dist/aplicacion.jar
// Crear carpeta dist; jar --create --file dist/aplicacion.jar --main-class Aplicacion -C build . ; opcional: jpackage --type app-image --name Demo --input dist --main-jar aplicacion.jar --dest paquete (imagen local, sin instalador).

public class Aplicacion {
    public static void main(String[] args) throws Exception {
        System.out.println("Aplicacion empaquetada");
    }
}

// Salida esperada:
// Aplicacion empaquetada
