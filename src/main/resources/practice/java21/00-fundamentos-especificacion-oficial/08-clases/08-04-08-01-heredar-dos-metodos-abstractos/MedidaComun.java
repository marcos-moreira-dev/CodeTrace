// Tema:
// Dos interfaces pueden declarar métodos abstractos con firmas y retornos compatibles. Una
// implementación concreta satisface ambos contratos a la vez.
// Compilar: javac --release 21 -encoding UTF-8 -d . MedidaA.java MedidaB.java MedidaComun.java HerenciaMetodos.java
// Ejecutar: java HerenciaMetodos
// Comandos desde la carpeta de la unidad.

abstract class MedidaComun implements MedidaA, MedidaB {}

// Salida esperada:
// 5
