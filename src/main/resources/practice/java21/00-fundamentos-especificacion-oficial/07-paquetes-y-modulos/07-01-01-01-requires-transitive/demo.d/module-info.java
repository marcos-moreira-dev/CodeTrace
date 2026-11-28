// Tema:
// requires establece una dependencia entre módulos. requires transitive permite que los
// consumidores del módulo también lean la dependencia transitiva; exports determina los
// paquetes accesibles.
// Compilar: javac --release 21 --module-source-path . -d salida demo.a/module-info.java demo.a/cliente/Main.java demo.b/module-info.java demo.c/module-info.java demo.d/module-info.java demo.d/datos/Valor.java
// Ejecutar: java --module-path salida -m demo.a/cliente.Main
// Comandos desde la carpeta de la unidad.

module demo.d { exports datos; }

// Salida esperada:
// 9
