# CodeTrace

**Transcripción de código y reconstrucción virtual de proyectos de software.**

CodeTrace es una herramienta de escritorio para practicar la creación de estructuras y la escritura de código sobre una referencia existente. No es un curso, un asistente de IA ni un IDE completo.

## Ejecutar en Windows 11

Requisitos: **Eclipse Temurin JDK 21** y **Apache Maven** (mvn.cmd en PATH).

Para **probar todo y generar un ZIP de retroalimentación**, ejecuta el archivo:

    scripts\diagnostico.bat

El script comprueba Java y Maven, construye y prueba el proyecto, abre CodeTrace y, cuando cierras la aplicación, genera el ZIP y lo selecciona en el Explorador de archivos. Captura únicamente la ventana de CodeTrace cuando Windows lo permita. Lee [Pruebas en Windows](scripts/README.md) para los detalles y las opciones.

Para trabajar normalmente desde IntelliJ IDEA: importar pom.xml como Maven, seleccionar el SDK **Eclipse Temurin 21**, configurar Maven Toolchains y ejecutar mvn javafx:run. El pom.xml exige **Temurin 21** como toolchain; Maven puede usar ~/.m2/toolchains.xml o un archivo específico con la opción -t.

## Vistas multimedia y distribución portable (en validación)

Al abrir imágenes, audio y vídeo, CodeTrace muestra una pestaña **solo de lectura**. PNG/JPEG/GIF/BMP se visualizan directamente, y SVG seguro y WebP se presentan mediante WebView restringido. Los README Markdown admiten imágenes locales, incluidas algunas etiquetas HTML de imagen, tablas y emojis, sin modificar los originales.

Para audio/vídeo, primero se intenta JavaFX. En formatos como MKV, WebM, AVI, FLAC y OGG, o si JavaFX no reconoce los códecs, hay una conversión **temporal y local** con FFmpeg. La conversión no se guarda dentro del proyecto, se ejecuta fuera del hilo gráfico y se cancela al cerrar la pestaña. FFmpeg puede proporcionarse mediante la propiedad Java `codetrace.ffmpeg`, la carpeta de herramientas de la aplicación, la carpeta local del usuario o `PATH`. **No se distribuye el binario de FFmpeg por defecto**: su redistribución exige cumplir la licencia del build elegido.

En Windows, `scripts\package-windows.ps1` prepara un ZIP portable que incorpora el runtime Java con jpackage; `-Installer` solicita un instalador EXE si existe WiX. El parámetro `-IncludeFfmpeg` integra una copia facilitada por el distribuidor, junto con sus avisos de licencia, después de revisar obligaciones de distribución. El empaquetado automatizado y los códecs todavía requieren aceptación en Windows real. La matriz de pruebas está en [Aceptación de Windows](docs/QA-WINDOWS.md).

## Implementado (versión de desarrollo 0.3)

- Referencia de solo lectura, árbol con creación/copia/movimiento/eliminación **virtual**.
- **Plantilla inmutable y selección por línea:** al hacer clic en una fila se resalta completa y el cursor pasa a su inicio. Escribir solo marca la posición original con verde (correcto) o rojo (incorrecto, subrayado), sin sustituir caracteres ni desplazar la referencia. El documento se muestra con líneas virtualizadas.
- **Control horizontal del tamaño tipográfico** en la barra inferior (10 a 36 pt, rueda del ratón y botones −/+), sincronizado con Preferencias.
- Opacidad de caracteres pendientes ajustable con un Slider horizontal en Preferencias → Editor (20–100 %); los caracteres correctos o incorrectos permanecen completamente visibles.
- Persistencia binaria en sesiones *.ctrace, progreso y hashes SHA-256.
- **Explorador con tres estados vectoriales**: círculo gris y nombre en cursiva si falta recrearlo; visto ámbar si ya existe virtualmente pero no terminó; visto verde si todos sus caracteres están correctamente transcritos. Botones de expandir/contraer y recuentos de archivos recursivos.
- **Reconstrucción explícita desde el menú contextual**: sobre una carpeta pendiente, «Crear esta carpeta»; sobre una carpeta creada, «Crear subcarpeta...» y «Crear archivo...»; sobre un archivo pendiente, «Crear este archivo». Los nombres nuevos deben coincidir exactamente con la referencia; no se crean archivos reales. La carpeta padre debe existir virtualmente antes que sus descendientes. El filtro de progreso por carpeta permanece en el menú.
- **Doble clic sobre cualquier archivo de texto compatible**: se abre inmediatamente como referencia de solo lectura. Para mecanografiarlo, primero debes recrear explícitamente el archivo y sus carpetas antecesoras en el explorador virtual. Si intentas escribir antes, aparece un cuadro de diálogo; nunca se crea nada automáticamente.
- **Progreso configurable por categorías** desde el botón **Filtro** de la barra inferior. Solo el código fuente cuenta inicialmente; Markdown, configuración y otros textos son optativos. El porcentaje muestra tres decimales y solo contabiliza caracteres correctos, nunca los imports omitidos.
- Iconografía transversal de carpetas y lenguajes con Ikonli/Devicons y Feather; controles desplegables con el mismo estilo rectangular.
- Resaltado inicial para Java, Python, JavaScript/TypeScript, C, C++, Go, Ruby, C#, PowerShell, Shell (.sh/.bash/.zsh), CMD (.bat/.cmd) y Markdown.
- **Modo Turbo opcional**: omite signos de puntuación consecutivos al mecanografiar. Las omisiones no cuentan como aciertos ni modifican la referencia.
- **Biblioteca de práctica interna**: Práctica → Biblioteca de práctica. Incluye 44 plantillas de código cortas en 12 packs (Java 21, TypeScript, JavaScript, Python, C, C++, C#, Go, Ruby, PowerShell, Shell y CMD). Java y TypeScript incorporan además estructuras de datos nativas, algoritmos y patrones de diseño. Funciona offline, no requiere descargar un repositorio ni un compilador para transcribir.
- **Consola plegable y redimensionable** desde Ver → Consola, Alt+F12 o la toolbar. Arrastra el divisor horizontal para ampliar/reducir la salida, como ya haces con el explorador. Permite ejecutar comandos preconfigurados, ver su salida y detenerlos.
- **Comandos por proyecto** desde Herramientas → Preferencias → Comandos: múltiples perfiles con nombre, intérprete, instrucción y directorio relativo de trabajo.
- Vista Markdown alternable entre fuente y renderizado en una misma pestaña.
- Preferencias rediseñadas con navegación lateral Editor/Ventana/Sonido, vista previa de tipografía, pantalla completa y modo concentración.
- Scrollbars verticales y horizontales con estilo compartido, compacto y rectangular en toda la aplicación.
- Galería compacta **Símbolos**: copia llaves, corchetes, arroba y más; informa la combinación de teclado y devuelve el foco a la transcripción. **Ctrl+V** pega exactamente un carácter en la posición actual sin modificar la referencia. Copiar por sí solo no aumenta estadísticas; el pegado correcto sí cuenta.
- Dos sonidos opcionales de escritura (acierto/error) suministrados por el usuario. La carga convierte y amplifica los WAV originalmente muy silenciosos. Se activan únicamente desde **Herramientas → Preferencias → Sonido**, donde también se pueden probar. Desactivados de manera predeterminada.
- Pruebas JUnit, CI con Temurin 21 y prueba de inicio JavaFX en pantalla virtual.

### Todavía pendiente de pulir

Selección de rangos y deshacer avanzados, alineación flexible de errores, conjunto definitivo de iconos de archivos, consola integrada y pruebas manuales completas de UX con teclado latinoamericano. Los archivos de texto UTF-8 grandes o binarios no se transcriben en esta versión.

## Organización

Los paquetes nacen de necesidades del producto: referencia, espacio virtual, transcripción, sesiones, lenguajes, interfaz y aplicación. No se introducen patrones de diseño sin necesidad.

- [Contrato de producto](docs/PRODUCTO.md)
- [Arquitectura](docs/ARQUITECTURA.md)
- [Diagnóstico de Windows](scripts/README.md)

Paquete base: com.mmdev.codetrace. Java 21 / JavaFX 21 / Maven.

La licencia todavía no ha sido decidida.

## Consola y comandos externos

Abre una carpeta de referencia, luego entra a **Herramientas → Preferencias → Comandos**.
Define varios perfiles si lo necesitas: por ejemplo `Compilar`, `Probar` y `Ejecutar`.
Cada uno guarda intérprete (CMD, PowerShell o sh), texto del comando y la
subcarpeta de trabajo relativa al proyecto (`.` por defecto). Guarda el perfil
y pulsa **Aplicar**. Los perfiles se almacenan fuera del repositorio, bajo
`~/.codetrace/commands`, en formato binario propio.

Abre **Ver → Consola** (Alt+F12), selecciona el perfil y pulsa **Ejecutar**.
No se ejecuta nada automáticamente. Puedes plegar la consola o detener
un proceso en curso.

**Atención:** los comandos se ejecutan en la **carpeta real de referencia**
y pueden modificar archivos reales. El código que escribiste en la
reconstrucción virtual no se materializa ni se utiliza para ejecutarlos.
Trabaja con una copia del repositorio si vas a probar comandos destructivos
o de despliegue. La ejecución no influye en el porcentaje de mecanografía.

## Biblioteca integrada

Abre **Práctica → Biblioteca de práctica...**, selecciona un lenguaje y pulsa
**Abrir práctica**. CodeTrace cargará el pack y abrirá el primer archivo. Puedes
seguir con los demás desde el árbol de referencia; la creación virtual, la
opacidad, el modo Turbo, los filtros y las estadísticas funcionan igual que en
los repositorios externos. El contenido original siempre permanece inmutable.

Las plantillas se empaquetan dentro del JAR y, al abrirse, se materializan
de forma determinista en `~/.codetrace/practice-library/v1/`. No hay conexión,
plugin o descarga de terceros. **Guarda tu progreso en una sesión `.ctrace`**
para continuarlo después. Los packs se basan en ejemplos originales de
mecanografía y no en ejercicios evaluados automáticamente.

La biblioteca es una fuente de texto de referencia: no compila ni ejecuta
los ejemplos y no necesita compilar para que puedas practicar.

## Creación explícita y consola

El código de referencia puede inspeccionarse sin recrear su estructura. Si
intentas escribir, borrar, pegar un símbolo o saltar imports en un archivo que
todavía no existe virtualmente, CodeTrace muestra un mensaje y deja la
sesión y el proyecto virtual sin cambios. Para habilitar la práctica
crea primero las carpetas desde el árbol y después el archivo de nombre idéntico.
La creación no altera la copia real ni descarta la pestaña abierta.

La consola plegable comienza sin salida. Si todavía no hay perfiles de comandos,
muestra un estado vacío con el botón **Configurar comandos...**, que abre la
sección correspondiente de Preferencias. Los comandos jamás se ejecutan
automáticamente; al seleccionar uno y pulsar Ejecutar se muestra su salida.

### Interacción de escritorio y reconstrucción

- Las filas del árbol mantienen el ícono de carpeta o de lenguaje separado del nombre, del estado de creación y del contador. Un nombre largo nunca sustituye el ícono por puntos suspensivos.
- Los archivos creados virtualmente son hitos: no se ofrecen acciones de eliminar, copiar, cortar o renombrar desde el explorador. El menú contextual de los archivos ofrece **Mostrar en el Explorador**, que revela el archivo original en Windows y su carpeta contenedora en Linux si no puede seleccionarlo.
- Al abrir o seleccionar una pestaña de código, la superficie de mecanografía recupera el foco después del cambio de pestaña. Al crear explícitamente un archivo ya abierto, también queda preparado para escribir sin otro clic.
- La opacidad de la plantilla es un **Slider horizontal** en Preferencias → Editor; los caracteres ya evaluados conservan su visibilidad.

## Modalidades de práctica y estados del explorador

**Práctica → Modalidad del proyecto** permite elegir entre:

- **Reconstrucción manual** (predeterminada para repositorios externos): recreas carpetas y archivos desde el árbol antes de escribir.
- **Transcripción directa** (predeterminada para la biblioteca interna): basta abrir el archivo y empezar a teclear. Al primer carácter se crean en memoria las carpetas y el archivo correspondientes; abrir la plantilla por sí solo no crea nada.

La modalidad se recuerda por carpeta y se incluye en las nuevas sesiones
`.ctrace` (formato v2). Las sesiones anteriores v1 siguen siendo compatibles
y se restauran en reconstrucción manual. Cambiar de modalidad no altera el
código original ni borra los avances. Turbo es independiente de la modalidad.

Los íconos del explorador ahora distinguen **pendiente** (círculo gris y
nombre en cursiva), **creado** (visto ámbar) y **terminado** (visto verde).
Terminado significa todos los caracteres correctos, sin errores ni omisiones.
Un archivo excluido del porcentaje global puede terminarse individualmente;
no impide completar las carpetas según el filtro de progreso.

El logo de CodeTrace también se utiliza en Preferencias, los cuadros de
creación y la Biblioteca de práctica, manteniendo el estilo de escritorio.

## Finalización y archivos no practicables

- Un archivo puede terminarse en modo Turbo o con imports omitidos. Los caracteres
  saltados explícitamente cuentan como *resueltos* para cerrar el ejercicio,
  pero nunca se suman a los caracteres correctamente tecleados ni al porcentaje.
  La última línea terminada en salto de línea no exige pulsar Enter adicional.
  Una posición pendiente o incorrecta sí mantiene el ejercicio incompleto.
- El indicador verde aparece al terminar el ejercicio, incluso si el porcentaje
  global del repositorio sigue siendo inferior al 100 % por las omisiones.
- Solo los lenguajes reconocidos por el resaltador y Markdown son elegibles
  para mecanografía. Binarios, configuraciones y demás archivos permanecen
  visibles en el árbol, pero no requieren creación virtual ni mecanografía.
  Un archivo reconocido pero vacío no tiene ejercicio pendiente.
- Las carpetas solo ofrecen crear subcarpetas o archivos cuando existe algún
  descendiente inmediato pendiente y practicable. Las vacías o sin código
  practicable no presentan acciones de creación.
- **Acerca de** conserva el texto explicativo y muestra una versión ampliable
  y centrada del logotipo, distinta del icono pequeño de la barra de título.

### Markdown: fuente y vista renderizada

En las pestañas `.md` y `.markdown`, el botón con icono **Renderizado** muestra
una vista de lectura sin modificar la plantilla; **Fuente** vuelve al editor.
La vista reconoce emojis Unicode y alias comunes de GitHub (`:rocket:`,
`:white_check_mark:`, etc.), conserva el texto literal de los bloques de código
y muestra imágenes PNG, JPEG, GIF, BMP, WebP y SVG seguros incluidos en la
referencia, utilizando rutas relativas desde el documento. Las imágenes se
incorporan solo para lectura, con límites de tamaño; no se realizan descargas
ni se permite acceder fuera del proyecto. La vista se prepara en segundo plano.

## Roadmap y criterios de aceptación

La [matriz de producto y cierre](docs/ROADMAP.md) distingue funciones verificadas, soporte parcial y trabajo pendiente.

