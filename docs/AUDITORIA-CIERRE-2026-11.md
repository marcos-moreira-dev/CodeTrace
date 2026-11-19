# CodeTrace — auditoría técnica y de cierre

**Fecha:** 18 de noviembre de 2026 · **Rama:** `fix/media-preview-and-release-20261118` · **Objetivo:** estabilizar v0.3, no convertir CodeTrace en un IDE.

## Dictamen

CodeTrace ya cuenta con los flujos centrales de referencia inmutable,
transcripción, sesión `.ctrace`, exploración, búsqueda, consola, biblioteca
integrada, previsualización multimedia y mapa de dependencias. **No es un
producto cerrado:** el CI cubre una parte de la calidad, pero no prueba
todo el hardware Windows, códecs ni la interacción a distintas escalas DPI.

El trabajo de esta revisión incorpora:

- Menú con **Expandir todas las subcarpetas** (abre todos los niveles por
  lotes) y **Colapsar todas las subcarpetas** (cierra la propia carpeta y
  todos sus descendientes). Las vistas anteriores se reconstruyen sin
  conservar estados de expansión obsoletos.
- Mapa con **cámara lógica** `GraphCamera` independiente del Canvas:
  paneo sin bordes, sin bitmap gigantesco y sin reescritura de posiciones.
  Inicio centrado y con ajuste de vista; zoom anclado al cursor.
- Distribución inicial Organizado, con migración de la antigua preferencia
  experimental `graph.layout` a `graph.layout.v2`. La preferencia actual
  se respeta al generar mapas nuevos y no es sobrescrita por una sesión de
  Cuadrícula antigua. Los nodos manuales se restauran solo en el mismo modo.
- Estilo transversal `AppControls.checkBox`, aplicado a la búsqueda, las
  preferencias, el árbol y las reglas; filtros de Buscar distribuidos en una
  fila adaptable.

## Hallazgos priorizados

| ID | Prioridad | Hallazgo y evidencia | Criterio de cierre |
|---|---|---|---|
| C-01 | P0 | No hay aceptación visual definitiva sobre Windows real. El smoke test corre con JavaFX/Xvfb y el diagnóstico CLI en GitHub Actions. | Pasar matriz `QA-WINDOWS.md` en Windows con capturas, 100/125/150 % DPI y mouse/teclado físico. |
| C-02 | P0 | Deben validarse las sesiones v1–v4 con referencias modificadas, reinicios y errores de archivos. `SessionArchive` es el punto crítico de recuperación. | Guardar/reabrir progreso y grafo, probar corrupción, referencia cambiada y no perder el trabajo virtual. |
| C-03 | P0 | La licencia de redistribución del repositorio está **por decidir**, según README. FFmpeg no se distribuye de forma predeterminada por motivos de licencia. | Elegir licencia de CodeTrace y revisar avisos de dependencias/FFmpeg antes de publicar versión estable. |
| C-04 | P0 | La versión Maven es `0.3.0-SNAPSHOT`, mientras las ventanas y documentos pueden sugerir v0.3 definitiva. | Definir versión única, notas de cambios, etiqueta y paquete reproducible; probar ZIP portable en Windows sin JDK. |
| C-05 | P0 | Las vistas multimedia dependen de JavaFX/FFmpeg y de los códecs del sistema. Los tests automáticos no prueban todos los medios reales. | MP3/WAV/MP4 y fallback MKV/FLAC reales; detener/cerrar/liberar reproductores y temporales, sin modificar la referencia. |
| C-06 | P0 | Los comandos configurados se ejecutan en el **proyecto real**, no en la reconstrucción virtual. | Mantener advertencia visible, nunca autoejecutar, probar cancelación, salidas largas y rutas seguras. |
| E-01 | P1 | `CodeTraceApp` concentra menús, sesiones, preferencias, árbol, mapa, zoom y métricas (más de 1.600 líneas). | Extraer poco a poco controladores por dominio sin crear capas abstractas innecesarias; pruebas de cada extracción. |
| E-02 | P1 | `ReferenceTree` mezcla construcción, virtualización, menús, selección y expansión. | Separar reglas de expansión/menú de los controles JavaFX; probar árboles profundos y búsquedas simultáneas. |
| E-03 | P1 | Componentes transversales aún son parciales. Ya existen `AppControls`, `ExclusiveChoiceBar`, `RecordListCell` y zoom contextual, pero el inventario de pantallas no tiene una guía integral. | Matriz visual de checkbox, radio, selector, slider, tooltip, botón y diálogo; mismo foco, espaciado y contraste en todas las ventanas. |
| E-04 | P1 | Grafo con análisis léxico y vista limitada a **180 nodos/400 aristas**; dependencias dinámicas no son resolubles de forma general. | Aviso de vista parcial perceptible, conteos claros y pruebas de rendimiento/cancelación con repositorios grandes. |
| E-05 | P1 | Documentación histórica acumulada describe a veces grafo estático, versión `.ctrace` anterior o 12 paquetes frente a los 13 actuales. | Consolidar README, ROADMAP, arquitectura y QA antes de etiquetar; eliminar información contradictoria. |
| E-06 | P1 | Accesibilidad por teclado, foco en diálogo, contraste de nodos, navegación y estados deshabilitados no está validada íntegramente. | Recorrer toda la aplicación sin ratón; Escape para deseleccionar mapa, atajos, teclado LATAM y zoom 25–400 %. |
| E-07 | P1 | Búsqueda en proyectos y autocreación recorren estructuras extensas. Aunque usan tareas y límites, el rendimiento real requiere muestreo. | Probar cancelación y capacidad de respuesta con carpetas grandes, nombres Unicode y salida generada `target`. |
| E-08 | P1 | Los ejercicios integrados contienen versiones/comandos comentados, pero no se han compilado con cada toolchain externa. | Validar por lenguaje ejemplos seleccionados, manejo de Unicode y rutas Windows; documentar dependencias reales. |
| M-01 | P2 | Exportar mapa SVG/PNG, minimapa, rutas de import dinámicas, deshacer avanzado o editor completo. | Fuera del alcance de v0.3 salvo que bloquee una tarea esencial; registrar para futuras versiones. |

**Prioridades:** P0 impide una versión estable; P1 es deuda necesaria para calidad sostenida; P2 es opcional y no debe bloquear el cierre.

## Matriz UX de componentes compartidos

| Familia | Componente/estilo existente | Qué comprobar en Windows |
|---|---|---|
| Checkboxes | `AppControls.checkBox` + `.app-checkbox` | Buscar (tres opciones), Solo, Excluir, Autocrear, ajustes de audio; foco, hover, clic de etiqueta y deshabilitado. |
| Radios | `ExclusiveChoiceBar` | Grafo: vertical, sin borde artificial, selección exclusiva, preferencia guardada. |
| ComboBox | CSS global `.combo-box-base` | Buscar/ámbito, consola, preferencias; lista desplegable y navegación de teclado. |
| Sliders | `EditorZoomControl`, `MediaZoomControl`, CSS | Zoom pt/% y opacidad; el mapa debe mantener el punto bajo el cursor. |
| Listas | `RecordListCell` | Buscar y comandos; selección, truncamiento, texto largo y contraste. |
| Diálogos | `ProductDialogs`, `WindowBranding` | Icono, título, scroll, botones, foco inicial, Esc y fotografías locales. |
| Pestañas | `TabPane` con menú de desbordamiento | Cerrar todas/otras, selección, zoom correcto y liberación multimedia. |
| Explorador | `ReferenceTree` | Expandir todos los niveles y colapsar toda la rama, incluida la carpeta pulsada; conservar selección y navegación. |
| Grafo | `DependencyGraphPane` + `GraphCamera` | Centrado inicial, paneo virtual sin bordes, selección de vecinos, abrir archivo, sesión y reset. |

## Pruebas de aceptación añadidas por esta revisión

1. **TREE-DEEP:** abrir `src/main/java/com`, colapsar sobre `src`
   y comprobar que `src` queda cerrada. Al reabrirla, `main`, `java`
   y `com` permanecen cerradas hasta expandirse explícitamente.
   «Expandir todas» debe abrir los tres niveles sin clics adicionales.
2. **GRAPH-CAMERA:** al abrir, mapa centrado; panear grandes distancias
   horizontal y verticalmente y regresar. No debe existir borde de paneo.
3. **GRAPH-FIT:** clic derecho → Centrar y ajustar a ventana. Vuelve al
   conjunto de nodos, sin borrar su distribución manual.
4. **GRAPH-ZOOM:** con 55 %, arrastrar nodo sin que se mueva la cámara;
   Ctrl+rueda conserva bajo el cursor el mismo punto; el zoom inferior
   usa el centro de la vista.
5. **GRAPH-DEFAULT:** instalación migrada inicia en Organizado aunque
   hubiera una elección experimental de Cuadrícula; si el usuario
   cambia explícitamente a Cuadrícula en Preferencias, se respeta.
6. **FIND-CHECKS:** la búsqueda presenta los tres checkboxes con el
   mismo estilo que Preferencias/Reglas; todo es accesible a 590px
   de ancho y sin truncamiento.
7. **REGRESSION:** abrir sesión previa, consultar Markdown con imágenes,
   audio/video, comandos configurados, reglas de progreso, guardar
   y reabrir sin escribir en el proyecto de referencia.

## Secuencia de cierre recomendada

**Primero estabilización**: terminar la regresión JavaFX/Windows de esta
revisión y resolver defectos P0. **Después ergonomía**: aplicar
`AppControls` al resto de pantallas y comprobar foco/contraste;
no añadir más funciones. **Luego refactorización pequeña**: extraer
servicios/controladores desde `CodeTraceApp` y `ReferenceTree`
por casos reales, conservando pruebas. **Finalmente distribución**:
licencia, versión, documentación coherente, ZIP portable probado en
Windows limpio, etiqueta y notas de versión.

Para cada incidencia conservar: pasos de reproducción, resultado esperado,
resultado observado, captura, commit, y ZIP de `scripts/diagnostico.bat`.

**Regla de cierre:** ni un pipeline verde ni una captura bonita equivalen
a aceptación definitiva; deben cumplirse las verificaciones P0 en hardware
Windows real.

## Avances de cierre de esta revisión

- **TREE-DEEP / E-02 (implementación y regresión):** menú de expansión total
  y colapso total con pruebas JavaFX de varios niveles. Pendiente aceptación
  manual en el árbol de un repositorio real.
- **C-02 (parcial):** cobertura JUnit adicional para sesiones v4 con
  posiciones negativas, archivos truncados y escritura excesiva que NO debe
  destruir la última sesión guardada. La compatibilidad v1/v2/v3 ya tenía
  pruebas. Falta recuperación real en Windows con referencias modificadas.
- **C-06 (UX mejorada):** advertencia visible y confirmación antes del primer
  comando externo de cada proyecto; ejecuciones únicamente por clic explícito.
  Faltan pruebas manuales de comandos peligrosos y procesos largos.
- **E-04 (parcial):** aviso persistente «Vista parcial» en barra de estado
  y tooltip del tab cuando el análisis excede límites; no promete analizar
  todos los imports.
- **E-06 (parcial):** Buscar admite Escape para cancelar/cerrar,
  Ctrl+Intro para ejecutar y nombre accesible en la lista de resultados.
  Falta recorrido completo de foco, contrastes y DPI.

**No se declaran cerrados P0** hasta contar con las pruebas indicadas en
Windows y una decisión sobre licencia y paquete de publicación.

- **C-04 (parcial):** «Acerca de» ya obtiene la versión directamente
  de la propiedad `project.version` del POM mediante
  `codetrace-version.properties` filtrado por Maven, sin duplicar
  una cadena fija. Sigue faltando acordar versión final, etiqueta,
  notas de cambios y ensayo en Windows limpio.
- **TREE-RECURSIVE (aclaración):** «Colapsar todas las subcarpetas»
  contrae también la propia carpeta pulsada; «Expandir todas las
  subcarpetas» abre toda su descendencia de forma progresiva. Son
  operaciones inversas, con pruebas JavaFX que reabren la rama
  y verifican que no reaparezcan niveles antiguos.
