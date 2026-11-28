// Tema:
// uses declara que el módulo consume el servicio. No es necesario que dependa directamente
// del módulo proveedor.
// Compilar: javac --release 21 --module-source-path . -d salida demo.api/module-info.java demo.api/api/Saludo.java demo.proveedor/module-info.java demo.proveedor/proveedor/SaludoSimple.java demo.cliente/module-info.java demo.cliente/cliente/Consumidor.java
// Ejecutar: java --module-path salida -m demo.cliente/cliente.Consumidor
// Comandos desde la carpeta de la unidad.

module demo.cliente { requires demo.api; uses api.Saludo; }

// Salida esperada:
// hola
