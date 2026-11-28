// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.3.1.3; ejemplo 8.3.1.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . DatosPersistentes.java PersistenciaTransient.java
// Ejecutar: java PersistenciaTransient
// Comandos desde la carpeta de la unidad.

import java.io.Serializable;
class DatosPersistentes implements Serializable {
    int permanente = 8;
    transient int temporal = 7;
}

// Salida esperada:
// 8
// 0
