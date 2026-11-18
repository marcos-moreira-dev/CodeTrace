# Pruebas en Windows

Este paquete se ejecuta **localmente** en Windows 11. No manda archivos a ChatGPT ni a ningún servicio externo. Maven descarga dependencias desde los repositorios que tengas configurados.

## Requisitos

- **Eclipse Temurin JDK 21** (completo, no solo JRE).
- **Apache Maven** con mvn.cmd disponible en PATH.
- Windows PowerShell 5.1 (incluido en Windows 11).

No es necesario editar los toolchains.xml globales. El diagnóstico comprueba el archivo release del JDK seleccionado y prepara un toolchains.xml **solo para esa ejecución**, que se pasa a Maven con -t.

## Un clic

Desde la carpeta descomprimida:

1. Ejecuta scripts\diagnostico.bat.
2. El programa busca Temurin 21 y Maven, guarda sus versiones y ejecuta **mvn clean verify**.
3. Si pasan las pruebas, crea automáticamente `target\qa-preview` con ejemplos de Markdown, imágenes, SVG y multimedia (si FFmpeg está disponible), e inicia CodeTrace con **mvn javafx:run**. Abre esa carpeta de QA desde Archivo → Abrir proyecto para comprobar las vistas.
4. Puedes probar la aplicación; ciérrala cuando quieras finalizar.
5. El script crea un ZIP en reports\ y abre el Explorador de Windows seleccionándolo.

El ZIP incluye resultado.txt, registros de Maven, informes JUnit y una captura de la ventana CodeTrace **si Windows puede obtenerla**. La captura no es obligatoria para considerar exitosas las pruebas; algunos entornos de escritorio remoto o ventanas minimizadas impiden obtenerla.

## Opciones

- scripts\diagnostico.bat : pruebas, ejecutar CodeTrace, intentar captura, generar ZIP.
- scripts\diagnostico.bat -NoApp : solo compilación/pruebas y ZIP.
- scripts\diagnostico.bat -NoScreenshot : no intentar captura.
- scripts\diagnostico.bat -NoExplorer : no abrir el Explorador al finalizar.
- En CI se utilizan -NoApp -NoExplorer.

## Privacidad del ZIP

El diagnóstico **no adjunta los proyectos fuente**, las sesiones *.ctrace ni otros archivos personales. Sin embargo, los logs y versiones pueden revelar rutas de usuario y la captura puede mostrar lo que aparece dentro o encima de la ventana. **Revisa el ZIP antes de subirlo al chat**.

Si faltan herramientas o falla la compilación, el informe se genera de todos modos, con los datos disponibles y un mensaje de error. El código de salida será distinto de cero en ese caso.
