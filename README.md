# CodeTrace

**Transcripción de código y reconstrucción virtual de proyectos de software.**

CodeTrace es una herramienta de escritorio para practicar la creación de estructuras y la escritura de código sobre una referencia existente. No es un curso, un asistente de IA ni un IDE completo.

## Ejecutar en Windows 11

Requisitos: **Eclipse Temurin JDK 21** y **Apache Maven** (mvn.cmd en PATH).

Para **probar todo y generar un ZIP de retroalimentación**, ejecuta el archivo:

    scripts\diagnostico.bat

El script comprueba Java y Maven, construye y prueba el proyecto, abre CodeTrace y, cuando cierras la aplicación, genera el ZIP y lo selecciona en el Explorador de archivos. Captura únicamente la ventana de CodeTrace cuando Windows lo permita. Lee [Pruebas en Windows](scripts/README.md) para los detalles y las opciones.

Para trabajar normalmente desde IntelliJ IDEA: importar pom.xml como Maven, seleccionar el SDK **Eclipse Temurin 21**, configurar Maven Toolchains y ejecutar mvn javafx:run. El pom.xml exige **Temurin 21** como toolchain; Maven puede usar ~/.m2/toolchains.xml o un archivo específico con la opción -t.

## Implementado (versión de desarrollo 0.2)

- Referencia de solo lectura, árbol con creación/copia/movimiento/eliminación **virtual**.
- Editor de transcripción sobre plantilla; aciertos verdes, errores rojos y avance sin bloqueo.
- Persistencia binaria en sesiones *.ctrace, progreso y hashes SHA-256.
- Resaltado inicial por adaptadores de Java, Python, TypeScript, C/C++ y Markdown.
- Vista Markdown alternable entre fuente y renderizado en una misma pestaña.
- Preferencias de fuente, pantalla completa y modo concentración.
- Pruebas JUnit, CI con Temurin 21 y prueba de inicio JavaFX en pantalla virtual.

### Todavía pendiente de pulir

Selección/deshacer avanzados, alineación flexible de errores, recursos gráficos definitivos, consola integrada y pruebas manuales completas de UX con teclado latinoamericano. Los archivos de texto UTF-8 grandes o binarios no se transcriben en esta versión.

## Organización

Los paquetes nacen de necesidades del producto: referencia, espacio virtual, transcripción, sesiones, lenguajes, interfaz y aplicación. No se introducen patrones de diseño sin necesidad.

- [Contrato de producto](docs/PRODUCTO.md)
- [Arquitectura](docs/ARQUITECTURA.md)
- [Diagnóstico de Windows](scripts/README.md)

Paquete base: com.mmdev.codetrace. Java 21 / JavaFX 21 / Maven.

La licencia todavía no ha sido decidida.
