// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.6.1; ejemplo 9.6.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Ficha.java AnotacionDeclarada.java
// Ejecutar: java AnotacionDeclarada
// Comandos desde la carpeta de la unidad.

@Ficha(codigo = 3, descripcion = "prueba", autor = "equipo")
public class AnotacionDeclarada {
    public static void main(String[] args) { System.out.println("Anotación declarada"); }
}

// Salida esperada:
// Anotación declarada
