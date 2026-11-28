// Tema:
// transient excluye un campo de la serialización predeterminada de objetos. Al deserializar,
// ese campo recibe su valor predeterminado si no se restaura explícitamente.
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
