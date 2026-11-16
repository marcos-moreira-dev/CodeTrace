# CodeTrace

**Transcripción de código y reconstrucción virtual de proyectos de software.**

CodeTrace es una aplicación JavaFX para practicar manualmente la creación de estructuras de archivos y la escritura de código sobre una referencia existente. Busca favorecer soltura, familiaridad y confianza en las tareas cotidianas del desarrollo de software. No es un curso, un asistente de inteligencia artificial ni un compilador.

## Implementado — 0.2.0-SNAPSHOT

- Selección de carpeta de referencia **de solo lectura** y explorador con entradas pendientes.
- Creación, renombrado, copia, movimiento y eliminación de elementos **virtuales** mediante menú contextual (crear archivos en la carpeta padre y escribir su nombre).
- Transcripción directamente sobre la plantilla, con verde para aciertos y rojo para errores **sin bloquear el avance**; navegación de cursor, Retroceso y Supr.
- Soporte en el motor para caracteres Unicode compuestos, saltos de línea y omisiones voluntarias de imports Java.
- Guardar/abrir sesiones binarias **`.ctrace`**, con escrituras temporales y recuperación del proyecto virtual y la mecanografía. El código original no se modifica.
- Catálogo de referencias y cálculos de **SHA-256 por archivo**; progreso global calculado en segundo plano sobre los archivos de texto compatibles.
- Interfaz clara de escritorio: panel de proyecto, pestañas, área central amplia, pantalla completa, modo concentración, barra de progreso y preferencias persistentes de fuente y tamaño.
- Pruebas JUnit del dominio y GitHub Actions con **Eclipse Temurin 21**, compilación y prueba de inicio gráfico mediante Xvfb.

### Limitaciones de esta versión

Todavía falta el resaltado de sintaxis por lenguaje, el visor Markdown renderizado, la consola de comandos, los iconos definitivos, la gestión avanzada de selección/deshacer y una verificación visual manual en Windows 11. La transcripción actual es por posiciones de la plantilla, por lo que el algoritmo de alineación de errores aún puede mejorarse. Solo se admiten vistas de texto UTF-8 acotadas a 2 MiB; los binarios no se transcriben.

## Tecnología

- **JDK: Eclipse Temurin 21**, como SDK del proyecto.
- **JavaFX 21.0.13**.
- **Maven** y JUnit 5.
- Namespace: **`com.mmdev.codetrace`**.

En IntelliJ IDEA, importa `pom.xml` como Maven y establece Project SDK = **Eclipse Temurin 21**. Después ejecuta:

```powershell
java -version
mvn -version
mvn clean verify
mvn javafx:run
```

## Documentación

- [Contrato de producto](docs/PRODUCTO.md)
- [Arquitectura y estado técnico](docs/ARQUITECTURA.md)

La licencia está pendiente de escoger. No se debe asumir una licencia abierta sin un archivo `LICENSE`.
