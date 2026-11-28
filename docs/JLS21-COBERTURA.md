# Fundamentos — Especificación oficial (Java 21)

Primera entrega parcial. Ejemplos originales basados en los conceptos de la [JLS SE 21](https://docs.oracle.com/javase/specs/jls/se21/html/index.html); no son una publicación oficial de Oracle ni una reproducción completa del documento.

| Sección | Archivo | Estado |
| --- | --- | --- |
| 1.2 | ArgumentosJls.java | Implementado; pendiente CI |
| 3.10.1 | LiteralesEnterosJls.java | Implementado; pendiente CI |
| 3.10.2 | LiteralesFlotantesJls.java | Implementado; pendiente CI |
| 3.10.3 | LiteralesBooleanosJls.java | Implementado; pendiente CI |
| 3.10.4 | LiteralesCaracterJls.java | Implementado; pendiente CI |
| 3.10.5 | LiteralesCadenaJls.java | Implementado; pendiente CI |
| 3.10.6 | BloqueTextoJls.java | Implementado; pendiente CI |
| 3.10.7 | EscapesJls.java | Implementado; pendiente CI |
| 3.10.8 | LiteralNullJls.java | Implementado; pendiente CI |
| 4.2.2; ejemplo 4.2.2-1 | OperacionesEnterasJls.java | Implementado; pendiente CI |
| 5.1.2; ejemplo 5.1.2-1 | AmpliacionPrimitivaJls.java | Implementado; pendiente CI |
| 5.1.3; ejemplo 5.1.3-2 | ReduccionPrimitivaJls.java | Implementado; pendiente CI |

Pendientes: inventario exhaustivo de ejemplos y fragmentos de los 19 capítulos, casos negativos de compilación, ejemplos múltiples y módulos, migración del nombre de la biblioteca anterior y publicación del instalador. Los capítulos 1, 3, 4 y 5 solo tienen cobertura inicial; los restantes no se han implementado. Los ejemplos se verifican individualmente con `python3 scripts/verificar-jls21.py` y se integran en el verificador existente. No se incluye el PDF ni su código literal masivamente.
