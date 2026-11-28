// Tema:
// ServiceLoader descubre el proveedor registrado por el sistema de módulos, en lugar de
// construir la implementación por nombre.
// Compilar: javac --release 21 --module-source-path . -d salida demo.api/module-info.java demo.api/api/Saludo.java demo.proveedor/module-info.java demo.proveedor/proveedor/SaludoSimple.java demo.cliente/module-info.java demo.cliente/cliente/Consumidor.java
// Ejecutar: java --module-path salida -m demo.cliente/cliente.Consumidor
// Comandos desde la carpeta de la unidad.

package cliente;
public class Consumidor {
    public static void main(String[] args) {
        api.Saludo saludo = java.util.ServiceLoader.load(api.Saludo.class)
                .findFirst().orElseThrow();
        System.out.println(saludo.mensaje());
    }
}

// Salida esperada:
// hola
