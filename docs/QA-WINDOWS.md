# CodeTrace — aceptación manual en Windows

**Estado: pendiente de ejecutar en hardware real.** El CI prueba compilación y arranque básico, pero no sustituye la inspección visual de JavaFX WebView ni la reproducción real de códecs. Utiliza una copia desechable del proyecto de referencia si pruebas comandos externos.

## Preparación

1. Ejecuta scripts\crear-fixtures-preview.ps1 desde PowerShell y abre target\qa-preview como proyecto de referencia. Si FFmpeg está disponible, generará vídeos y audios pequeños además de imágenes y README de prueba.
2. Ejecuta scripts\diagnostico.bat para generar el ZIP técnico.
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
| UX-01 | LATAM/US, teclado y foco tras cambiar pestaña | Atajos y teclas correctos |
| UX-02 | Tamaño, opacidad, paneles y escala DPI | Sin solapamientos ni truncamientos |
| CLI-01 | Consola: comando controlado, detener y cerrar | Sin ejecución implícita; proceso termina |
| PKG-01 | ZIP portable sin JDK/Maven | CodeTrace.exe inicia y abre una práctica |
| PKG-02 | Portable con FFmpeg incluido legalmente | MKV/FLAC sin FFmpeg instalado globalmente |

Para cada caso anota PASS / FAIL / N/A, captura, versión de Windows, escala DPI, archivo mínimo que reproduce el defecto y ZIP de diagnóstico. No cerrar incidencias visuales solo porque Maven verify esté verde.

## Empaquetado y FFmpeg

CodeTrace usa directamente los formatos admitidos por JavaFX. Si no puede reproducirlos, puede convertirlos temporalmente con FFmpeg, sin editar el repositorio.

Busca FFmpeg en este orden: propiedad Java codetrace.ffmpeg, binario junto a la aplicación empaquetada, directorio local de usuario .codetrace/tools/ffmpeg y PATH.

Para el ZIP portable:

    powershell -ExecutionPolicy Bypass -File scripts\package-windows.ps1

Para un instalador EXE, añadir -Installer (necesita WiX).

Para incluir un FFmpeg legalmente redistribuible, proporcionar el binario en third_party/ffmpeg/bin/ffmpeg.exe y la licencia en third_party/ffmpeg/LICENSE.txt. Revisar el licenciamiento y las obligaciones de fuentes y avisos antes de ejecutar el script con -IncludeFfmpeg.

No se distribuye FFmpeg por defecto. La CI no reemplaza la auditoría de licencias.
