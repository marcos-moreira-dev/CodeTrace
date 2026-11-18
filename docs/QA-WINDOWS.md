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
