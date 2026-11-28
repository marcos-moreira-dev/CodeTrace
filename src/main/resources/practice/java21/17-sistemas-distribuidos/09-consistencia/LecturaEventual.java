// Tema:
// Consistencia eventual: si dejan de ocurrir escrituras y todas las actualizaciones se propagan, las réplicas convergen. El modelo muestra una lectura desactualizada antes de entregar la escritura pendiente.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 LecturaEventual.java
// Ejecutar: java LecturaEventual

public class LecturaEventual {
    public static void main(String[] args) {
        int[] replicas = {10, 10, 10};
        replicas[0] = 20;
        System.out.println("Lectura remota: " + replicas[2]);
        for (int i = 1; i < replicas.length; i++) replicas[i] = replicas[0];
        System.out.println("Convergencia: " + (replicas[0] == replicas[1] && replicas[1] == replicas[2]));
    }
}

// Salida esperada:
// Lectura remota: 10
// Convergencia: true
