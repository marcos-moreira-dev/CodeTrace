# Arquitectura de CodeTrace

## Stack

Eclipse Temurin 21, JavaFX 21.0.13, Maven y JUnit 5. Paquete raíz `com.mmdev.codetrace`.

## Paquetes y responsabilidades

| Paquete | Responsabilidad |
|---|---|
| `app` | Inicio JavaFX, ventana principal, coordinación, preferencias |
| `reference` | Lectura de fuente, SHA-256, catálogo y progreso global |
| `workspace` | Árbol de carpetas y archivos virtuales, sin escritura física |
| `practice` | Ranuras de escritura Unicode, comparación y avance no bloqueante |
| `progress` | Archivo binario versionado `.ctrace` y reemplazo seguro |
| `ui` | Árbol combinado, superficie visual de transcripción y preferencias |

El núcleo de `workspace`, `practice`, `progress` y `reference` no depende de JavaFX.

## Implementación actual

`VirtualWorkspace` distingue referencia real y reconstrucción en memoria. El usuario crea los elementos virtuales escribiendo sus nombres desde el explorador. `TypingSession` es una transcripción de ranuras fijas: no introduce automáticamente llaves ni modifica la referencia. Los aciertos se cuentan una sola vez según el estado actual; las omisiones no suman caracteres mecanografiados.

`SessionArchive` guarda un encabezado y una versión propia, la estructura virtual, el contenido introducido, las posiciones del cursor y los hashes de las referencias practicadas. Sustituye el archivo final mediante un temporal, usando renombrado atómico cuando está disponible.

`ReferenceCatalogue` analiza en un hilo secundario y calcula SHA-256 de archivos regulares. Las carpetas generadas conocidas (como `.git`, `target` o `node_modules`) se excluyen de la exploración de progreso por defecto. El progreso global se calcula sobre las unidades de texto practicables; se considera cero lo escrito contra una versión de referencia que haya cambiado hasta resolver ese conflicto.

El estilo común JavaFX utiliza fondo claro, azul acero, gris y verde, bordes rectangulares y editor con tipografía ajustable. Se mantienen los paneles auxiliares separados de la superficie de escritura.

## Verificación

- `mvn clean verify`: compilación y pruebas del núcleo con Java 21.
- GitHub Actions: Eclipse Temurin 21 y arranque gráfico del shell JavaFX con pantalla virtual Xvfb.
- El arranque automatizado **no equivale** a validar la ergonomía ni la interacción completa en Windows 11; queda pendiente una prueba de aceptación visual y de teclado latinoamericano.

## Pendiente

Selección/deshacer avanzados del editor, recuperación guiada de cambios de referencia y auditoría visual/accesibilidad. Markdown, comandos externos e iconografía ya disponen de implementaciones que necesitan aceptación en Windows. No se implementará un IDE completo ni un asistente de programación.

## Consola / comandos
`commands/ProjectCommandStore` persiste configuraciones binarias versionadas
por ruta del proyecto fuera de la referencia. `CommandRunner` ejecuta un
proceso explícitamente solicitado en un hilo daemon, informa salida acotada
y valida que el directorio de trabajo permanezca dentro de la carpeta raíz.
`ui/CommandSettingsPane` gestiona los comandos desde Preferencias;
`ui/CommandConsolePane` muestra salida y ofrece ejecución/detención.
Ningún comando se ejecuta durante la apertura de archivos o sesiones.

## Modalidad por proyecto y estado de reconstrucción
`practice/PracticeMode` define la modalidad manual o directa. Es independiente
de Turbo, de la inclusión de archivos en el porcentaje y de la sesión de
mecanografía. Se recuerda en Preferences usando un identificador SHA-256 de la
raíz del proyecto y también se guarda en las sesiones `.ctrace` v2; el
lector es retrocompatible con v1.

`practice/ReconstructionStatus` calcula estados sin modificar referencias:
PENDING → CREATED → COMPLETED. Un archivo exige todos sus grafemas correctos
y SHA-256 vigente. La carpeta se completa cuando todos los archivos de texto
incluidos por el filtro están completos. La aplicación actualiza los estados
al crear estructura, cambiar filtros o cruzar el umbral de finalización, no
por cada pulsación indiscriminadamente.

## Elegibilidad y resultado del ejercicio
`reference/PracticeEligibility` determina si el tipo del archivo tiene un
adaptador de lenguaje soportado. `ReferenceTreeIndex` calcula fuera del
hilo FX los archivos practicables y sus antecesores; los menús contextuales
se muestran solo si existen acciones posibles. `TypingSession.finishedExercise`
separa el fin de ejercicio del cómputo de aciertos: acepta omisiones
intencionales y no exige un Enter tras el último salto de línea.
`ReconstructionStatus` distingue referencias no practicables, pendientes,
creadas y ejercicios finalizados. Los cambios de filtros afectan a los
contadores y estados recursivos, no a la elegibilidad del código.

## Previsualización multimedia y distribución

`MarkdownRenderer` trabaja con texto de referencia, reconoce Markdown y HTML de imagen restringido, y convierte imágenes locales válidas en URI de datos con límites de tamaño. Solo admite archivos dentro de la raíz real resuelta del proyecto; usa CSP en WebView y no habilita JavaScript ni imágenes externas sin consentimiento.

`ReferenceMediaPane` distingue imágenes, SVG, audio y vídeo sin crear entradas virtuales ni sumar estadísticas. La ruta WebP/SVG utiliza WebView; PNG/JPEG/GIF/BMP emplean JavaFX Image. `MediaTranscoder` ofrece conversión local opcional a MP3/MP4 mediante FFmpeg: proceso separado y cancelable, directorio temporal del sistema, límites de entrada/salida y limpieza al cerrar pestañas. No invoca shell ni altera la referencia. El códec concreto depende del FFmpeg instalado o incorporado legalmente.

`MarkdownPracticePane.dispose()` cancela la preparación de HTML y vacía WebView al cerrar pestañas o cambiar el proyecto. `CodeTraceApp.clearOpenTabs()` debe liberar reproductores y vistas Markdown de forma simétrica.

`scripts/package-windows.ps1` utiliza jpackage con Temurin 21, dependencias de Maven y un runtime incluido para generar el ZIP portable. FFmpeg no se distribuye por defecto. El flujo de CI empaqueta Windows y conserva el ZIP como artefacto. La prueba final de UX y códecs debe seguir la matriz de [QA-WINDOWS](QA-WINDOWS.md).

## Refactorización de navegación y componentes de código (iteración posterior)

- `ui/DocumentTabs` mantiene la identidad de pestañas y la liberación de vistas de medios/Markdown; `CodeTraceApp` coordina los casos de uso y delega la administración del ciclo de vida de pestañas.
- `ui/CodeLineGutter` proporciona una numeración 1-based uniforme, integrada en las celdas virtualizadas tanto de `TypingEditor` como de `ReferenceTextPane`. Los índices internos de `TypingSession` no cambian.
- Buscar coloca Ámbito y sus opciones en una fila adaptable y navega al primer clic en resultados (sin modificar la transcripción).
- Pendiente de aceptación visual Windows: scroll a coincidencias recién abiertas, foco, escalado DPI y pruebas de regresión de cierre con medios activos.

## Índice de hijos del explorador

`workspace/VirtualChildrenIndex` es una instantánea inmutable que agrupa
archivos y carpetas virtuales por padre inmediato. `ReferenceTree` la
reconstruye al actualizar su modelo y consulta hijos directos durante la
expansión perezosa, evitando recorrer el workspace completo por cada carpeta.
La fuente de verdad sigue siendo `VirtualWorkspace`; la caché no altera
la lógica de reconstrucción ni el formato de sesiones.

## Expansión recursiva del árbol

`ui/BatchedTreeExpander` concentra el temporizador JavaFX, la cancelación y
los límites de trabajo para expandir carpetas de forma recursiva sin congelar
la interfaz. `ReferenceTree` conserva la semántica de colapso, la selección,
los nodos y los índices. Cambiar de referencia o iniciar otra expansión
cancela la operación anterior.

## Contrato de coordenadas de búsqueda

`reference/SourceRange` representa una coincidencia con línea y columna
**1-based**, tal como los resultados de `ProjectTextSearch`. `Hit.range()`
convierte la coincidencia en un valor validado. `TypingEditor` adapta el
rango a sus índices internos 0-based, mientras `ReferenceTextPane` usa
líneas visibles 1-based. `CodeTraceApp` no tiene que conocer esa diferencia.
Se conservan los métodos primitivos anteriores por compatibilidad de pruebas.

## Estado de sesiones y cálculo de progreso (extracción incremental)

`progress/PracticeSessionPaths` encapsula el traslado y eliminación de
transcripciones y huellas por subárbol. El criterio de pertenencia distingue
`src/` de `src2/`, y siempre devuelve instantáneas de los mapas sin tocar
archivos de referencia ni el formato binario `.ctrace`.

`reference/ProgressSummary` concentra el cálculo puro de unidades esperadas,
caracteres correctos y archivos incluidos (con filtros y exclusiones).
`CodeTraceApp` conserva solo la presentación del resultado en JavaFX.

## Índice de posición en transcripción

`reference/SourceLineIndex` conserva las posiciones finales de las líneas
inmutables y permite localizar en tiempo logarítmico la línea del cursor.
`TypingEditor` deja de recorrer todas las líneas en cada pulsación. Los
saltos de línea pertenecen a su fila anterior, como en el comportamiento
preexistente; no se modifican las ranuras Unicode de `TypingSession`.
