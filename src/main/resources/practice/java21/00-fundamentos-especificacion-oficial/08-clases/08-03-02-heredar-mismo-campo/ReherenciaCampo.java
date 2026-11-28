// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.3; ejemplo 8.3-2
// Compilar: javac --release 21 -encoding UTF-8 -d . RaizCampo.java RamaCampo.java BaseCampo.java ReherenciaCampo.java
// Ejecutar: java ReherenciaCampo
// Comandos desde la carpeta de la unidad.

public class ReherenciaCampo extends BaseCampo implements RamaCampo {
    public static void main(String[] args) { System.out.println(VALOR); }
}

// Salida esperada:
// 9
