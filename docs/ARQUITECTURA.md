# CodeTrace: arquitectura inicial

## Tecnología
- Eclipse Temurin JDK 21, fuente/código compilado para Java 21.
- JavaFX 21.0.13 con Maven; paquete \`com.mmdev.codetrace\`.
- Pruebas con JUnit 5; CI GitHub Actions configura \`distribution: temurin\`.

## Implementado en el bootstrap
- Ventana JavaFX con menús y toolbar funcionales.
- Elegir una carpeta física de referencia, explorarla de forma perezosa y actualizar el árbol.
- Vista previa de UTF-8 en solo lectura y SHA-256 por archivo abierto.
- Exclusión de binarios y vista acotada a 2 MiB.
- Estilos compartidos de GUI, diálogos de información y pruebas de lectura/hash.

El editor mostrado **aún no es un editor de transcripción**. La creación de archivos virtuales, guardado \`.ctrace\`, estadísticas, Markdown renderizado y consola siguen pendientes.

## Próximos hitos
1. Árbol virtual, operaciones y tests puros de dominio.
2. Motor de escritura sobre plantilla (Unicode confirmado, errores no bloqueantes).
3. Editor con coloreado correcto/incorrecto y saltos voluntarios.
4. Persistencia versionada \`.ctrace\`, detección de cambios, recuperación.
5. Paneles, estadísticas, Markdown y comandos externos.

## Desarrollo
Configurar el SDK de IntelliJ IDEA como **Eclipse Temurin 21** e importar \`pom.xml\` como proyecto Maven.

\`\`\`powershell
java -version
mvn -version
mvn clean verify
mvn javafx:run
\`\`\`

No se ha comprobado todavía el arranque gráfico en Windows. La verificación de Maven deberá ejecutarse en CI o en una máquina con Maven instalado.
