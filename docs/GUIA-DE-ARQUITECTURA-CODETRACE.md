# CodeTrace por dentro: guía de arquitectura e ingeniería

Esta guía complementa [ARQUITECTURA.md](ARQUITECTURA.md) y el Javadoc del
código. Su intención es explicar **por qué** la aplicación se construyó
de este modo y qué compromisos de ingeniería contiene. No sustituye las
pruebas ni implica que CodeTrace sea un compilador o un IDE.

## 1. El dominio, antes que el framework

El usuario selecciona un **proyecto de referencia real**, estudia su
estructura y reconstruye otra estructura **virtual** mediante ejercicios de
transcripción. El original permanece de solo lectura y el avance se guarda en
un documento `.ctrace` distinto. Esa separación es el requisito central:
las bibliotecas de interfaz no deberían dictar cómo se representa el trabajo.

```text
Proyecto real (referencia) ──lectura──┐
                                     ├── índices/catálogo ───────┐
Proyecto reconstruido (VirtualWorkspace) ──estado en RAM─────────┤
                                                               │
TypingSession + ProgressAccumulator ──avance y métricas─────────┤
                                                               ▼
                  CodeTraceApp (coordinador JavaFX)
                                                               │
                  Explorer / Editor / Graph / Media / Console ◀┘

   SessionArchive (.ctrace) ⇄ estado de reconstrucción guardado
```

`CodeTraceApp` es la **raíz de composición** (*composition root*): crea
controles, conecta servicios y coordina transiciones. Que dependa de muchas
clases **no** convierte a cada una de ellas en una dependencia de dominio.
La dirección deseada es: presentación → casos de uso / coordinación → modelos
sin JavaFX. En una aplicación de escritorio monolítica y modular, una capa
separada solo se justifica si protege una responsabilidad real.

| Paquete | Pregunta que resuelve | Ejemplos |
| --- | --- | --- |
| `app` | ¿Qué ocurre al abrir, restaurar o cerrar un proyecto? | `CodeTraceApp`, `ProjectActivation`, `LatestTaskRunner` |
| `reference` | ¿Qué existe realmente y qué se puede analizar? | `ReferenceTreeIndex`, `ReferenceCatalogue`, `ProjectDependencyGraph` |
| `workspace` | ¿Qué ha reconstruido virtualmente el estudiante? | `VirtualWorkspace`, `VirtualChildrenIndex`, `VirtualTreeRecreator` |
| `practice` | ¿Qué caracteres se han transcrito y con qué resultado? | `TypingSession`, `ReconstructionStatus` |
| `progress` | ¿Cómo se guarda y recupera una sesión? | `SessionArchive`, `PracticeSessionPaths` |
| `ui` | ¿Cómo se representa y opera todo lo anterior en JavaFX? | `ReferenceTree`, `VirtualExplorerView`, `TypingEditor` |
| `languages`, `library` | ¿Cómo se reconocen lenguajes y ejercicios internos? | `LanguageRegistry`, `BuiltInLibrary` |
| `media`, `markdown`, `sound`, `symbols` | ¿Cómo se muestran otros formatos y ayudas? | `MediaTranscoder`, `MarkdownRenderer` |
| `commands` | ¿Cómo se ejecuta una orden externa deliberadamente? | `CommandRunner`, `ProjectCommandStore` |

## 2. Los dos índices que no deben confundirse

`ReferenceTreeIndex.scan` recorre el sistema de archivos mediante
`Files.walkFileTree` fuera del hilo de JavaFX. Conserva un mapa de hijos
inmediatos, carpetas y totales recursivos. Es un **índice topológico** para
navegar, expandir y mostrar contadores: el recorrido inicial cuesta
aproximadamente O(N), con N entradas, pero consultas posteriores a hijos y
contadores evitan otra visita al disco.

`ReferenceCatalogue.scan` hace una tarea distinta: registra metadatos,
contenido elegible, unidades practicables y huellas SHA-256 para el progreso.
Debe evitar cargar archivos multimedia completos solo para contar ejercicios.
No conviene fusionar estos dos servicios: abrir el explorador no necesita
esperar a un análisis semántico o de progreso más caro.

`ReferenceScanRules` define dónde se omiten carpetas generadas al buscar
código, evaluar progreso o construir dependencias. **No** es una orden para
ocultar dichas carpetas del explorador de consulta.

## 3. Por qué se virtualizó el explorador

Inicialmente, el árbol JavaFX expandía `TreeItem` de forma recursiva. Para
miles de carpetas, cada llamada podía disparar nuevas celdas, layout y
notificaciones sobre el hilo de la interfaz. Mantener datos en RAM ayuda a
reducir lecturas de disco, pero **no** elimina el coste de crear miles de
objetos gráficos.

La solución actual combina dos presentaciones sobre un mismo modelo:

1. `ReferenceTree`: `TreeView` perezoso para abrir carpetas manualmente,
   preservar interacciones y disponer de un árbol simple cuando es pequeño.
2. `ExplorerRows.project`: una proyección lógica e inmutable de rutas,
   profundidades y carpetas expandidas; usa un recorrido iterativo con pila y
   ordena los hermanos. Su coste depende de las filas visibles más el orden
   de cada grupo de hermanos; nunca es «O(1) para expandir todo».
3. `VirtualExplorerView`: `ListView` con `VirtualFlow` y celdas reutilizables
   para presentar esa proyección. Solo se dibuja una pequeña parte del total.
4. `VirtualChildrenIndex`: añade hijos virtuales desde una instantánea en
   memoria, sin consultar el workspace completo por cada carpeta.

Se preservan cancelación, límite de filas y generaciones de trabajo para
evitar que una tarea antigua reemplace la navegación actual.

**Regla de interfaz:** mientras el índice inicial no existe, se presenta un
estado de preparación visible; no se informa «Proyecto listo» como si ya
hubieran aparecido los elementos. Al expandir una referencia ya visible, no
se bloquea la lista con un modal: la preparación continúa fuera de JavaFX y
se mantiene la información que el usuario ya estaba consultando.

**Compromisos que siguen existiendo:** la exploración inicial sigue siendo
O(N) respecto al sistema de archivos; la vista usa memoria proporcional a
las filas proyectadas; hay un límite explícito de filas. El sistema debe
probarse en Windows con scroll y filtros reales, no solo con JUnit sin interfaz.

## 4. Reconstrucción virtual y mecánica de escritura

`VirtualWorkspace` es la fuente de verdad para carpetas, archivos y texto
**recreados**, no para el contenido físico original. Un camino relativo como
`src/Main.java` puede existir como referencia sin haberse creado todavía en
el workspace virtual. `VirtualChildrenIndex` es una vista derivada, no una
segunda fuente editable.

`TypingSession` modela ranuras alineadas con grafemas Unicode de referencia.
Una tecla no inserta libremente texto nuevo al final de un documento, sino
que llena o cambia una posición esperada. Aciertos, errores y omisiones tienen
semánticas diferentes. `TypingEditor` representa estas ranuras mediante
filas virtualizadas y maneja teclado, foco y scroll; **no** debe implementar
nuevas reglas de puntuación o progreso.

`ReconstructionStatus` distingue estructura creada de ejercicio
completado. `ProgressAccumulator` conserva contribuciones por archivo para
actualizar contadores al escribir, sin reanalizar el catálogo completo en cada
pulsación; si cambia el ámbito, la exclusión o la estructura, se invalida y
recalcula.

## 5. Modelo de concurrencia

El hilo **JavaFX Application Thread** crea controles, administra foco y
publica cambios visuales. El trabajo de escanear carpetas, leer sesiones,
verificar huellas, buscar contenido o preparar multimedia utiliza tareas
en segundo plano.

`LatestTaskRunner<T>` y las generaciones de solicitudes resuelven
un problema distinto del simple rendimiento: una tarea vieja puede
terminar después de que el usuario haya cambiado de proyecto. Se combina
`Task.cancel(true)` con una comprobación del identificador de la petición
antes de publicar resultados. **Cancelar no equivale a garantizar que el
trabajador ya haya terminado físicamente**.

El ciclo de activación pasa por `ProjectActivation` y
`CodeTraceApp.applyProjectState`: cancela tareas obsoletas, libera pestañas,
reemplaza la reconstrucción, actualiza referencias y vuelve a calcular
modelos derivados. El orden importa más que repartir el método en decenas
de clases artificiales.

## 6. Sesiones persistidas y seguridad de datos

`SessionArchive` define un formato binario explícito, actualmente versión 4.
No serializa arbitrariamente objetos Java. Lee y valida encabezados,
recuentos, cadenas UTF-8, posiciones y rutas, y conserva compatibilidad
con formatos anteriores contemplados por los tests.

Al guardar se escribe primero un **archivo temporal junto al destino** y
después se sustituye el documento `.ctrace`, con movimiento atómico cuando
el sistema de archivos lo permite. Si una escritura se interrumpe antes de la
sustitución, debe conservarse la última sesión buena. La reconstrucción puede
desaparecer de la memoria al cerrar, pero nunca debe reescribirse
silenciosamente el proyecto real.

La revisión de huellas por `ReferenceChangeDetector` verifica si la fuente
cambió desde la última práctica. La carpeta original sigue siendo la
referencia de comparación; un hash desactualizado no autoriza a sobrescribirla.

## 7. Mapas de dependencias: análisis y geometría separados

`ProjectDependencyGraph` detecta relaciones locales reconocibles entre
archivos; es una herramienta de exploración estática aproximada. No equivale
al análisis de un compilador, a resolver todas las bibliotecas externas ni
a confirmar que un proyecto compila.

`GraphLayoutEngine` determina posiciones según relaciones y reserva espacio
para las etiquetas. `GraphEdgeRouting` toma decisiones de trayectorias de
líneas; `DependencyGraphPane` dibuja en `Canvas`; `GraphCamera` convierte
coordenadas del mundo en coordenadas del viewport para paneo y zoom.

Esta división responde a tres escalas distintas: la topología del grafo,
su geometría legible y su presentación. Guardar posiciones del mundo en la
sesión no exige guardar la posición temporal de la cámara ni modificar archivos.

## 8. Fronteras de confianza y efectos secundarios

**Referencia y vista previa.** Markdown y recursos locales son contenido
no confiable: se restringe la carga de imágenes y scripts. Las vistas
multimedia pueden utilizar JavaFX, FFmpeg opcional y temporales en ubicaciones
separadas; cerrar pestañas debe detener reproductores y liberar recursos.

**Consola.** Es la excepción deliberada al principio de solo lectura:
`CommandRunner` ejecuta comandos externos reales mediante procesos del
sistema operativo. Por ello requiere una acción explícita y avisos claros.
No utiliza la reconstrucción virtual como sustituto de la carpeta real.
Mantiene límites de salida y cancelación de procesos descendientes.

**Integridad.** Las operaciones virtuales, los analizadores y el archivo
de sesión se comprueban mediante pruebas de no modificación de referencias.
Los permisos del sistema operativo siguen siendo una frontera adicional,
no algo que la UI pueda prometer por sí sola.

## 9. Cómo estudiar esta base de código

Recorrido sugerido, siguiendo dependencias y no el orden alfabético:

1. `VirtualWorkspace` → `ReferenceTreeIndex` → `VirtualChildrenIndex`.
   Pregunta: ¿qué estructuras permiten consultar un árbol sin tocar el disco?
2. `TypingSession` → `ReconstructionStatus` → `ProgressAccumulator`.
   Pregunta: ¿qué constituye un acierto y qué datos son derivados?
3. `SessionArchive` → `ProjectActivation` → `CodeTraceApp`.
   Pregunta: ¿cuál es la transacción lógica al cambiar de proyecto?
4. `ExplorerRows` → `VirtualExplorerView` → `ReferenceTree`.
   Pregunta: ¿por qué la complejidad del modelo no es la del renderizador?
5. `ProjectDependencyGraph` → `GraphLayoutEngine` →
   `GraphCamera` → `DependencyGraphPane`.
   Pregunta: ¿qué significa una coordenada persistente frente a un píxel?
6. `CommandRunner` y `MediaTranscoder`.
   Pregunta: ¿qué riesgos aparecen al iniciar procesos externos?

Ejercicios útiles de ingeniería: dibujar un diagrama de dependencias por
**paquetes**, identificar llamadas que atraviesan la frontera FX/worker,
justificar cada caché con una operación dominante, y escribir un test que
compruebe un **invariante** antes de microoptimizar código.

## 10. Política de Javadoc

La documentación de clase describe su responsabilidad, colaboradores y
límites; el Javadoc de métodos públicos debe añadir contratos, unidades,
semántica de nulos, efectos secundarios o coste **solo cuando no sean
evidentes**. Los comentarios internos justifican decisiones inusuales
(por ejemplo, generación para descartar trabajos obsoletos, reemplazo atómico,
o por qué se conserva un índice), sin narrar cada asignación o setter.

No escribir «usa patrón X» para justificar una abstracción que el dominio no
necesita; primero documentar el problema, después la solución escogida.
Los comentarios deben actualizarse en la misma revisión que cambie el
comportamiento. Ni este documento ni el Javadoc sustituyen a JUnit, pruebas
de integración y aceptación visual en Windows.
