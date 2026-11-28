// Tema:
// El accesor devuelve la lista referenciada. Añadir un elemento modifica esa lista, aunque
// el campo del record sea final.
// Compilar: javac --release 21 -encoding UTF-8 -d . Grupo.java RecordSuperficial.java
// Ejecutar: java RecordSuperficial
// Comandos desde la carpeta de la unidad.

public class RecordSuperficial {
    public static void main(String[] args) {
        var nombres = new java.util.ArrayList<String>();
        Grupo g = new Grupo(nombres);
        nombres.add("Ana");
        g.nombres().add("Luis");
        System.out.println(g.nombres());
    }
}

// Salida esperada:
// [Ana, Luis]
