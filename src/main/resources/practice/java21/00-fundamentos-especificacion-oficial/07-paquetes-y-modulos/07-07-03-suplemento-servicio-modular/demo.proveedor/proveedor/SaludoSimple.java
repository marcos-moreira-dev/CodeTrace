// Tema:
// Esta clase implementa el contrato del servicio y ofrece un constructor público sin
// argumentos para su carga.
// Compilar: javac --release 21 --module-source-path . -d salida demo.api/module-info.java demo.api/api/Saludo.java demo.proveedor/module-info.java demo.proveedor/proveedor/SaludoSimple.java demo.cliente/module-info.java demo.cliente/cliente/Consumidor.java
// Ejecutar: java --module-path salida -m demo.cliente/cliente.Consumidor
// Comandos desde la carpeta de la unidad.

package proveedor;
public class SaludoSimple implements api.Saludo {
    public String mensaje() { return "hola"; }
}

// Salida esperada:
// hola
