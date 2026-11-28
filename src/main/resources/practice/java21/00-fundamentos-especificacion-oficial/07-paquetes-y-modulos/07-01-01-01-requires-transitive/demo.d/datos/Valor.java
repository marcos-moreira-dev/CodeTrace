// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §7.1.1; ejemplo 7.1.1-1
// Compilar: javac --release 21 --module-source-path . -d salida demo.a/module-info.java demo.a/cliente/Main.java demo.b/module-info.java demo.c/module-info.java demo.d/module-info.java demo.d/datos/Valor.java
// Ejecutar: java --module-path salida -m demo.a/cliente.Main
// Comandos desde la carpeta de la unidad.

package datos;
public class Valor { public static final int NUMERO = 9; }

// Salida esperada:
// 9
