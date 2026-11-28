// Tema:
// Los argumentos del constructor corresponden a los componentes del record. Se leen con
// nombre() y edad(), no con setters.
// Compilar: javac --release 21 -encoding UTF-8 -d . Persona.java ComponentesRecord.java
// Ejecutar: java ComponentesRecord
// Comandos desde la carpeta de la unidad.

public class ComponentesRecord {
    public static void main(String[] args) {
        Persona p = new Persona("Ana", 21);
        System.out.println(p.nombre());
        System.out.println(p.edad());
    }
}

// Salida esperada:
// Ana
// 21
