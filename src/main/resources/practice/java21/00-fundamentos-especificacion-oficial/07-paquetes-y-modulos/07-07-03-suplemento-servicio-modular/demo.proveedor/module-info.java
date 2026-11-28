// Tema:
// provides registra una implementación del servicio. El paquete de la implementación no
// necesita exportarse.
// Compilar: javac --release 21 --module-source-path . -d salida demo.api/module-info.java demo.api/api/Saludo.java demo.proveedor/module-info.java demo.proveedor/proveedor/SaludoSimple.java demo.cliente/module-info.java demo.cliente/cliente/Consumidor.java
// Ejecutar: java --module-path salida -m demo.cliente/cliente.Consumidor
// Comandos desde la carpeta de la unidad.

module demo.proveedor { requires demo.api; provides api.Saludo with proveedor.SaludoSimple; }

// Salida esperada:
// hola
