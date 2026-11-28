# Plan de cierre de Java 21 y ampliación curricular

## Puerta de cierre de fundamentos (00–05)

- Inventario: 233 identificadores numerados, todos con adaptación. No confundir esto con cobertura de todos los fragmentos sin numeración.
- Comparación semántica: comprobar cada capítulo, distinguir variantes, ejemplos negativos, modelos no ejecutables y demostraciones ya existentes. Registrar equivalencias y omisiones con justificación en documentación externa.
- Pedagogía: comentarios en español sobre el código, sin referencias bibliográficas; un concepto por unidad, sin infraestructura artificial; un tipo superior por archivo.
- Pruebas: compilación y ejecución aisladas, diagnósticos esperados, catálogo y rutas, pruebas de la aplicación y empaquetado Windows en el mismo commit. Conservar cachés y probar reapertura de sesiones anteriores. No publicar el instalador hasta el cierre conjunto.
- Los capítulos 2 y 19 son gramática: documentar los conceptos, no forzar programas Java artificiales. La especificación del lenguaje no reemplaza la API del JDK ni la especificación de la JVM.

## Ampliación avanzada (propuesta; no implementada)

| Área prevista | Prioridad de ejecución | Enfoque |
| --- | --- | --- |
| 06 Matemáticas computacionales | 1 | lógica, conjuntos, matrices, precisión |
| 07 Representación binaria, arquitectura y memoria | 1 | representación real; máquina, caché y memoria virtual simuladas |
| 08 Sistemas de archivos y entrada/salida | 1 | rutas, streams, canales, temporales |
| 09 Sistemas operativos y procesos | 1 | procesos hijos propios, pipes; planificación y señales POSIX modeladas |
| 10 Concurrencia y paralelismo | 1 | primitivas, productor-consumidor, lectores-escritores, problemas clásicos |
| 11 Redes y protocolos de comunicación | 1 | loopback, TCP, UDP, tramas, direccionamiento y enrutamiento modelado |
| 12 Persistencia y gestión de datos | 2 | almacenamiento, índices, transacciones simuladas; JDBC sin driver obligatorio |
| 13 Criptografía y seguridad | 2 | primitivas estándar, integridad, autenticación |
| 14 Hardware y dispositivos | 2 | modelos de protocolos seriales, sensores e interrupciones, sin dispositivo obligatorio |
| 15 Gráficos y multimedia | 2 | imágenes, transformaciones, audio |
| 16 Compiladores, intérpretes y enlazado | 2 | herramientas JDK, clases, tokens, AST, máquina simple |
| 17 Sistemas distribuidos | 3 | modelos de relojes, replicación, fallos |
| 18 Pruebas, depuración y rendimiento | transversal | invariantes, diagnóstico, JDK tools; sin dependencias |
| 19 IA fundamental | 3 | búsqueda, aprendizaje elemental, no frameworks |

El árbol detallado de las 219 unidades se mantiene como propuesta de trabajo; contrastar cada bloque con fuentes y eliminar duplicados antes de implementarlo. Cada unidad tendrá una modalidad declarada: ejecución real, simulación, dependiente de plataforma o documental. Todo ejemplo debe poder estudiarse sin hardware, red externa o frameworks. Las pruebas deben ser acotadas y limpiar temporales, sockets y procesos.

Fuentes: materiales de CCPG1051 (ESPOL), unidades 1–6; CS2023; Java SE 21 API; OSTEP; Nand2Tetris. No incorporar PDFs docentes al repositorio. La atribución y la trazabilidad permanecen fuera del código.

## Orden general

1. Cerrar fundamentos Java y congelar su alcance.
2. Java avanzado: primero el núcleo de Programación de Sistemas; después las demás áreas.
3. C: preparar biblioteca pedagógica de programación de sistemas a partir de las seis unidades docentes.
4. TypeScript: integrar el material ya importado y sustituir sus fundamentos antiguos tras validación.
5. Publicar instalador definitivo.

Este plan no certifica la cobertura exhaustiva de la JLS ni implica que las carpetas avanzadas existan.
