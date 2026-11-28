// Tema:
// La interfaz define el servicio que el consumidor solicita sin conocer su implementación
// concreta.
// Compilar: javac --release 21 --module-source-path . -d salida demo.api/module-info.java demo.api/api/Saludo.java demo.proveedor/module-info.java demo.proveedor/proveedor/SaludoSimple.java demo.cliente/module-info.java demo.cliente/cliente/Consumidor.java
// Ejecutar: java --module-path salida -m demo.cliente/cliente.Consumidor
// Comandos desde la carpeta de la unidad.

package api;
public interface Saludo { String mensaje(); }

// Salida esperada:
// hola
