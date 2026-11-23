## Mapa de estructura con archivos opcionales — v0.3.5 experimental

- **GRAPH-FILES-01:** Abrir un proyecto con carpetas y varios archivos Java,
  Python o TypeScript. Por defecto solo deben verse las carpetas. Clic
  derecho en el fondo del grafo → **Mostrar archivos de código**: aparecen
  los archivos como nodos secundarios con línea fina hasta su carpeta directa.
  No debe aparecer ninguna flecha semántica nueva al activar esta opción.
- **GRAPH-FILES-02:** Clic en un archivo: resalta el archivo e inspecciona
  su carpeta derecha. Doble clic: abre su referencia. Arrastrar un archivo
  debe mover **ese archivo** sin desplazar la cámara ni los demás nodos.
  Comprobar que al moverlo lejos aparece su conexión continua hasta la
  carpeta, y que al intentar invadir otra etiqueta el movimiento se bloquea.
- **GRAPH-FILES-03:** Mover una carpeta y alternar mostrar/ocultar tres veces:
  sus posiciones y el diseño organizado/cuadrícula no deben cambiar.
  Guardar y restaurar .ctrace debe conservar posiciones de carpetas; no
  requiere persistir posiciones de archivos.
- **GRAPH-FILES-04:** Si únicamente existe una clase de conexión, se oculta la
  leyenda. Con contención y archivos visibles, se muestra una leyenda para
  esas dos clases. Si además hay dependencias detectadas, aparecen los tres
  símbolos diferenciados.
- **GRAPH-FILES-05:** Abrir una referencia extensa. Comprobar que el canvas
  permite pan y zoom, informa cuántos archivos se visualizan y advierte
  «vista parcial» cuando se alcanzan los límites del analizador o dibujo.
  No debe bloquearse al activar/desactivar los nodos de archivo.
- **GRAPH-FILES-06:** En la vista, nunca representar configuraciones,
  Markdown, imágenes u otros archivos que el catálogo de código no reconoce.
  El inspector sigue ofreciendo navegación mediante doble clic e Intro.
- **GRAPH-FILES-07:** Con 11 archivos dentro de app, confirmar una isla
  compacta cerca de su carpeta (rejilla local, no un barrido global de filas).
  Seleccionar app resalta la isla y sus archivos en violeta tenue; seleccionar
  AppSettings.java destaca solo ese documento con violeta más intenso.
  La selección desde el inspector debe sincronizar el mismo resaltado.
- **GRAPH-FILES-08:** Con carpetas próximas y numerosos archivos,
  ninguna isla debe cubrir rótulos de carpetas ni solaparse con otra isla.
  Cada carpeta se une a su grupo con **una sola línea continua, sin flecha**.
  Al seleccionar una carpeta se engrosan las aristas incidentes; al seleccionar
  un archivo se engrosa su conexión a la carpeta. Las dependencias dirigidas
  conservan sus flechas y la jerarquía su línea discontinua.
  No se crean nuevas dependencias ni se alteran las posiciones persistidas.
  Los enlaces entre carpetas pueden cruzarse: no representan objetos sólidos.
- **GRAPH-FILES-09:** Arrastrar carpetas, cambiar entre organizado y
  cuadrícula, ocultar y mostrar los archivos: recalcular islas sin
  intercalar los archivos de dos carpetas. Revisar también un proyecto de
  más de 1.200 fuentes y una carpeta cerca del borde del espacio lógico.
- **GRAPH-FILES-10:** En una carpeta con 198 fuentes al lado de una carpeta
  con solo un archivo, este último debe quedar en una isla próxima a su
  carpeta y no ser expulsado por la isla grande.
- **GRAPH-FILES-11:** Arrastrar un archivo, ocultar/mostrar archivos y mover
  su carpeta: se mantiene el desplazamiento relativo si hay espacio.
  Restablecer distribución descarta esos movimientos de archivos y devuelve
  el empaquetado automático. La sesión .ctrace solo conserva posiciones
  manuales de **carpetas**, no de archivos.

- **GRAPH-FILES-12:** La línea de un archivo seleccionado debe terminar
  en el borde de su isla, sin atravesar otras etiquetas. Las conexiones
  del grafo se dibujan debajo de fondos y textos de las islas; verificarlo
  en un mapa grande con numerosas relaciones.
- **GRAPH-FILES-13:** Seleccionar dos archivos distintos del mismo inspector
  de manera sucesiva. Solo el último queda seleccionado en el ListView
  y resaltado en el Canvas. El hover debe distinguirse visualmente de
  la selección, también cuando el puntero permanece encima de una fila.
- **GRAPH-FILES-14:** Activar «Solo panear y desplazar» debajo de la leyenda.
  Arrastrar desde un nodo o archivo debe mover únicamente la cámara, no
  alterar posiciones ni marcar la sesión como modificada. La rueda sin Ctrl
  ajusta el zoom; un clic selecciona; doble clic en archivo NO abre pestaña.
  Desactivar la casilla restablece movimiento de nodos, doble clic de archivo
  y Ctrl+rueda para zoom. Repetir con la leyenda oculta por tener una sola
  relación: la casilla debe seguir visible.

Las pruebas automáticas comprueban distribución, límites y eventos JavaFX;
la fluidez y legibilidad requieren revisión manual en Windows.

# CodeTrace — aceptación manual en Windows

**Estado: pendiente de ejecutar en hardware real.** El CI prueba compilación y arranque básico, pero no sustituye la inspección visual de JavaFX WebView ni la reproducción real de códecs. Utiliza una copia desechable del proyecto de referencia si pruebas comandos externos.

## Preparación

1. Ejecuta scripts\diagnostico.bat. El diagnóstico compila y prueba el proyecto, genera automáticamente target\qa-preview, abre CodeTrace y al cerrarlo genera el ZIP técnico.
2. Abre target\qa-preview como referencia dentro de CodeTrace. Si FFmpeg está instalado en PATH, el generador crea también audio y vídeo de prueba.
3. Alternativamente, abre un repositorio de muestra con README.md, una subcarpeta assets, archivos .bat y .cmd, código Java y TypeScript, un SVG y archivos multimedia.
4. Conserva los hashes SHA-256 de los archivos originales antes y después.
5. Repite con una sesión .ctrace nueva y con otra restaurada.

## Matriz por validar

| ID | Prueba | Resultado requerido |
|---|---|---|
| MD-01 | Imagen Markdown relativa (assets/logo.png) | Se muestra sin rutas externas ni alterar el archivo |
| MD-02 | Imagen con espacios y %20 | Se muestra correctamente |
| MD-03 | HTML de imagen centrada con p/img | Se muestra la imagen, no las etiquetas literales |
| MD-04 | PNG/JPEG/SVG/WebP y ruta inexistente | Se muestran las imágenes válidas y los avisos |
| MD-05 | Tablas, bloques de código, emojis Unicode y :rocket: | Render correcto; el código permanece literal |
| MD-06 | Imagen HTTPS con permiso desactivado | Ninguna descarga automática; botón explícito |
| MD-07 | Habilitar/bloquear imágenes HTTPS y cerrar pestaña | Se respetan los controles y se cancelan cargas |
| MD-08 | Imagen que intenta salir del proyecto con ../ | El contenido no se muestra |
| EXP-01 | Nombres largos .bat y .cmd | Iconos vectoriales visibles separados del texto |
| EXP-02 | Expandir y contraer un árbol profundo | Sin bloqueo persistente ni pérdida de foco |
| EXP-03 | PNG/JPEG/GIF/BMP/WebP y SVG seguro | Pestañas de solo lectura, WebP mediante WebView |
| EXP-04 | SVG con scripts o enlaces externos | Rechazado; sin ejecución ni descarga |
| MED-01 | MP3, WAV y MP4/H.264 conocidos | Reproducir, pausar, detener y desplazar |
| MED-02 | FLAC, OGG, MKV, AVI y WebM | Conversión temporal si FFmpeg está disponible |
| MED-03 | FFmpeg ausente o códec incompatible | Error claro, referencia intacta |
| MED-04 | Cerrar pestaña durante conversión | Cancelación del proceso y limpieza temporal |
| MED-05 | Cambiar de proyecto con vídeo abierto | Reproductor liberado, sin reproducción residual |
| PR-01 | Modo manual | Consultar no crea; escribir exige creación |
| PR-02 | Modo directo | Crea virtualmente al primer carácter |
| PR-03 | Turbo e imports omitidos | No incrementan aciertos aunque cierren ejercicios |
| PR-04 | Filtros y exclusión por carpeta | Modifican cómputo, no visibilidad ni archivos |
| PR-05 | Guardar/restaurar sesiones v1/v2 | Progreso correcto y referencia intacta |
| UX-01 | LATAM/US, teclado y foco tras cambiar pestaña | Atajos, teclas muertas y acentos al final de archivo correctos |
| UX-02 | Tamaño, opacidad, paneles y escala DPI | Sin solapamientos ni truncamientos |
| CLI-01 | Consola: comando controlado, detener y cerrar | Sin ejecución implícita; proceso termina aunque se pulse Detener inmediatamente |
| CLI-02 | Cambiar de referencia con comando en curso | No mezcla salida del proyecto anterior |
| PR-06 | Archivo resuelto únicamente con omisiones voluntarias | Indicador de finalización sin incrementar aciertos |
| PR-07 | Abrir una sesión .ctrace truncada o alterada | Informa error sin restauración parcial |
| PKG-01 | ZIP portable sin JDK/Maven | CodeTrace.exe inicia y abre una práctica |
| PKG-02 | Portable con FFmpeg incluido legalmente | MKV/FLAC sin FFmpeg instalado globalmente |

Para cada caso anota PASS / FAIL / N/A, captura, versión de Windows, escala DPI, archivo mínimo que reproduce el defecto y ZIP de diagnóstico. No cerrar incidencias visuales solo porque Maven verify esté verde.


## Iteración UX de navegación y búsqueda

En el explorador, selecciona una carpeta y marca/desmarca "Excluir del progreso".
La exclusión se mantiene por proyecto, no oculta archivos y se hereda por subcarpetas.
"Solo" sigue siendo un ámbito temporal; no equivale a excluir carpetas.
Escribe en "Buscar archivo o carpeta" para filtrar nombres y mostrar antecesores.
Borra el campo para regresar a la estructura original.

En imágenes, SVG, WebP y vídeos, la zona inferior cambia de "16 pt" a "100 %".
Usa Ctrl+rueda, +/− y arrastre con clic izquierdo para ampliar y panear. El
doble clic restablece 100 %. Abre un código: debe reaparecer el zoom tipográfico.

Comprueba el menú "Pestañas" y el menú contextual del tab: cerrar actual,
cerrar las demás y cerrar todas; las sesiones .ctrace no se borran.
En Preferencias > Comandos, pulsa Nuevo, Agregar/Actualizar comando y luego
Aplicar: estos dos últimos pasos son diferentes deliberadamente.

En Herramientas > Buscar, prueba Ctrl+F (archivo) y Ctrl+Shift+F (proyecto).
Busca "CodeTrace" (literal), "sumar" (solo Demo.java) y
"int\s+total" como expresión regular. Abre un resultado; debe
resaltarse sin avanzar ni modificar la mecanografía. F3/Shift+F3 navega.
La búsqueda global incluye config.json, aunque no sea un ejercicio de escritura.

| ID | Prueba | Resultado requerido |
|---|---|---|
| NAV-01 | Filtrar nombres en el explorador | Antecesores visibles, sin leer contenidos |
| NAV-02 | Excluir carpeta seleccionada | Persiste, no oculta archivos; herencia indicada |
| NAV-03 | Cambiar "Solo" y "Excluir" | Estados independientes |
| NAV-04 | Cerrar las demás y todas las pestañas | Recursos multimedia liberados, sesión conservada |
| NAV-05 | Cambiar entre código, imagen y vídeo | Zoom tipográfico vs multimedia contextual |
| NAV-06 | Ctrl+rueda y arrastrar una imagen grande | Zoom 25–400 %, paneo, restablecimiento |
| FIND-01 | Buscar en archivo actual | Rutas, líneas y columnas correctas |
| FIND-02 | Buscar en todo el proyecto | Ignora binarios y carpetas generadas |
| FIND-03 | Búsqueda literal, palabra completa, mayúsculas y regex | Coincidencias verificables |
| FIND-04 | Navegar a coincidencia en Java/Markdown/JSON | Resalta sin mover el cursor de transcripción |
| FIND-05 | Interrumpir búsqueda, cambiar de proyecto | No muestra resultados obsoletos |
| CMD-02 | Editar comando y Aplicar cambios | Persistencia explícita y UI consistente |

## Empaquetado y FFmpeg

CodeTrace usa directamente los formatos admitidos por JavaFX. Si no puede reproducirlos, puede convertirlos temporalmente con FFmpeg, sin editar el repositorio.

Busca FFmpeg en este orden: propiedad Java codetrace.ffmpeg, binario junto a la aplicación empaquetada, directorio local de usuario .codetrace/tools/ffmpeg y PATH.

Para el ZIP portable:

    powershell -ExecutionPolicy Bypass -File scripts\package-windows.ps1

Para un instalador EXE, añadir -Installer (necesita WiX).

Para incluir un FFmpeg legalmente redistribuible, proporcionar el binario en third_party/ffmpeg/bin/ffmpeg.exe y la licencia en third_party/ffmpeg/LICENSE.txt. Revisar el licenciamiento y las obligaciones de fuentes y avisos antes de ejecutar el script con -IncludeFfmpeg.

No se distribuye FFmpeg por defecto. La CI no reemplaza la auditoría de licencias.

## Revisión de interfaz: reproductor, búsqueda y reglas

- AUDIO-UI-01: Abre \`src/main/resources/sounds/success.mp3\` dentro del
  proyecto CodeTrace. El audio debe presentar controles de icono reproducir
  (triángulo), pausar (dos barras) y detener (cuadrado), cada uno con tooltip.
  La barra debe permitir desplazarse, junto a tiempo y volumen.
- AUDIO-UI-02: Comprueba el espectro de frecuencias: barras reactivas al sonido
  durante reproducción; tras detener, quedan en reposo. Esta representación
  no es una forma de onda precalculada.
- AUDIO-UI-03: Una pestaña MP3 muestra controles de audio y **no muestra zoom
  de imagen**. Una pestaña PNG o vídeo sí recupera el zoom multimedia.
- AUDIO-UI-04: Cierra un audio mientras reproduce y cambia de referencia:
  no debe quedar reproducción ni seguir recibiendo eventos de espectro.
- SND-01: Los recursos \`tecla-correcta.wav.gz\` y \`tecla-error.wav.gz\`
  son archivos WAV comprimidos con GZIP que \`TypingSounds\` descomprime
  y normaliza en temporal. Los originales nunca se deben modificar.
- FIND-UI-01: La ventana Buscar muestra el icono de CodeTrace en la barra
  de título. Su lista de coincidencias presenta nombre, ruta + línea +
  columna y fragmento, con el mismo estilo de registros de Comandos.
- NAV-UI-01: Aumentar el espacio sobre y alrededor del buscador del explorador.
  La X para limpiar carece de borde salvo en estados hover/foco.
- PRACTICE-UI-01: En Reglas de práctica, marcar «Autocrear archivos y carpetas»
  activa \`DIRECT\`: las carpetas y el archivo se crean **virtualmente al
  empezar a transcribir**, no al abrirlos. Desmarcar activa
  \`RECONSTRUCTION\`: creación manual. El menú Modalidad del proyecto debe
  reflejar siempre la misma opción. Los originales y estadísticas permanecen
  intactos al alternar.


## Revisión: coloreado, grafo y compatibilidad de ejercicios

- SYNTAX-01: Abre `styles/theme.css` dentro de `target/qa-preview`. Debe
  distinguir selector, propiedad, número, comentario verde y código hexadecimal,
  mostrar líneas y permitir copiar sin alterar el archivo.
- SYNTAX-02: Abre JSON/XML/YAML de referencia y comprueba colores de
  propiedades, tipos básicos y literales, sin activar mecanografía.
- SYNTAX-03: Java/Kotlin/Python/JS/TS/C/C++: los tipos primitivos, llamadas
  como `obj.metodo()`, números y comentarios tienen estilos distinguibles.
- GRAPH-01: Selecciona la carpeta raíz de `target/qa-preview`, abre
  Herramientas → Mapa de dependencias. Debe encontrar Java A→B, TS main→util,
  Python app→helper y C app→demo.h (la cabecera está en include/).
- GRAPH-02: Selecciona una carpeta sin enlaces internos, usa el mismo menú.
  Debe explicar que no encontró dependencias internas, sin crear una pestaña vacía.
- GRAPH-03: Cambia de referencia durante el análisis. No debe abrirse un mapa
  del proyecto anterior.
- GRAPH-04: Usa el zoom y doble clic sobre un nodo de archivo. Debe abrir
  la consulta sin afectar las estadísticas de transcripción.
- PACK-01: Práctica → Biblioteca: 13 paquetes, 108 ejercicios. Kotlin 2.2
  incluye 16, así como Java 21 y TypeScript 5.9; los demás seis.
- PACK-02: Cada archivo contiene en comentarios una versión explícita y un
  comando PowerShell para comprobar/compilar/ejecutar, según tenga `main`.
- VISUAL-01: Valida barra de menú pizarra contra barra de herramientas neutral
  cálida; comprobar legibilidad y contraste en Windows y al abrir submenús.

## Aceptación: zoom global, autocreación y mapa arrastrable

- ZOOM-TEXT-01: Abre `pom.xml`, `README.txt`, `config.json` y un CSS.
  Usa el deslizador inferior de `pt`: todos los caracteres aumentan o disminuyen
  manteniendo la coloración y el contenido de solo lectura.
- ZOOM-TEXT-02: Cambia de archivo de consulta a Java o Markdown; el
  deslizador conserva el tamaño de letra y no modifica las sesiones.
- GRAPH-ZOOM-01: Abre un mapa con conexiones y comprueba que desaparece
  el deslizador de letra y aparece el de porcentaje con icono de compartir.
  Dentro del mapa NO debe haber otro deslizador. Al regresar al código
  debe volver el control en puntos.
- GRAPH-DRAG-01: Arrastra un nodo; la flecha debe seguirlo mientras lo mueves.
  El doble clic sigue abriendo el archivo asociado.
- GRAPH-SAVE-01: Guarda sesión, cierra, vuelve a abrir el archivo `.ctrace`,
  genera el mapa de la misma carpeta y verifica las posiciones guardadas.
- GRAPH-RESET-01: Usa `Restablecer distribución` y comprueba que recupera
  la posición regular, guardable nuevamente.
- GRAPH-REF-01: Cambia el código real de una copia de prueba, actualiza
  referencia y genera mapa: las posiciones antiguas no deben restaurarse.
  No se debe alterar ningún archivo original automáticamente.
- AUTO-01: Activa `Autocrear archivos y carpetas`, espera el índice y
  observa carpetas/archivos virtuales de práctica en estado creado amarillo.
  Archivos de imagen/medios pueden indicar no aplicable.
- AUTO-02: Verifica que un archivo con 0 caracteres transcritos sigue
  aportando 0 % aunque esté creado. Termínalo y verifica verde.
- AUTO-03: Alterna Código fuente/Markdown, Solo carpeta y Excluir carpeta:
  los indicadores de creación no desaparecen, pero el porcentaje usa
  exclusivamente el conjunto seleccionado.
- AUTO-04: Desactiva autocreación: no desaparecen los archivos ya creados,
  y la práctica manual sigue funcionando.
- TABS-UI-01: Abre muchas pestañas, verifica que el menú de desbordamiento
  sea rectangular, legible y consistente con el resto de controles.

## Ámbito Solo: foco visual y navegación

- SOLO-01: Selecciona `src/main/java`, marca `Solo`. El subárbol y sus
  ancestros deben mantenerse legibles; `src/test` y `README.md` quedan
  atenuados sin verse bloqueados.
- SOLO-02: Sin desmarcar `Solo`, selecciona `src/main/resources`:
  el foco visual y el porcentaje deben cambiar al nuevo ámbito.
- SOLO-03: Selecciona el nodo raíz del proyecto o desmarca `Solo`:
  todas las ramas recuperan su aspecto normal y el progreso es global.
- SOLO-04: Abre un archivo atenuado. Debe permitir consulta o práctica sin
  contabilizar sus caracteres en el porcentaje de otra carpeta.
- SOLO-05: Combina `Solo`, `Excluir del progreso` y autocreación: el foco
  no borra exclusiones, los estados creados o los ejercicios terminados.
- SOLO-06: Al abrir otra referencia o sesión, no debe quedar atenuada
  por un ámbito perteneciente al proyecto anterior.

## Aceptación adicional: menú de carpeta y distribución ordenada

- TREE-MENU-01: Clic derecho en carpeta con subcarpetas: aparecen
  **Expandir todas las subcarpetas**, **Colapsar todas las subcarpetas**
  y **Mapa de dependencias...**.
- TREE-MENU-02: Clic derecho en carpeta sin subcarpetas: omite colapso
  recursivo, pero conserva el acceso al mapa.
- TREE-MENU-03: Expande varias subcarpetas, colapsa recursivamente una
  carpeta intermedia (ella misma queda cerrada) y vuelve a abrirla.
  No deben reaparecer subcarpetas abiertas.
- TREE-MENU-04: En esa carpeta pulsa «Expandir todas las subcarpetas».
  TODOS los niveles hasta los más profundos deben abrirse en lotes,
  sin congelar la interfaz. Prueba de nuevo «Colapsar todas» y verifica
  que la carpeta seleccionada también se cierre.
- GRAPH-OPEN-01: Clic derecho sobre una carpeta que NO estaba seleccionada
  y abre Mapa: el análisis debe corresponder exactamente a esa carpeta.
- GRAPH-MODE-01: El selector de radios ofrece Cuadrícula y Organizado, con
  una sola opción activa y estilos rectangulares consistentes.
- GRAPH-MODE-02: Organizado agrupa nodos cercanos por conexiones, procura
  reducir cruces y deja suficiente espacio entre sus nombres. Debe
  conservar flechas y doble clic para abrir archivos.
- GRAPH-MODE-03: Arrastra nodos en ambas distribuciones en cualquier
  dirección. La cámara permite paneo sin límites, sin agrandar el Canvas.
- GRAPH-STATUS-01: Selecciona y arrastra nodos: la ruta relativa completa
  aparece a la izquierda en la barra de estado. El pie del mapa no la repite.
- GRAPH-SAVE-02: Elige Organizado, reacomoda nodos, guarda la sesión,
  cierra y vuelve a abrirla. Deben recuperarse radio y posiciones.
- GRAPH-LEGACY-01: Abre un .ctrace v3: conserva nodos guardados
  en modo Cuadrícula, sin errores de lectura.

## Aceptación: revisión de arrastre, superposición y microcopias

- MAP-DEFAULT-01: Abre un mapa por primera vez, sin sesión previa.
  Su modo inicial debe ser Organizado y no debe mostrarse barra de radio
  botones ni títulos de estadísticas encima del lienzo.
- MAP-PREFS-01: Herramientas → Preferencias → Grafo. Comprueba los
  radios estilizados Organizado / Cuadrícula y guarda Cuadrícula. Los
  mapas nuevos deben utilizar Cuadrícula; vuelve a Organizado después.
- MAP-DRAG-01: Con zoom 55 %, panea hasta una zona alejada del origen
  y arrastra varias veces un nodo. El scroll NO debe saltar a (0,0) y
  las aristas deben acompañar el movimiento.
- MAP-PAN-01: Arrastra el fondo: debe desplazarse el mapa, pero no
  ningún nodo. Arrastra el nodo desde su etiqueta o desde el círculo:
  solo ese nodo cambia de posición.
- MAP-LABELS-01: En el grafo real de CodeTrace y en una estrella con
  al menos 80 relaciones, no deben superponerse las etiquetas iniciales.
  Los enlaces pueden cruzarse, pero no atravesar visualmente las letras.
- MAP-STATUS-01: Selecciona un nodo. La barra inferior izquierda muestra
  su ruta; el área informativa de la barra inferior incluye los números
  de conexiones. La vista del grafo no debe tener microcopias repetidas.
- MAP-RESET-01: Clic derecho sobre el lienzo y elige Restablecer
  distribución. Deben recuperarse posiciones sin modificar las fuentes.
- MAP-SESSION-01: Mueve un nodo, guarda y reabre la sesión. La posición
  debe conservarse; una disposición anterior con etiquetas superpuestas
  debe repararse al abrir el mismo grafo.
- MAP-VISUAL-01: Valida el contraste del texto sobre su fondo, el
  zoom contextual y la ausencia de bandas o saltos al cambiar tamaño.

## Revisión visual: zoom anclado, botón central y vecinos únicos

- PREF-GRAPH-02: Abre Preferencias → Grafo. Organizado y Cuadrícula
  aparecen **en vertical**, sin rectángulo exterior. Cambiar uno
  desmarca el otro.
- GRAPH-ZOOM-ANCHOR-01: Abre un grafo, desplázate al centro del mapa y
  coloca el cursor sobre una etiqueta. Pulsa Ctrl+rueda varias veces.
  Ese nodo debe permanecer prácticamente bajo el cursor, no viajar
  hacia el ángulo superior izquierdo. Repite desde distintos lugares.
- GRAPH-ZOOM-ANCHOR-02: Modifica el zoom desde el slider inferior.
  Debe mantenerse el **centro del área visible** y seguir pudiendo
  desplazarte hasta todos los bordes del contenido escalado.
- GRAPH-PAN-MIDDLE-01: Pulsa el botón central sobre un nodo o el fondo:
  el cursor muestra mano cerrada. Arrastra para panear; al soltar
  vuelve a mano abierta o al cursor de nodo. No debe reubicar archivos.
- GRAPH-PAN-MIDDLE-02: Arrastra un nodo con botón izquierdo después de
  panear: solo cambia su posición, sin salto del viewport.
- GRAPH-LEAF-01: Prueba un pequeño grafo con cuatro archivos que solo
  referencian a un quinto. Los cuatro satélites deben aparecer en
  celdas vecinas de su único padre, no al otro extremo del mapa.
- GRAPH-LEAF-02: En un grafo real con conexiones múltiples, verifica
  que el algoritmo mantiene etiquetas separadas y que las aristas
  pueden cruzarse sin tapar nombres.

## Regresión: selección del grafo, colapso contextual y Acerca de

- GRAPH-SELECT-01: Selecciona un nodo con dependencias. Debe verse violeta
  y solo sus vecinos de primer grado en turquesa; los enlaces directamente
  relacionados se distinguen del resto.
- GRAPH-SELECT-02: Prueba un nodo con aristas entrantes y salientes: ambos
  conjuntos de vecinos deben recibir resaltado. Los vecinos de segundo
  grado NO deben resaltarse.
- GRAPH-SELECT-03: Haz clic una sola vez sobre fondo vacío, sin arrastrar:
  desaparecen los colores de selección, la ruta se sustituye por el
  resumen «Mapa · carpeta» y el statusbar muestra totales.
- GRAPH-SELECT-04: Un arrastre sobre fondo sigue paneando y no modifica
  posiciones ni selecciona nodos por accidente. El doble clic sobre
  un archivo continúa abriéndolo.
- TREE-COLLAPSE-01: Expande `src`, `main`, `java`, `com`, etc.
  Clic derecho en `main` → «Colapsar todas las subcarpetas».
  `main` queda cerrada. Reabrirla muestra `java` y `resources`
  cerradas; reabrir `java` deja sus descendientes igualmente cerrados.
- TREE-COLLAPSE-02: Repite sobre un subárbol pequeño y verifica que
  el menú funciona aunque otra carpeta estuviera seleccionada.
- ABOUT-AUTHOR-01: Ayuda → Acerca de CodeTrace: logo, versión,
  texto del producto y sección «Autor y desarrollo» con retrato del autor.
  Debe desplazarse verticalmente cuando falte altura, sin texto truncado.
- ABOUT-AUTHOR-02: Abre Acerca de sin internet. La imagen debe ser
  local y no solicitar permisos externos; su recurso empaquetado tiene
  resolución 112×112.

## Última aceptación: cierre recursivo y cámara virtual (18-nov-2026)

Consulta también [Auditoría de cierre](AUDITORIA-CIERRE-2026-11.md).

- TREE-RECURSIVE-01: expande `src/main/java/com/...` completamente,
  clic derecho sobre `src` → «Colapsar todas las subcarpetas».
  `src` debe quedar cerrado. Al reabrirlo aparecen `main` y `test`
  cerradas. Reabre `main`: `java` sigue cerrada; al reabrirla,
  `com` sigue cerrada. Después pulsa «Expandir todas las subcarpetas»
  y verifica que `com` se despliega sin abrir cada nivel manualmente. La selección permanece coherente.
- GRAPH-CAMERA-01: abre un mapa nuevo: Organizado debe activarse
  inicialmente incluso si una versión experimental anterior había
  guardado Cuadrícula. El grafo aparece centrado y ajustado.
- GRAPH-CAMERA-02: panea miles de píxeles a derecha, izquierda,
  arriba y abajo. No deben existir bordes del antiguo Canvas ni
  crecer el consumo de memoria por un bitmap enorme.
- GRAPH-CAMERA-03: pulsa «Centrar y ajustar a ventana» en el menú
  contextual del mapa o Inicio. Debe aparecer otra vez el conjunto de
  nodos sin borrar las posiciones manuales.
- GRAPH-CAMERA-04: usa Ctrl+rueda sobre un nodo y arrastra un nodo
  con zoom 55 %. La cámara no debe saltar ni desplazarse al mover
  únicamente el nodo.
- GRAPH-PREF-03: tras migrar preferencias antiguas, Organizado es
  predeterminado. Elegir Cuadrícula explícitamente en Preferencias debe
  respetarse en mapas nuevos; un archivo .ctrace antiguo no debe
  imponer Cuadrícula silenciosamente.
- FIND-CHECKS-01: en Buscar, comprueba que Distinguir mayúsculas,
  Solo palabras completas y Expresión regular usen el mismo control
  rectangular que el explorador y Preferencias, sin solapamientos a
  anchura mínima de ventana.
- RELEASE-01: ejecutar el paquete portable en Windows limpio,
  comprobar licencia/avisos de terceros, versión y todos los P0 de
  la auditoría antes de etiquetar una versión estable.


## Regresión arquitectónica (tras refactorización de noviembre)

- **FIND-FIRST-CLICK:** buscar en todo el proyecto, pulsar una sola vez
  sobre un resultado y comprobar que abre la pestaña correcta, muestra la
  línea seleccionada y resalta la coincidencia. Repetir con Java, XML, JSON
  y Markdown en modo fuente. La transcripción no debe cambiar.
- **FIND-FILTER-LAYOUT:** con ventana amplia, Ámbito y los tres checkboxes
  deben ocupar una fila. A 590 px deben envolver sin solaparse ni ocultarse.
- **CODE-GUTTER:** abrir un Java para practicar y un XML para consultar.
  Ambas vistas deben mostrar numeración de líneas 1-based desde el inicio,
  incluso tras cambiar zoom, mover scroll y buscar una coincidencia.
- **TAB-LIFECYCLE:** abrir Markdown con imágenes, un vídeo y dos archivos;
  cerrar pestaña individual con X, cerrar otras y cerrar todas. Reabrir cada
  referencia sin estado residual y confirmar que cesa el audio/vídeo.
- **TREE-INDEXED:** expandir/colapsar directorios profundos y aplicar filtro
  por nombre sobre un proyecto con miles de archivos, incluyendo carpetas
  virtuales creadas. Debe seguir respondiendo; ninguna operación escribe
  archivos de la referencia.
- **TREE-CANCEL:** iniciar Expansión recursiva en un árbol grande, cambiar
  inmediatamente de proyecto y verificar que la expansión anterior no
  reaparece ni modifica el nuevo árbol.


## GUTTER-COMPACT — numeración continua al estilo de Notepad++

- Abrir Java de práctica y XML/JSON de consulta: margen gris **continuo**, sin números en cajas ni rayas horizontales.
- Números alineados a la derecha, separador vertical fino y separación discreta antes del texto.
- Fila activa tenue, sin barra gruesa azul a la izquierda.
- A 13 pt caben más líneas que antes, sin recortar glifos o desajustar cursor.
- Probar 10, 20, 36 pt; archivo de 1000+ líneas, zoom, búsqueda, pestañas y Windows 100/125/150 % DPI.

## PROJECT-ASYNC — pruebas de cancelación de trabajos

- Abrir un proyecto grande, actualizar la referencia y comprobar que la
  interfaz sigue respondiendo mientras se verifican las huellas.
- Cambiar inmediatamente de proyecto o cerrar CodeTrace durante un escaneo;
  ningún resultado previo debe sobrescribir el nuevo estado.
- Abrir una sesión `.ctrace` con archivos originales modificados: informar
  la cantidad de referencias divergentes conservando la transcripción virtual.
- Buscar una coincidencia y cambiar inmediatamente de proyecto: una navegación
  diferida no debe recuperar la pestaña cerrada ni resaltar código del anterior.

## TREE-ASYNC-CANCEL — regresión de tareas obsoletas

- Abrir un proyecto grande y cambiar de referencia mientras aún aparece
  actividad en el explorador: jamás deben reaparecer carpetas anteriores.
- Escribir tres consultas rápidamente en el filtro del explorador y borrarlo:
  debe imponerse la última consulta, sin resultados rezagados.
- Actualizar referencia durante un escaneo y cerrar la aplicación: cierre
  sin bloqueo ni mensajes tardíos; no se publican índices parciales.

## SEARCH-LIMITS — límites explícitos y resultados de diagnóstico

- Buscar dentro del proyecto CodeTrace: los resultados de `reports/` no
  deben mezclarse con los archivos fuente y documentación.
- Generar 1600 coincidencias en un proyecto temporal: Buscar presenta
  1500 y avisa que llegó al límite.
- Buscar una palabra dentro de un archivo que tenga líneas de más de 4096
  caracteres: informar el número de líneas omitidas, nunca decir que los
  resultados son exhaustivos.
- Autocrear 1000 nodos y cambiar inmediatamente de proyecto: no debe
  completarse trabajo del proyecto anterior ni crearse archivos físicos.

## SEARCH-REGEX-BUDGET — patrones costosos

- Comprobar búsqueda regex normal de palabras y números con acentos.
- Ejecutar una expresión de retroceso patológico en un proyecto de prueba:
  debe informar patrón demasiado costoso y dejar responder la interfaz.
- Ejecutar una búsqueda costosa y pulsar «Detener»: no deben quedar
  resultados tardíos ni modificarse los archivos de referencia.

## SESSION-ASYNC — restauración sin congelar la interfaz

- Abrir un `.ctrace` grande: el mensaje «Leyendo sesión» aparece y la
  ventana continúa respondiendo. La sesión activa no se reemplaza hasta
  terminar correctamente la lectura.
- Durante la carga, cambiar a otro proyecto o elegir otro `.ctrace`:
  la primera operación no debe restaurarse después.
- Durante la carga, modificar la transcripción actual: antes de sustituirla
  debe solicitar confirmación; al cancelar se conserva el progreso.
- Abrir una sesión con referencia original ausente y cancelar la reubicación:
  no cambia el proyecto abierto.
- Verificar que un escaneo de archivos grandes se interrumpe al cambiar de
  proyecto y que ninguna referencia se modifica físicamente.

## SEARCH-QUERY-CHANGE — resultados anteriores obsoletos

- Buscar «class», después escribir «interface» sin ejecutar de nuevo:
  los resultados de la consulta anterior deben desaparecer y no navegarse.
- Cambiar entre Archivo actual/Todo el proyecto, mayúsculas, palabras
  completas y regex: no mantener coincidencias obsoletas de otro ámbito.
- Tras una búsqueda válida, introducir una expresión regular inválida y
  pulsar Buscar: mostrar el error de validación, no coincidencias antiguas.
- Pulsar Detener durante una búsqueda y repetir con otro patrón: los
  callbacks del trabajo cancelado no deben repoblar la lista.

## COMMAND-CONFIG / SEARCH-CANCEL — regresión

- Añadir datos sobrantes a un archivo `.ctcmd` de prueba: CodeTrace debe
  rechazar la configuración, sin ejecutar sus comandos ni modificar
  archivos dentro del proyecto original.
- Iniciar una búsqueda lenta y pulsar «Detener» o Escape: mostrar
  «Búsqueda cancelada» y no recuperar resultados tardíos.

## REFERENCE-SYMLINK — protección de referencias

- En un entorno de pruebas, crear una subcarpeta simbólica hacia un
  directorio exterior y reabrir una sesión con una huella bajo ese enlace:
  debe contarse como referencia divergente, nunca leerse como original.
- Mantener rutas normales y proyectos con la raíz seleccionada mediante
  un enlace interno funcionando como antes.

## COMMAND-ACTIVE-STOP — proceso externo en ejecución

- Lanzar un comando de larga duración con una salida inicial.
- Mientras permanece activo, pulsar «Detener» y verificar que la consola
  recupera el botón Ejecutar y el proceso deja de ejecutarse.
- Intentar iniciar un segundo comando durante la primera ejecución: solo
  debe ejecutarse uno.
- Al cambiar de proyecto durante el comando, comprobar que no se agregan
  líneas de salida antiguas a la nueva consola.

## MEDIA-FULL-PLAYBACK — MKV completo, calidad HD y cadencia

- Abrir un MKV H.264/AAC de varios minutos: debe conservar **el vídeo
  completo**, no solo 30 segundos, y mostrar la duración total en la
  barra de tiempo; saltar al minuto final.
- Comparar el vídeo original con el MP4 temporal remultiplexado: misma
  resolución, FPS y duración. No debe existir `-r 24`, `-t 30` ni
  escala forzada a 540p en el modo rápido.
- Usar un archivo con códec HEVC/AV1/VP9 no admitido por JavaFX:
  presentar opción para preparar **todo** el vídeo en H.264 HD hasta
  1080p, conservando la frecuencia de fotogramas; no iniciar esta
  recodificación pesada sin confirmación.
- En caso de decodificación entrecortada, probar la apertura del
  archivo original en el reproductor externo y comparar fluidez.
  El éxito del remux no demuestra que JavaFX tenga aceleración por GPU.
- Cancelar la preparación y cerrar la pestaña: eliminar los MP4
  temporales `codetrace-fullmedia-*`; el original debe mantenerse intacto.
- Con poco espacio libre o sin FFmpeg: mostrar aviso accionable y
  permitir abrir el original sin que CodeTrace quede bloqueado.

## UX-PROJECT-HEADER — controles y diálogos compactos

- La barra principal contiene Abrir y Guardar, pero no «Actualizar referencia».
- La cabecera Proyecto presenta tres acciones pequeñas a la derecha:
  actualizar, contraer todas y expandir todas, con accesibilidad y tooltip.
- Comprobar que Actualizar permanece desactivado si no hay proyecto y
  conserva pestañas y progreso al ejecutarlo.
- Abrir el diálogo «Sesión modificada»: debe ajustarse al texto, sin una
  gran zona vacía, y seguir mostrando Sí, No y Cancelar completos.
- Provocar un mensaje largo de error: el texto debe desplazarse dentro
  del diálogo sin recortar el botón de aceptación, incluso a DPI 150 %.

## MEDIA-SEEK-ANYWHERE — barra temporal y navegación aleatoria

- Con un MP4 completamente preparado de dos minutos, hacer clic en el
  10 %, 50 % y 95 % de **la pista**, lejos del cuadradito: debe saltar
  directamente al instante proporcional, sin avances por incrementos.
- Arrastrar el pulgar y soltarlo: debe mantenerse el comportamiento de
  arrastre y el tiempo reflejado en la etiqueta.
- Hacer clic tras pausar y tras pulsar Detener: el salto no debe
  volver a transcodificar FFmpeg ni alterar el archivo de referencia.
- Usar flechas, Inicio y Fin con el control enfocado. Comprobar también
  funcionamiento con el ancho de panel estrecho y a 150 % de DPI.
- El salto dentro de un MP4 completo no implica generación de segmentos;
  la reproducción HLS progresiva sigue siendo una mejora futura.

## PRACTICE-COMPLETED — felicitación y sonido de finalización

- Crear un archivo de código breve y transcribir correctamente su última
  letra: mostrar «Archivo terminado» con un solo botón «Aceptar».
- Reproducir `sounds/success.mp3` una vez en ese momento si
  Preferencias > Sonido > «Reproducir sonido al terminar un archivo»
  está activado (valor predeterminado). Con la casilla desactivada, el
  aviso visual permanece y el sonido no se reproduce.
- Abrir un archivo ya completado desde una sesión `.ctrace`: no volver
  a mostrar felicitación. Modificarlo y completarlo de nuevo sí genera
  una nueva transición.
- Corregir una letra equivocada al final debe mostrar el aviso una vez;
  una referencia vacía o un ejercicio todavía incorrecto, nunca.
- La felicitación no depende de que haya terminado de indexarse la
  referencia ni de que el árbol haya actualizado su icono de progreso.

## MEDIA-CODEC-CHOICE — abrir un vídeo sin pantalla en blanco

- Con `ffprobe` disponible, los vídeos MKV con H.264 mantienen la ruta
  de apertura rápida; los que contengan HEVC, VP9 o AV1 preparan una
  versión compatible automáticamente, sin reproducir solo audio primero.
- Si `ffprobe` no está instalado, continuar con la apertura habitual y
  ofrecer un botón sencillo de compatibilidad si falta la imagen.
- Los mensajes visibles deben describir acciones del usuario: «Abriendo
  vídeo», «Reintentar» y «Abrir con otra aplicación»; reservar nombres de
  códecs, FFmpeg y detalles técnicos para el diagnóstico.
- Cancelar y reintentar la preparación dentro de la misma pestaña.


## Cierre final: workspace vacío, cronómetro, Full HD y EXE

- **EMPTY-01:** Al iniciar sin pestañas se muestra el logotipo ORIGINAL en
  escala de grises, centrado, con «Abre un archivo para comenzar». No sustituir
  su geometría por una versión reinterpretada.
- **EMPTY-02:** Abrir archivo y cerrar la última pestaña con X, menú Cerrar
  todas y al cambiar de referencia. La pantalla vacía reaparece inmediatamente
  y ninguna vista multimedia queda abierta.
- **CLOCK-01:** Barra de estado muestra 00:00:00 aun sin referencia y en modo
  concentración. Clic en Iniciar: avanza; Pausar: se detiene; Reanudar: continúa.
  Botón de iniciar desde cero: vuelve a 00:00:00 y corre.
- **CLOCK-02:** Pasar entre archivos, gráficos, vídeos, cerrar todas las
  pestañas y guardar la sesión no debe reiniciar ni esconder el cronómetro.
  Cerrar la aplicación detiene el temporizador; reiniciar comienza en cero.
- **VIDEO-FHD-01:** MP4 H.264/AAC 1920×1080 real, duración completa,
  audio/vídeo, pausa, línea de tiempo, seek, volumen y finalización.
- **VIDEO-FHD-02:** MKV H.264 y vídeos HEVC, VP9 o AV1 con FFmpeg y ffprobe
  disponibles: imagen visible, duración íntegra, máxima resolución 1080p
  para conversiones, sin tocar el archivo de referencia.
- **VIDEO-FHD-03:** MP4 cuyo códec reproduce sonido pero pantalla negra:
  pulsar «¿No se ve la imagen? Mejorar reproducción» y comprobar que se
  obtiene imagen H.264. Verificar cancelación/reintento y limpieza temporal.
- **DIST-01:** El workflow «Windows portable package» entrega tanto
  CodeTrace-Windows-portable.zip como el instalador CodeTrace-*.exe.
- **DIST-02:** Instalar desde el EXE en un Windows x64 sin Java ni Maven,
  abrir/cerrar, revisar accesos directos, desinstalar y repetir con ZIP.
  Verificar carpeta de usuario y preservación de los archivos .ctrace.
- **DIST-03:** Sin FFmpeg instalado, H.264 directo debe seguir disponible.
  Para otros formatos, debe existir mensaje comprensible o abrir el original
  con el reproductor del sistema. Antes de redistribuir FFmpeg, revisar
  su licencia, avisos y fuentes correspondientes.

**Gate:** no considerar publicada la v0.3.0 hasta registrar aceptación
manual Windows real de los puntos anteriores y resolver los P0 de auditoría.


## Aceptación multimedia por segmentos (HLS local) y artefactos de consulta

- **HLS-01:** Abrir un MKV/WebM/AVI/HEVC/VP9 real de 20–60 minutos.
  Debe mostrarse el reproductor inmediatamente tras analizar metadatos;
  pulsar Reproducir no debe esperar la conversión de todo el archivo.
  Medir tiempo desde clic a primer fotograma para comparar con la ruta antigua.
- **HLS-02:** Saltar de 00:10 a 22:00 y luego a 05:00. CodeTrace solo
  debe procesar los intervalos solicitados; verificar vídeo Y audio tras
  cada salto y que no requiera procesar los minutos intermedios.
- **HLS-03:** Continuar más de dos fragmentos (6 segundos cada uno)
  comprobando sincronía labial, ausencia de pantallas negras, continuidad
  del audio y del reloj de reproducción. Repetir a 720p, 1080p y 60 fps.
- **HLS-04:** Pausar, reanudar, arrastrar línea de tiempo, saltar durante
  reproducción, y detener. Confirmar que conserva el control de volumen
  y no se congelan explorador ni cronómetro general.
- **HLS-05:** Cerrar la pestaña durante la transcodificación o cambiar
  de referencia: ningún proceso FFmpeg queda en segundo plano ni el
  puerto de loopback permanece abierto. Se eliminan los medios temporales.
- **HLS-06:** Probar cuando FFmpeg o FFprobe no están disponibles,
  cuando falla un segmento y cuando otro proceso ocupa muchos recursos.
  La interfaz debe ofrecer recuperación y reproductor externo, sin bloquearse.
- **HLS-07:** El servidor local escucha solo en 127.0.0.1, utiliza una
  ruta aleatoria privada, no permite recorrer carpetas ni servir archivos
  arbitrarios. Los originales mantienen exactamente el mismo hash.
- **TREE-NOPRACTICE-01:** Doble clic en artefacto marcado «No requiere
  práctica de mecanografía». Debe mostrar un diálogo de información con
  ese mismo texto y permitir la consulta sin sumar progreso de mecanografía.

La prueba unitaria de HLS confirma URLs, lista VOD, segmentos y acceso directo
a la parte final. Solo la prueba GUI en Windows confirma que JavaFX reproduce
HLS con imagen, sonido, seek y sincronía reales.


## Auditoría del cierre: estadísticas, revelar originales, diálogos y capturas

- **PRACTICE-METRICS-01:** Practica un .c o .ps1, comete un error y corrígelo.
  Debe mostrarse felicitación (si está habilitada) con aciertos finales
  correctos, tiempo transcurrido, velocidad media de esta apertura y errores
  introducidos antes de corregirlos. Un archivo restaurado no puede inflar
  la velocidad con los caracteres correctos de sesiones anteriores.
- **PRACTICE-METRICS-02:** En Preferencias desactiva solo «Mostrar felicitación
  y estadísticas al terminar un archivo». Completa otro .java/.ps1: no
  aparece diálogo, pero el sonido success.mp3 sigue disponible si está
  habilitado. Desactiva solo el sonido y comprueba lo contrario.
- **PRACTICE-METRICS-03:** Audio, vídeo, imágenes, PDF y DOCX no deben generar
  jamás el mensaje de finalización de mecanografía.
- **DIALOG-ICONS-01:** Comprueba iconos discretos y legibles para información,
  éxito, confirmación, advertencia y error; sin cortarse ni saturar el diseño.
- **EXPLORER-01:** Abre un proyecto de referencia bajo un directorio con
  espacios, acentos y comas. Botón derecho en un archivo, «Mostrar en el
  Explorador»: debe abrir la carpeta CONTENEDORA exacta y seleccionar el
  archivo. No debe abrir «Documentos» ni la raíz por defecto.
- **AUTO-CAPTURE-01:** Ejecuta scripts/auditoria-visual.ps1 sin argumentos.
  Cambia entre varias vistas de CodeTrace y abre al menos un diálogo:
  genera ZIP con imágenes de los estados realmente mostrados y manifiesto.
  No requiere pulsar Intro en cada pantalla.
- **AUTO-CAPTURE-02:** Con la consola PowerShell en primer plano, la captura
  debe RECHAZAR la ventana aunque su título diga CodeTrace. Verifica que
  tampoco capture el navegador u otras aplicaciones. El ZIP no se envía
  automáticamente.
- **AUTO-CAPTURE-03:** Con -Guiado, comprueba el flujo opcional de 37 casos
  y la opción S (omitir) / Q (terminar). Al cerrar la app en modo automático,
  crea el ZIP y abre la carpeta reports correcta.

La captura automática observa cambios visuales mientras se navega, pero
NO recorre por sí misma casos de uso no abiertos. La cobertura completa de
37 casos solo existe después de comprobar cada caso explícitamente.


## Pulido conservador y biblioteca ampliada (noviembre 2026)

- **WARN-01:** Una nueva referencia y ejecución externa muestra «Confirmar
  ejecución sobre archivos reales» con la casilla
  «No volver a mostrar esta advertencia» DESMARCADA. Cancelar no debe ocultar
  advertencias ni ejecutar el comando. Aceptar con la casilla marcada evita
  futuras confirmaciones, sin ocultar el aviso persistente de consola.
- **WARN-02:** Preferencias → Avisos: opción para volver a exigir la
  confirmación y botón «Restablecer avisos de seguridad». Aplicar, ejecutar
  otra orden en el mismo proyecto y comprobar que el aviso reaparece.
  No restablecer fuentes, otros sonidos ni configuración de grafos.
- **HOVER-01:** Sobre botones, pestañas, menús, iconos y controles claros:
  los iconos siguen siendo visibles en color azul/gris, nunca blanco sobre
  blanco. El botón azul principal de reproducción conserva blanco sobre azul.
- **EMOJI-01:** Abrir texto de consulta y código con 🙂, 🇪🇨, ✅, 👨‍👩‍👧‍👦
  y un keycap 1️⃣. Mantener los grafemas completos; comprobar fuentes de
  Windows (Segoe UI Emoji), sin reemplazos ni cuadros vacíos.
- **EMOJI-02:** Ninguno de esos emojis requiere tecleo, ni incrementa
  correctos/CPM; su presencia no impide el diálogo de archivo terminado.
  Probar corrección justo antes/después del emoji y restaurar sesión .ctrace.
- **DEPS-01:** «Omitir dependencias de código» es el único texto visible del
  botón y menú. Probar declaraciones import Java/Kotlin, Python, TS/JS,
  #include C/C++, using C#, import Go (incluido bloque), require Ruby,
  source Bash, Import-Module PowerShell y %include NASM. No omitir
  llamadas ordinarias a funciones ni comentarios que contengan «import».
- **LIB-01:** Biblioteca de práctica ofrece 14 paquetes y 160 archivos.
  Verificar carpetas de proyectos multiarhivo para Factory Method y Observer;
  ejecutar muestras manualmente con toolchains cuando estén disponibles.
  La validación automática compila TODOS los archivos Java 21 conjuntamente.
- **ASM-01:** NASM x86-64 Intel aparece con ícono propio, resaltado y
  práctica habilitada. Validar «nasm -f elf64» sobre los ocho archivos en
  Linux/WSL; el ejemplo «hola_linux.asm» utiliza llamadas al sistema Linux
  y no debe presentarse como aplicación Win32 nativa.

La verificación de compilación automatizada NO sustituye la revisión gráfica
de emojis ni la comprobación manual de los iconos al pasar el ratón.


## Regresión multimedia / avisos / emojis (cierre de noviembre)

- **MEDIA-NOTICE-01:** Doble clic en .mkv, .mp4, .mov, .mp3, .wav, .png,
  .jpg, .svg y .webp. Se abre su visor de solo lectura directamente, sin
  mostrar ninguna ventana «Archivo de consulta» ni mensaje de mecanografía.
  El comportamiento de PDF y documentos de consulta permanece independiente.
- **PREFS-AVISOS-01:** Abrir Preferencias sin proyecto: aparece una categoría
  izquierda visible «Avisos» con icono y controles de confirmación.
  Desmarcar, pulsar Aplicar y cerrar: un comando real no vuelve a solicitar
  confirmación. «Restablecer avisos de seguridad» vuelve a marcar la casilla;
  al pulsar Aplicar se reactivan las confirmaciones, incluso sin abrir un
  proyecto nuevo. El aviso permanente de la consola NO desaparece.
- **EMOJI-FIXTURE-01:** Ejecutar
  \`powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\generar-prueba-emojis.ps1\`.
  Debe crear un .txt y un .md dentro de Descargas sin sobrescribir copias
  anteriores. Abrir el TXT en CodeTrace, abrir Markdown y alternar
  Fuente/Renderizado; observar banderas, tonos, familia ZWJ, keycaps y
  alias Markdown; cada símbolo debería conservar su secuencia.
- **EMOJI-FIXTURE-02:** Distinguir legibilidad de renderizado a todo color:
  JavaFX Text en Windows puede utilizar pictogramas monocromáticos, incluso
  con Segoe UI Emoji. No afirmar visualización cromática sin observarla.
  Confirmar que los emojis no se exigen en el editor de práctica.

La compilación y los tests de componentes no certifican el aspecto final
de fuentes ni las ventanas modales: se comprueban manualmente en Windows.


## Auditoría final de reproductor y carpeta auxiliar (19 noviembre 2026)

- **AUDIO-UI-01:** Abrir MP3/WAV/FLAC y reproducir unos segundos. No debe
  aparecer «Audio de referencia · solo lectura» ni «ESPECTRO DE AUDIO ·
  EN TIEMPO REAL». Debe mantenerse la animación del espectro y mostrar
  datos verificados: formato, tamaño, duración al estar disponible y
  etiquetas de artista/álbum si existen. Nunca inventar bitrate o códec.
- **TREE-SECOND-01:** El ícono de maximizar expande las carpetas del
  proyecto principal. El botón **+**, distinto, permite seleccionar una
  carpeta auxiliar arbitraria; se crea una segunda sección del acordeón.
  No puede agregarse una tercera sección ni duplicar el árbol auxiliar.
- **TREE-SECOND-02:** Al agregar, cambiar y quitar la carpeta auxiliar,
  el proyecto principal, las exclusiones y el porcentaje de práctica
  permanecen intactos. Se pueden abrir MP3, videos, imágenes, TXT, fuentes
  y Markdown desde el árbol auxiliar sin crear sesiones ni carpetas
  virtuales. No hay comandos de crear, mover ni borrar en el auxiliar.
- **TREE-SECOND-03:** Abrir audio desde carpeta auxiliar, iniciar
  reproducción y cambiar a una pestaña Java de mecanografía.
  El audio debe continuar hasta que se pause/detenga o se cierre su pestaña.
  Una carpeta con miles de elementos debe cargar sin congelar la ventana.
  Comprobar que enlaces simbólicos no permiten salir de la carpeta elegida.
- **TREE-SECOND-04:** Archivos PDF o binarios sin visor integrado muestran
  información y acceso al Explorador, sin intentar interpretarlos como
  texto ni prometer edición deshabilitada en aplicaciones externas.
- **TXT-GUTTER-01:** Abrir CodeTrace-Emojis-Prueba.txt; comparar sus
  números de línea con C y Java. Los números deben alinearse a la derecha
  y cada archivo debe mantener una fila por línea original, incluso cuando
  contiene Unicode compuesto. Las líneas largas deben desplazarse en
  horizontal en lugar de empujar la siguiente línea.
- **EMOJI-SVG-01:** En Windows, preparar assets antes del instalador con
  \`powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\preparar-emojis.ps1\`.
  Son SVG oficiales de Twemoji (CC BY 4.0), fijados a una revisión
  específica y empaquetados sin peticiones externas durante la lectura.
  Verificar que el Markdown renderizado muestra caras, familias, banderas y
  keycaps completos; en Fuente y TXT se conserva el Unicode original.
  La ausencia de un recurso usa el fallback tipográfico, no un cuadro vacío.
- **EMOJI-SVG-02:** Verificar que el visor no interpreta código embebido
  ni incorpora solicitudes externas, que se mantiene su política CSP y
  que los créditos Twemoji aparecen en Acerca de y licencias del paquete.

El último cierre debe combinar Maven verify, diagnóstico Windows,
empaquetado y una comprobación real en JavaFX. Las capturas no equivalen
a una certificación de los estados que no se muestran.

## Asociación de sesiones y continuidad del gutter

- FILETYPE-01: Guardar un archivo .ctrace y confirmar que conserva su formato binario de sesión.
- FILETYPE-02: Instalar mediante CodeTrace-0.3.0.exe y comprobar que Windows muestra el
  icono propio de sesión (no el logo principal) para archivos .ctrace.
- FILETYPE-03: Doble clic desde Windows en una sesión con espacios o acentos en su ruta:
  CodeTrace debe iniciar y restaurar con el mismo cargador usado por Archivo > Abrir sesión.
- FILETYPE-04: Doble clic en .ctrace desde el árbol principal: restaurar en vez de
  mostrar el mensaje genérico de consulta. No modificar el proyecto de referencia sin
  la confirmación requerida para descartar cambios pendientes.
- FILETYPE-05: El ZIP portable no registra asociaciones; solo lo hace el instalador.
- FILETYPE-06: El ICO generado debe incluir 16, 24, 32, 48, 64, 128 y 256 píxeles.
- GUTTER-01: Abrir Java, YAML, TXT, Markdown fuente, JSON, XML y PowerShell.
  Los números de línea no deben mostrar franjas blancas entre celdas.
- GUTTER-02: Cambiar el tamaño de fuente y desplazarse por documentos extensos:
  las filas del gutter siguen alineadas con el contenido.

Las pruebas CI verifican la sintaxis del generador ICO, el recurso PNG y
el archivo de asociación. La presentación real de los iconos y gutter
todavía requiere comprobación visual en Windows.
