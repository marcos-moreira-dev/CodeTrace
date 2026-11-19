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

## Indexación asíncrona cancelable del proyecto

`app/ReferenceCatalogueScanner` administra la tarea JavaFX de catálogo del
proyecto activo: un escaneo nuevo cancela el anterior y descarta callbacks
obsoletos. El cambio de referencia o restauración de una sesión cancela la
indexación previa. `ReferenceCatalogue.scan` consulta la interrupción del hilo
entre entradas del árbol y termina el recorrido cuando se solicita cancelación.
Los archivos del proyecto continúan siendo de solo lectura.

## Activación unificada de proyectos y sesiones

`app/ProjectActivation` es un estado de transición inmutable para abrir una
referencia desde cero o retomar una sesión `.ctrace`, con huellas y progresos
conservados. `CodeTraceApp.applyProjectState` realiza en un solo lugar la
invalidación de tareas previas, limpieza de pestañas y refresco del explorador,
consola, búsqueda y métricas. Al restaurar se indexa la referencia una sola vez.

`app/ReferenceChangeDetector` compara las huellas guardadas de forma
independiente de JavaFX; no escribe en los archivos originales. Reubicar una
carpeta de referencia al abrir una sesión conserva el contenido virtual.

## Ciclo de vida de operaciones en segundo plano

`app/LatestTaskRunner<T>` concentra cancelación, generación de solicitudes
y descarte de resultados antiguos para trabajos `Task<T>` de JavaFX.
`ReferenceCatalogueScanner` delega ahí sus escaneos; la verificación de
huellas de una sesión también se ejecuta ahora fuera del hilo gráfico.
Al cambiar de proyecto o cerrar CodeTrace se cancelan ambas operaciones.
Los eventos de navegación de Buscar comprueban que la pestaña aún pertenezca
al proyecto activo antes de modificar la selección o el resaltado.

## Verificación acotada a la referencia

`ReferenceChangeDetector` normaliza cada ruta relativa antes de comprobar
su huella y rechaza cualquier ruta que escape la carpeta seleccionada. No
sigue enlaces simbólicos al verificar archivos. Estas precauciones protegen
también a futuros consumidores del detector, aunque `SessionArchive` ya
valide las rutas al leer el archivo `.ctrace`.

## Cancelación de indexación y búsqueda del explorador

`ReferenceTree` ahora conserva los `Task` activos y cancela el escaneo
anterior al cambiar de referencia o actualizar el árbol, además de cancelar
el filtro anterior al cambiar la consulta. El cierre de CodeTrace llama a
`cancelPendingWork()` para no mantener indexación y filtros después de
salir. `ReferenceTreeIndex`, `ReferenceCatalogue` y `TreeNameFilter`
rechazan cancelaciones por interrupción en lugar de devolver resultados
parciales aparentemente válidos. Sus resultados se siguen validando por
generación y por identidad de tarea.

## Reconstrucción virtual por lotes

`workspace/VirtualTreeRecreator` implementa el avance determinista de
autocreación sin JavaFX: procesa como máximo 10 000 nodos ya ordenados,
cuenta creaciones y omisiones, y no escribe en el disco de referencia.
`CodeTraceApp` conserva solo la planificación `Platform.runLater`
y las comprobaciones de generación al cambiar de proyecto.

## Límites explícitos de búsqueda

`ProjectTextSearch.searchWithReport` distingue coincidencias encontradas,
límite de 1500 resultados y líneas omitidas por superar 4096 caracteres.
`ProjectSearchWindow` presenta esos límites, de modo que el usuario no
confunda una búsqueda parcial con una búsqueda exhaustiva. Se omiten carpetas
generadas, incluidas las salidas de diagnóstico en `reports`.
La API histórica `search` continúa devolviendo una lista para consumidores
existentes.

## Política única de carpetas generadas

`reference/ReferenceScanRules` centraliza las carpetas omitidas por los
analizadores y la autocreación: `.git`, `.idea`, `.local`, `target`,
`node_modules`, `build`, `dist`, `reports`, `__pycache__` y `.venv`.
El explorador de solo lectura sigue mostrando esos archivos para consulta;
no se consideran ejercicios, progreso ni dependencias relevantes por defecto.
Evita que cada analizador mantenga un conjunto divergente de exclusiones.

## Empaquetado sin nombres de versión duplicados

`scripts/package-windows.ps1` obtiene `artifactId` y `version` desde
`pom.xml` para localizar el JAR y suministrarlo a `jpackage`, en vez
de codificar el nombre `codetrace-0.3.0-SNAPSHOT.jar`. No se ha cambiado
la versión del producto: continúa en desarrollo hasta decidir licencia,
avisos de terceros, aceptación Windows y release.

## Política del menú contextual del explorador

`reference/ExplorerMenuPolicy` calcula de forma pura las acciones
permitidas por el índice real y el espacio virtual: creación de nodos,
expansión de subcarpetas y alcance de progreso. `ReferenceTree` se
limita a construir los `MenuItem` JavaFX y delegar acciones. Los tests
comprueban permisos antes y después de crear una carpeta y un archivo
virtual, así como archivos no practicables.

## Presupuesto de evaluación de expresiones regulares

El motor `ProjectTextSearch` mantiene los patrones Java `Pattern`, pero
en búsquedas regex usa un `CharSequence` vigilado, con comprobación de
cancelación y presupuesto de unos 250 ms por línea (además del límite
previo de 4096 caracteres por línea). Los patrones con retrocesos
patológicos se rechazan con un mensaje legible, en lugar de bloquear
indefinidamente un hilo de búsqueda. Las búsquedas literales no necesitan
esta capa. La interrupción no borra resultados persistidos ni referencias.

## Lectura asíncrona de sesiones y huellas interrumpibles

La lectura `.ctrace` se realiza mediante `LatestTaskRunner<SessionArchive.State>`
fuera del hilo de JavaFX. Cambiar de proyecto, iniciar otra restauración o cerrar
la aplicación invalida la solicitud anterior; ninguna restauración aplica su
estado hasta haberse leído por completo. Antes de aplicar, si el usuario
modificó su sesión durante la lectura, se vuelve a consultar si desea guardarla.
Si cancela el selector de una referencia ausente, la sesión actual permanece
intacta. `ReferenceFingerprint.sha256` comprueba interrupciones entre bloques
de lectura para evitar trabajos costosos que sobrevivan a su cancelación.

## Cancelación cooperativa del lector `.ctrace`

`SessionArchive.load` comprueba la interrupción antes y durante la
reconstrucción de elementos virtuales, sesiones, ranuras de caracteres,
huellas y posiciones del grafo, y tras leer campos de texto grandes.
La cancelación produce `CancellationException` en vez de devolver un
estado parcialmente recuperado; el archivo original queda intacto.

## Integridad de preferencias de comandos

`ProjectCommandStore.load` exige consumir todo el documento binario
versionado `.ctcmd`; bytes sobrantes implican corrupción y se rechazan
con `IOException`. No se ejecuta ningún comando al cargar preferencias.

## Prueba integral de inmutabilidad de referencias

`integration/ReferenceIntegrityWorkflowTest` crea una referencia temporal y
compara las huellas SHA-256 de **todos** sus archivos antes y después de
indexar, recrear virtualmente, buscar texto, analizar dependencias y
guardar/restaurar una sesión `.ctrace` fuera del proyecto. Complementa las
pruebas aisladas y protege el contrato principal de CodeTrace: no escribir
sobre los originales durante el entrenamiento.
