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

Resaltadores modulares, editor de texto con selección/deshacer avanzado, vista Markdown, comandos externos, iconografía instalada y recuperación guiada de cambios de referencia. No se implementará un IDE completo ni un asistente de programación.

## Consola / comandos
`commands/ProjectCommandStore` persiste configuraciones binarias versionadas
por ruta del proyecto fuera de la referencia. `CommandRunner` ejecuta un
proceso explícitamente solicitado en un hilo daemon, informa salida acotada
y valida que el directorio de trabajo permanezca dentro de la carpeta raíz.
`ui/CommandSettingsPane` gestiona los comandos desde Preferencias;
`ui/CommandConsolePane` muestra salida y ofrece ejecución/detención.
Ningún comando se ejecuta durante la apertura de archivos o sesiones.
