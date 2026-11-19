# CodeTrace — matriz de producto y plan de cierre

Actualización: 18 de noviembre de 2026. Auditoría consolidada en [AUDITORIA-CIERRE-2026-11](AUDITORIA-CIERRE-2026-11.md). Esta matriz distingue la **implementación** de la **validación manual**; un CI verde no demuestra por sí solo la ergonomía en Windows.

## Identidad y límites

**CodeTrace permite explorar, comprender y reconstruir proyectos de software sin alterar los archivos originales.**

Dos acciones distintas:

1. **Consultar:** abrir estructura, código, documentación y medios como referencias de solo lectura. No crea nada ni acumula progreso.
2. **Reconstruir:** crear virtualmente archivos/carpetas (o activar transcripción directa), transcribir caracteres, omitir partes de forma deliberada y guardar avances.

CodeTrace no pretende sustituir un IDE ni compilar o evaluar automáticamente la corrección del código.

## Reglas semánticas aprobadas

- Cada proyecto tiene un directorio de referencia **intacto**.
- Crear una carpeta o archivo es un hito virtual, no una escritura sobre la referencia.
- **Pendiente:** gris y cursiva. **Creado/incompleto:** visto ámbar. **Completado:** visto verde. Los archivos vacíos compatibles no necesitan transcripción.
- El porcentaje reconoce solo caracteres efectivamente escritos y correctos. Turbo, imports saltados y caracteres erróneos no suman, aunque las omisiones intencionales permiten concluir un ejercicio.
- Filtro de código/Markdown, carpeta activa y exclusiones afectan únicamente el **alcance de evaluación**, no la lectura, navegación o capacidad de practicar.
- Turbo y omisión de imports son **ayudas de escritura**, no categorías de archivos. Su interfaz está agrupada con el contenido evaluado en «Reglas de práctica».
- «Solo esta carpeta» selecciona un ámbito temporal; «Excluir carpeta del progreso» persiste por proyecto.
- Toda previsualización multimedia es de solo lectura y no cuenta para la mecanografía.

## Estado por capacidad

| Capacidad | Estado | Criterio para cerrarla |
|---|---|---|
| Árbol de referencia, conteo recursivo, expansión incremental e iconos de estados | Implementado | Comprobar grandes repositorios, foco y contraste en Windows |
| Reconstrucción manual, directa, sesiones `.ctrace`, aciertos/errores, Turbo, imports | Implementado; endurecida la lectura de sesiones y las entradas Unicode separadas | Auditoría de sesiones, regresiones y accesibilidad de teclado en Windows real |
| Sonidos de pulsación y finalización, preferencias persistentes | Implementado | Verificación auditiva en hardware Windows |
| Reglas de práctica agrupadas en toolbar; filtro código/Markdown; exclusión por carpeta | Implementado | Validar selección y restauración, porcentajes por carpeta y que no cambia el contenido visible |
| Markdown: fuente/renderizado, tablas, emojis, imágenes relativas, HTML `<img>` seguro | Implementado, visual pendiente | Probar en Windows imágenes PNG/JPEG/SVG reales y casos relativos de README |
| Imágenes externas HTTPS en Markdown | Implementado con opt-in | Verificar conexión, CSP, error sin internet y comportamiento al desactivarlas |
| Pestañas de imágenes PNG/JPEG/GIF/BMP/WebP y SVG seguro | Implementado, visual pendiente | Escalado, imagen grande y pestañas cerradas |
| Pestañas de audio/video con play, pausa, stop y desplazamiento | Parcial | Validar MP3/WAV/MP4; manejar códecs no disponibles y liberación de recursos |
| FFmpeg para formatos adicionales | Fallback implementado, binario no incluido | Probar conversión, cancelación, temporales y códecs reales; revisar licencias antes de incluir FFmpeg |
| Símbolos y miniteclado LATAM/US orientativo | Implementado | Confirmar diferencias de distribución en Windows y ajustar teclas especiales |
| Comandos del proyecto y consola inferior redimensionable | Implementado; reforzada la cancelación y separación de salida por proyecto | Revisar historial, ergonomía y salidas extensas, Windows/PowerShell/sh |
| Biblioteca de práctica interna por 13 lenguajes | Base implementada | Completar temarios; Java y TypeScript: patrones, estructuras, algoritmos y callbacks nativos |
| Iconos de archivo y diálogos con identidad CodeTrace | Implementado, auditoría pendiente | Corregir formatos faltantes; revisar consistencia, resoluciones y selección |
| Paquetes instalables y actualización del producto | Script jpackage + ZIP implementado, validación pendiente | Comprobar el artefacto de CI y ejecutar en Windows limpio sin JDK/Maven; instalador EXE con WiX sin validar |
| Auditoría funcional, producto, UX/UI y accesibilidad | Pendiente | Ejecutar matriz de aceptación completa y corregir hallazgos |

## Iteración audiovisual y ergonomía (implementada, aceptación pendiente)

- Audio: iconos reproducir/pausar y detener, tooltips, barra temporal,
  controles de volumen y espectro de frecuencias JavaFX en tiempo real.
- Los archivos WAV.GZ de efectos son recursos comprimidos; el audio original
  no se reescribe y no requiere FFmpeg.
- Zoom contextual visible solamente en imágenes y vídeos.
- Buscar: título con icono de aplicación y resultados en filas estilizadas
  mediante `RecordListCell`, compartido con Comandos del proyecto.
- Reglas: checkbox Autocrear archivos y carpetas sincronizado con modalidad
  DIRECT/RECONSTRUCTION por proyecto, sin estado adicional ni cambios reales.
- Explorador: mayor separación entre título y buscador, X limpia sin borde.
- Pruebas gráficas y matriz de aceptación pendientes de inspección en Windows.

## Iteración de navegación (implementación, verificación visual pendiente)

- Búsqueda de nombres en TreeView: filtra archivos y carpetas con padres visibles,
  conserva la estructura y funciona fuera del hilo gráfico.
- Checkbox de exclusión de la carpeta seleccionada junto a "Solo": herencia
  identificada y persistencia por proyecto, sin ocultar la referencia.
- Menú contextual y barra de herramientas para cerrar pestaña actual, las demás
  o todas, liberando tareas de Markdown y reproductores.
- Zoom multimedia contextual con rango 25–400 %, Ctrl+rueda y paneo;
  el zoom de letra permanece independiente.
- Ventana no modal de búsqueda en archivo/proyecto, literal/regex/palabras
  completas/mayúsculas, resultados navegables sin afectar la transcripción.
- Pestañas de consulta de solo lectura para configuraciones y textos no ejercitables.
- Comandos: lista con iconos, campos rectangulares y persistencia explícita
  mediante Aplicar/Aceptar.
- Pruebas automáticas de búsqueda; aceptación humana en Windows según QA-WINDOWS.

## Secuencia recomendada de cierre

### Fase 1 — Estabilización de exploración (prioridad máxima)
- Ejecutar la matriz documentada en [QA-WINDOWS](QA-WINDOWS.md), sin confundir CI verde con validación visual.
- Probar imágenes Markdown locales y externas con el repositorio real del usuario.
- Verificar SVG, imágenes, audio y video; limpiar recursos al cerrar pestañas o cambiar de proyecto.
- Verificar el fallback FFmpeg local, su cancelación, y la opción de empaquetado con licencia auditada.

### Fase 2 — Reglas y sesiones
- Auditar las interacciones de alcance: proyecto, carpeta, exclusiones y categorías, sin duplicación de controles.
- Verificar correctamente la finalización con Turbo e imports.
- Decidir si la configuración de exclusiones debe viajar dentro del archivo `.ctrace` (ahora se guarda localmente por proyecto).

### Fase 3 — Material de práctica
- Revisar los trece repositorios/paquetes internos: ejemplos cortos, nomenclatura y alcance.
- Completar los módulos de Java/TypeScript de patrones, algoritmos y estructuras de datos sin depender de frameworks de colección para los ejercicios didácticos.

### Fase 4 — Pulido y distribución
- Auditoría de producto: funciones que sobran, expectativas, errores legibles y ausencia de ediciones reales.
- Auditoría UX/UI: navegación por teclado, contraste, fuentes, barra de estado, escalado y paneles.
- Distribución para Windows con JDK/JavaFX runtime, pruebas de arranque limpio y licencia de terceros.

## Verificación y evidencias

- `mvn clean verify` con JDK 21 y Maven configurados.
- `scripts/diagnostico.bat` ejecuta diagnósticos y abre la aplicación para la prueba de aceptación.
- GitHub Actions ejecuta pruebas unitarias y arranque gráfico básico; **no sustituye** la inspección de las imágenes y códecs reales en Windows.
- No dar por terminada ninguna capacidad visual solo por haber compilado; vincular cada incidencia a una captura/reproducción y repetir la prueba tras corregirla.

## Ampliación de biblioteca por lenguajes (18-nov-2026)

- 13 paquetes y 108 ejercicios con versiones fijas y comandos de CLI en **cada archivo**.
- Java 21, Kotlin 2.2 y TypeScript 5.9: 16 ejercicios cada uno.
- Los otros diez lenguajes: seis ejercicios cada uno, con estructuras, algoritmos y flujos según corresponda.
- El propósito es equivalencia **temática**, no forzar Observer o clases en shell/CMD.
- Matriz de compatibilidad y comandos: [LENGUAJES-Y-COMANDOS](LENGUAJES-Y-COMANDOS.md).
- Validación integral con compiladores externos todavía pendiente; la prueba de Maven comprueba estructura y comentarios y compila Java 21.

## Mapa de dependencias (vista estática experimental)

- Herramientas → Mapa de dependencias de carpeta; requiere carpeta seleccionada.
- Solo imports resueltos a archivos/paquetes internos dentro del ámbito.
- Análisis sin ejecutar código, en trabajador aparte; límites de 1200
  archivos y 1200 aristas; aviso claro cuando no hay relaciones.
- Pestaña con círculos, flechas, zoom y doble clic para consultar el archivo.
- Soporte léxico básico para 13 lenguajes; sin prometer análisis profundo de
  rutas generadas por frameworks ni importaciones dinámicas.
- Próxima etapa: reducir cruces en diagramas grandes y ampliar resolución
  semántica por lenguaje con pruebas en repositorios reales.

## Ergonomía del workspace, fase posterior al grafo

- Zoom contextual único: puntos para código y consultas de texto, porcentaje
  para imágenes, vídeos y grafos. Un solo deslizador visible a la vez.
- Grafo interactivo: arrastre individual de nodos, posiciones persistidas en
  sesión v4 y huella del contenido para restablecer cuando cambie la referencia.
- Selección de carpeta, doble clic para abrir archivo, flechas y mapa sin
  dibujar imports externos inventados.
- Autocreación de la estructura virtual completa en lotes; excluye cachés
  y artefactos de compilación, sin considerar completados los archivos vacíos
  de transcripción.
- Mantener evaluación de caracteres y selección de categorías separadas
  del estado de existencia virtual.
- Ajuste estilístico del botón de pestañas desbordadas.
- Verificación manual de contraste, arrastre, zoom, formatos mixtos de texto
  y reapertura de sesiones antiguas en Windows.

## Lectura de dependencias: distribución y navegación

- Dos distribuciones elegibles desde Preferencias mediante ExclusiveChoiceBar:
  Organizado es el valor inicial; Cuadrícula es la alternativa explícita.
- Organización determinista con separación de etiquetas y partición
  de capas densas para evitar lienzos gigantescos.
- Cámara virtual independiente del Canvas, centrada al abrir, con paneo
  libre y dibujado solo en el viewport; no necesita agrandar un bitmap.
- Menú contextual: mapa para carpeta pulsada y colapso recursivo donde
  existan subcarpetas. El grafo no modifica fuentes originales.
- Ruta del nodo en statusbar global; pie de grafo solo muestra
  conteo de relaciones.
- SessionArchive v4 persiste modo y coordenadas, compatible con v1-v3.
- Verificación visual pendiente con proyectos de distintas densidades.

## Iteración de ergonomía del mapa (19-nov-2026)

- Organizado es el modo inicial; Cuadrícula pasa a Preferencias → Grafo
  y se conserva en la configuración local de la aplicación.
- Se elimina el segundo encabezado/pie de microcopias en el workspace.
  La ruta y los contadores se presentan en el statusbar global.
- La organización dirigida por fuerzas se estabiliza con celdas
  libres para todas las etiquetas, especialmente los hubs de alto grado.
- Se desacopla el arrastre de nodos del panning del ScrollPane y se
  pospone el crecimiento del lienzo hasta soltar el nodo, manteniendo
  la posición visible.
- Restablecer distribución y centrar/ajustar a ventana están en menú contextual.
- Checkboxes compartidos mediante AppControls, incluidos filtros de Buscar.
- Dos comandos inequívocos: expandir recursivamente TODOS los niveles de
  la carpeta elegida (por lotes), y colapsar ese directorio Y sus
  descendientes, sin reabrirlos al expandir manualmente después.
- Validación visual, accesibilidad y arrastre en Windows aún necesarias.
