// Tema:
// Dos interfaces pueden declarar métodos abstractos con firmas y retornos compatibles. Una
// implementación concreta satisface ambos contratos a la vez.
// Compilar: javac --release 21 -encoding UTF-8 -d . MedidaA.java MedidaB.java MedidaComun.java HerenciaMetodos.java
// Ejecutar: java HerenciaMetodos
// Comandos desde la carpeta de la unidad.

class HerenciaMetodos extends MedidaComun {
    public int medir() { return 5; }
    public static void main(String[] args) { System.out.println(new HerenciaMetodos().medir()); }
}

// Salida esperada:
// 5
