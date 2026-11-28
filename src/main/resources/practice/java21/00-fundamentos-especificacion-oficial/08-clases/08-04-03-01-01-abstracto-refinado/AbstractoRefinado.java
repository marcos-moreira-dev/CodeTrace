// Tema:
// Una declaración abstracta puede sobrescribir otra y refinar su tipo de retorno a un subtipo
// permitido. La clase concreta debe implementar el contrato resultante.
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseAbstracta.java Refinada.java AbstractoRefinado.java
// Ejecutar: java AbstractoRefinado
// Comandos desde la carpeta de la unidad.

public class AbstractoRefinado extends Refinada {
    Integer obtener() { return 5; }
    public static void main(String[] args) { System.out.println(new AbstractoRefinado().obtener()); }
}

// Salida esperada:
// 5
