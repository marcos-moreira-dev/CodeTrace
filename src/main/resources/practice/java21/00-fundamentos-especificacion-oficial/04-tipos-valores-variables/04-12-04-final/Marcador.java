// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.12.4; ejemplo 4.12.4-1
// Compilar: javac --release 21 -encoding UTF-8 -d . ReferenciaFinal.java Marcador.java
// Ejecutar: java ReferenciaFinal
// Tema JLS:
// Este objeto tiene estado mutable. Aunque una variable final apunte a él, su campo puntos
// sigue siendo modificable; la restricción final pertenece a la variable de referencia.
// Comandos desde la carpeta de la unidad.

class Marcador {
    int puntos;
}

// Salida esperada:
// 5
