# CodeTrace

**Reconstrucción manual de proyectos de software, como práctica de escritura y organización.**

CodeTrace es una aplicación de escritorio para practicar la reconstrucción de proyectos existentes. El objetivo es adquirir familiaridad, soltura y confianza con el trabajo cotidiano del programador, sin convertir la experiencia en un curso ni en un IDE generalista.

## Estado actual: bootstrap 0.1.0-SNAPSHOT

**Ya implementado:** ventana JavaFX, menús y toolbar básicos, apertura de una carpeta de referencia, explorador perezoso, vista previa UTF-8 en solo lectura, cálculo de SHA-256 al abrir archivos y pruebas unitarias para lectura y hashes.

**Todavía pendiente:** creación virtual de archivos y carpetas, transcripción directamente sobre la plantilla, progreso, sesiones `.ctrace`, comparación de referencias actualizadas, Markdown renderizado y consola integrada. **La vista previa inicial no permite practicar mecanografía.**

## Entorno de desarrollo

- **Eclipse Temurin JDK 21** como Project SDK / `JAVA_HOME`.
- **JavaFX 21.0.13**, obtenido con Maven.
- **Maven** (compatible con Java 21).
- Paquete principal `com.mmdev.codetrace`.
- Windows 11 como primera plataforma objetivo.

Abrir el `pom.xml` como proyecto Maven en IntelliJ IDEA y seleccionar **Eclipse Temurin 21** como SDK. En una terminal con Maven:

```powershell
java -version
mvn -version
mvn clean verify
mvn javafx:run
```

El workflow de GitHub Actions también utiliza Temurin 21. **El arranque gráfico en Windows aún no ha sido validado manualmente.**

## Contrato de producto para los próximos hitos

- La referencia es una carpeta física de solo lectura, identificada por rutas relativas y SHA-256 por archivo.
- La reconstrucción es **virtual**, persistida en una sesión propia; no se duplica físicamente el repositorio.
- Se escribe directamente **sobre** la plantilla. Verde para aciertos, rojo para errores y avance sin bloqueo.
- Sin autocierre de llaves, autocompletado, edición libre ni corrección automática del programa.
- Las estadísticas positivas contabilizan únicamente caracteres introducidos correctamente, sin duplicar correcciones ni omisiones voluntarias.
- GUI clara y sobria de escritorio: azul acero, gris y verde, controles rectangulares, editor amplio, paneles secundarios plegables y barra global inferior.
- Adaptadores internos por lenguaje para resaltado y acciones concretas, sin duplicar el motor de mecanografía.

## Documentación

- [Contrato de producto](docs/PRODUCTO.md)
- [Arquitectura y estado de implementación](docs/ARQUITECTURA.md)

No se ha escogido una licencia. Hasta que exista un archivo `LICENSE`, no se debe suponer que el código se distribuye bajo una licencia de código abierto.
