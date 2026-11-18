# CodeTrace

**Transcripción de código y reconstrucción virtual de proyectos de software.**

CodeTrace es una herramienta de escritorio para practicar la creación de estructuras y la escritura de código sobre una referencia existente. No es un curso, un asistente de IA ni un IDE completo.

## Ejecutar en Windows 11

Requisitos: **Eclipse Temurin JDK 21** y **Apache Maven** (mvn.cmd en PATH).

Para **probar todo y generar un ZIP de retroalimentación**, ejecuta el archivo:

    scripts\diagnostico.bat

El script comprueba Java y Maven, construye y prueba el proyecto, abre CodeTrace y, cuando cierras la aplicación, genera el ZIP y lo selecciona en el Explorador de archivos. Captura únicamente la ventana de CodeTrace cuando Windows lo permita. Lee [Pruebas en Windows](scripts/README.md) para los detalles y las opciones.

Para trabajar normalmente desde IntelliJ IDEA: importar pom.xml como Maven, seleccionar el SDK **Eclipse Temurin 21**, configurar Maven Toolchains y ejecutar mvn javafx:run. El pom.xml exige **Temurin 21** como toolchain; Maven puede usar ~/.m2/toolchains.xml o un archivo específico con la opción -t.

## Implementado (versión de desarrollo 0.3)

- Referencia de solo lectura, árbol con creación/copia/movimiento/eliminación **virtual**.
- **Plantilla inmutable y selección por línea:** al hacer clic en una fila se resalta completa y el cursor pasa a su inicio. Escribir solo marca la posición original con verde (correcto) o rojo (incorrecto, subrayado), sin sustituir caracteres ni desplazar la referencia. El documento se muestra con líneas virtualizadas.
- **Control horizontal del tamaño tipográfico** en la barra inferior (10 a 36 pt, rueda del ratón y botones −/+), sincronizado con Preferencias.
- Opacidad de caracteres pendientes ajustable con un Spinner en Preferencias → Editor (20–100 %); los caracteres correctos o incorrectos permanecen completamente visibles.
- Persistencia binaria en sesiones *.ctrace, progreso y hashes SHA-256.
- **Explorador con marcadores de estado**: círculo gris si falta recrearlo, visto verde cuando existe en el proyecto virtual. Botones para contraer/expandir carpetas. Menú contextual sobre carpetas para medir el progreso de esa rama o de todo el proyecto.
- **Doble clic sobre cualquier archivo de texto compatible**: la plantilla se abre directamente. Al escribir, se crea el recorrido virtual necesario, sin alterar la referencia ni impedir la creación manual desde el explorador.
- **Progreso configurable por categorías** desde el botón **Filtro** de la barra inferior. Solo el código fuente cuenta inicialmente; Markdown, configuración y otros textos son optativos. El porcentaje muestra tres decimales y solo contabiliza caracteres correctos, nunca los imports omitidos.
- Iconografía transversal de carpetas y lenguajes con Ikonli/Devicons y Feather; controles desplegables con el mismo estilo rectangular.
- Resaltado inicial para Java, Python, JavaScript/TypeScript, C, C++, Go, Ruby, C#, PowerShell, Shell (.sh/.bash/.zsh), CMD (.bat/.cmd) y Markdown.
- **Modo Turbo opcional**: omite signos de puntuación consecutivos al mecanografiar. Las omisiones no cuentan como aciertos ni modifican la referencia.
- **Consola plegable** desde Ver → Consola, Alt+F12 o la toolbar. Permite ejecutar explícitamente comandos preconfigurados, leer la salida y detenerlos.
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
