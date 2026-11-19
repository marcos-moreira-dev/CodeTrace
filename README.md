# CodeTrace

**Transcripción de código y reconstrucción virtual de proyectos de software.**

CodeTrace es una herramienta de escritorio para practicar la creación de estructuras y la escritura de código sobre una referencia existente. No es un curso, un asistente de IA ni un IDE completo.

## Ejecutar en Windows 11

Requisitos: **Eclipse Temurin JDK 21** y **Apache Maven** (mvn.cmd en PATH).

Para **probar todo y generar un ZIP de retroalimentación**, ejecuta el archivo:

    scripts\diagnostico.bat

El script comprueba Java y Maven, construye y prueba el proyecto, prepara automáticamente `target\qa-preview` para probar Markdown, imágenes y medios, abre CodeTrace y, cuando cierras la aplicación, genera el ZIP y lo selecciona en el Explorador de archivos. Captura únicamente la ventana de CodeTrace cuando Windows lo permita. Lee [Pruebas en Windows](scripts/README.md) para los detalles y las opciones.

Para trabajar normalmente desde IntelliJ IDEA: importar pom.xml como Maven, seleccionar el SDK **Eclipse Temurin 21**, configurar Maven Toolchains y ejecutar mvn javafx:run. El pom.xml exige **Temurin 21** como toolchain; Maven puede usar ~/.m2/toolchains.xml o un archivo específico con la opción -t.

## Audio y flujo de creación automática (en validación)

Las pestañas de audio tienen controles habituales de **reproducir/pausar** (icono dinámico),
**detener**, avance, tiempo transcurrido y volumen, además de un **espectro en tiempo real**
de JavaFX. No es una forma de onda generada a partir del archivo ni requiere FFmpeg.
El zoom violeta queda reservado para **imágenes y vídeos**, no para MP3/WAV.

En **Reglas de práctica** hay una casilla `Autocrear archivos y carpetas`. Usa la
modalidad por proyecto ya existente: marcada equivale a **Transcripción directa**,
y crea virtualmente los antecesores y el archivo **al comenzar a teclear**;
desmarcada requiere creación manual. Ninguna modalidad escribe sobre la referencia.

Los archivos `tecla-correcta.wav.gz` y `tecla-error.wav.gz` son **WAV comprimidos
con GZIP** para empaquetar sonidos pequeños. `TypingSounds` los descomprime y normaliza
en una copia temporal antes de reproducirlos, conservando los originales intactos.

La ventana Buscar usa el icono de CodeTrace y filas de resultados de varias líneas
con el mismo componente visual que los comandos guardados.

## Lectura con resaltado y mapa de dependencias (iteración de evaluación)

Los archivos legibles que no son ejercicios de transcripción (CSS, JSON, XML,
YAML, Properties) ahora tienen un **visor coloreado de solo lectura** con
líneas virtualizadas, números y búsqueda. Los lenguajes de programación
reconocen en la medida de lo posible palabras clave, **tipos**, llamadas,
miembros, comentarios (verde) y literales. Los colores son compartidos con el
editor de transcripción; no se editan los originales.

Selecciona una carpeta en el árbol y abre **Herramientas → Mapa de dependencias
de carpeta**. CodeTrace analiza imports locales en segundo plano, abre una
pestaña con nodos y flechas y permite doble clic sobre los archivos relacionados.
Cuando no hay dependencias internas resolubles presenta un aviso. No presume que
todos los imports externos correspondan a archivos locales. Soporta resolución
estática básica para Java, Kotlin, Python, JS/TS, C/C++, C#, Go, Ruby,
PowerShell, shell y CMD; las importaciones dinámicas o de frameworks no son
un análisis semántico completo.

La barra de menú usa gris pizarra moderado y la barra de herramientas un
neutral cálido para diferenciarlas sin imitar interfaces antiguas exageradas.
Las versiones y comandos de los 108 ejemplos se describen en
[Versiones y CLI](docs/LENGUAJES-Y-COMANDOS.md).

## Navegación y búsqueda (versión en evaluación)

El explorador incluye una búsqueda por nombres con conservación de las carpetas
antecesoras. Las casillas "Solo" y "Excluir del progreso" actúan sobre la carpeta
seleccionada: la primera define el ámbito temporal; la segunda guarda una
exclusión persistente del cálculo, pero nunca impide explorar ni practicar.

Al seleccionar una pestaña de imagen o vídeo, el control inferior de fuente
se sustituye por el zoom multimedia (25–400 %, Ctrl+rueda, arrastrar para
panear y doble clic para restablecer). El menú "Pestañas" permite cerrar la
actual, las demás o todas; también existe menú contextual en cada pestaña.

Herramientas > Buscar en archivo (Ctrl+F) y Buscar en proyecto (Ctrl+Shift+F)
presenta coincidencias con ruta, línea y columna. F3 y Shift+F3 navegan.
No existen acciones de reemplazo: el original es inmutable y buscar nunca
mueve el cursor de mecanografía. Los archivos de configuración legibles pueden
abrirse en una pestaña de consulta exclusivamente de solo lectura.
El modo concentración utiliza F10 para evitar conflicto con la búsqueda global.

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
- **Biblioteca de práctica interna**: Práctica → Biblioteca de práctica. Incluye 108 ejercicios en 13 packs (Java 21, Kotlin 2.2, TypeScript, JavaScript, Python, C, C++, C#, Go, Ruby, PowerShell, Shell y CMD). Java, Kotlin y TypeScript cuentan con 16 ejercicios cada uno; los demás tienen seis, con fundamentos, estructuras, algoritmos y prácticas idiomáticas. Cada archivo indica una versión de referencia y cómo compilarlo, comprobarlo o ejecutarlo. Funciona offline, no requiere descargar un repositorio ni un compilador para transcribir.
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


## Ergonomía de consultas y distribución del grafo

El zoom tipográfico de la barra inferior controla **todos** los archivos de
texto, tanto ejercicios como consultas de solo lectura: CSS, XML, TXT,
JSON, YAML, Properties y similares. Permanece expresado en puntos (pt).

El **mapa de dependencias** reutiliza la ubicación del zoom contextual
multimedia, pero con el icono de relaciones y unidades porcentuales; ya
no duplica un segundo deslizador dentro de la vista. Los nodos están más
separados y pueden arrastrarse. Sus posiciones se guardan en la sesión
`.ctrace` (formato v3, compatible con lectura de v1/v2), nunca en el
repositorio original. Cuando cambia el contenido del proyecto analizado,
CodeTrace vuelve a la distribución automática y conserva las transcripciones.

La opción **Autocrear archivos y carpetas** reconstruye por lotes todos los
nodos de la referencia, salvo directorios generados y cachés. Un archivo
creado aparece en amarillo hasta terminar correctamente su transcripción,
cuando pasa a verde. Los archivos no ejercitables se siguen mostrando
como no aplicables. Las casillas de Reglas de práctica continúan decidiendo
únicamente qué archivos puntúan en el porcentaje de progreso.

El selector de pestañas desbordadas se presenta con una pequeña flecha
en un botón rectangular, en lugar del círculo predeterminado de JavaFX.

## Ámbito «Solo carpeta» del explorador

Al activar `Solo` para una carpeta, su subárbol y las carpetas
antecesoras permanecen plenamente visibles. Las ramas hermanas y los
archivos fuera del ámbito aparecen **atenuados**, pero nunca deshabilitados:
siguen admitiendo navegación, consulta, doble clic y menú contextual.
Seleccionar otra carpeta cambia inmediatamente el ámbito; seleccionar
la raíz o desmarcar `Solo` devuelve el progreso a todo el proyecto.

Este enfoque **solo afecta a la visualización y al cálculo de progreso**;
no altera la creación virtual, los estados amarillo/verde ni las reglas
de exclusión guardadas. Las ramas atenuadas muestran un tooltip que explica
por qué no se contabilizan.

## Distribuciones de grafo y acceso contextual desde el árbol

El menú contextual de una carpeta permite **Colapsar subcarpetas** cuando
contiene carpetas descendientes, y **Mapa de dependencias...** para crear el
mapa de esa carpeta exacta, incluso si otra entrada estaba seleccionada.

Las dos distribuciones **Organizado** (por conexiones, con reserva
de espacio para las etiquetas) y **Cuadrícula** (disposición clásica) se
seleccionan con radios exclusivos en **Herramientas → Preferencias → Grafo**,
no en una barra permanente sobre el mapa. Organizado es el valor inicial.
Es un algoritmo de presentación heurístico, no un analizador semántico nuevo.

El lienzo ofrece más superficie y puede ampliarse **al soltar** un nodo
cerca del borde, sin reiniciar el desplazamiento. Las posiciones manuales y el
modo de distribución se guardan en la sesión **.ctrace v4**; las sesiones
v1/v2/v3 se pueden leer, y v3 recupera Cuadrícula como modo inicial.

Al seleccionar o arrastrar un nodo, la **ruta completa** aparece a la
izquierda de la barra de estado de la aplicación. Los contadores de relaciones
se muestran también allí, sin encabezados ni pies fijos dentro del mapa.

## Mapa de dependencias: comportamiento simplificado (19-nov)

El **modo inicial es Organizado**. En lugar de colocar todos los archivos
en una capa comprimida, calcula posiciones reproducibles a partir de relaciones
internas y después asigna a cada nombre de archivo una celda libre; así evita
que sus etiquetas se escriban unas encima de otras. La alternativa Cuadrícula
se configura en **Herramientas → Preferencias → Grafo** mediante radios
exclusivos y estilizados. El modo seleccionado se guarda en Preferencias y,
si existe sesión, la configuración de esa sesión conserva su distribución.

Se quitaron el encabezado con contadores y el pie de instrucciones dentro del
mapa. Al abrirlo, la barra de estado muestra el ámbito; al seleccionar un
nodo, la **ruta completa va a la izquierda**, y el número de aristas entrantes
y salientes aparece junto a los demás datos de estado. Los totales del mapa
están en el tooltip de esa zona, no como texto permanente en el workspace.
**Restablecer distribución** está disponible con clic derecho sobre el mapa.

El arrastre ya **no utiliza el desplazamiento automático de ScrollPane**:
sobre un nodo mueve solo ese nodo; sobre el fondo mueve la vista.
El lienzo no cambia de tamaño durante el arrastre y puede crecer después de
soltar, preservando el desplazamiento actual. Las etiquetas usan fondo claro
para seguir siendo legibles aun cuando una arista pase por detrás.

El mapa nunca reescribe los imports reales ni cambia el progreso de mecanografía.
