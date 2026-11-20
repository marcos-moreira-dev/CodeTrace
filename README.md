<div align="center">

# CodeTrace

### Aprende código reconstruyéndolo. Explora proyectos sin alterar sus originales.

**Un espacio de práctica de mecanografía para código real, con explorador, lectura, progreso y mapa de dependencias.**

Java 21 · JavaFX · Windows 11 · 14 lenguajes en la biblioteca de práctica

[Qué puedes hacer](#qué-puedes-hacer) · [Capturas](#capturas-de-pantalla) · [Instalación](#instalación-y-primeros-pasos) · [Desarrollo](#desarrollo) · [Estado del proyecto](#estado-del-proyecto)

</div>

![CodeTrace mostrando una clase Java de práctica y el explorador virtual](docs/media/01-biblioteca-java.jpg)

> **Lee el código. Reconstruye su estructura. Escríbelo con precisión.** CodeTrace no pretende sustituir tu editor o tu IDE. Está pensado para estudiar la organización de programas y practicar su escritura, sin modificar los archivos que sirven de referencia.

## Qué puedes hacer

- **Practicar sobre una plantilla real.** Los caracteres de referencia permanecen visibles mientras escribes: los aciertos, errores y caracteres pendientes tienen estados diferenciados. Puedes seleccionar líneas, ajustar el tamaño de letra y usar un modo de concentración.
- **Reconstruir un proyecto de manera virtual.** Reproduce su estructura de carpetas y archivos, manualmente o mediante reglas automáticas, sin escribir encima de la referencia original.
- **Seguir el progreso.** Consulta el avance por archivo y proyecto; filtra carpetas y categorías, registra aciertos y velocidad, y guarda sesiones `.ctrace` para retomarlas más adelante.
- **Explorar proyectos existentes.** Navega el árbol de archivos, busca contenido y consulta código o archivos de configuración de solo lectura. La navegación está optimizada para proyectos grandes.
- **Comprender dependencias.** Visualiza relaciones estáticas entre archivos de una carpeta, acerca, desplaza y abre nodos desde el mapa. El análisis es aproximado: no sustituye a un compilador o analizador semántico.
- **Estudiar recursos complementarios.** Abre README con Markdown renderizado e imágenes locales; consulta imágenes, audio y vídeo compatibles, y utiliza una consola para ejecutar **comandos explícitos** sobre el proyecto real.

## Capturas de pantalla

### Aprende con una biblioteca organizada

![Biblioteca Java con fundamentos, estructuras de datos, algoritmos y patrones](docs/media/01-biblioteca-java.jpg)

La biblioteca integrada comprende **14 lenguajes y 813 archivos de referencia** en la candidata v0.3. Son *archivos*, no 813 ejercicios independientes. Sus contenidos cubren fundamentos, estructuras de datos, algoritmos y patrones de diseño; los ejemplos incluyen guías de consola y salidas esperadas, algunas todavía pendientes de validación cruzada en todos los entornos.

### Consulta documentación directamente dentro del proyecto

![README Markdown renderizado dentro de CodeTrace](docs/media/02-markdown.jpg)

### Descubre cómo se relacionan los archivos

![Mapa de dependencias de un proyecto Java con nodos y conexiones](docs/media/03-mapa-dependencias.jpg)

### Practica de forma visual y revisa tus errores

![Práctica de transcripción con indicadores verdes y rojos](docs/media/04-practica-tipografia.jpg)

### Ejecuta comandos de forma consciente

![Consola integrada mostrando el comando dir sobre una carpeta de referencia](docs/media/05-consola.jpg)

**Nota de seguridad:** la consola trabaja sobre los archivos **reales de la carpeta de referencia**, no sobre el espacio virtual. Ejecuta únicamente comandos que conozcas, preferiblemente en una copia de prueba.

## Instalación y primeros pasos

**Plataforma principal:** Windows 11.

Cuando exista una publicación validada, sus entregables serán:

- **Instalador `.exe`:** instalación normal y asociación prevista para sesiones `.ctrace`.
- **Archivo `.zip` portable:** descomprime y ejecuta `CodeTrace.exe` sin instalar un JDK por separado.

Por ahora, los binarios creados son **candidatos de prueba**, no una versión estable publicada. Consulta [las condiciones de cierre](docs/CIERRE-V0.3.md) antes de distribuirlos. No hay una release oficial disponible todavía.

Para probar el código fuente necesitas **Eclipse Temurin JDK 21**, **Maven** y un entorno gráfico de Windows:

```powershell
git clone https://github.com/marcos-moreira-dev/CodeTrace.git
cd CodeTrace
git switch feature/practice-curriculum-20261120
.\scripts\diagnostico.bat
```

El diagnóstico ejecuta las pruebas, abre la interfaz y produce un paquete de retroalimentación. Los scripts de compilación y empaquetado están documentados en [scripts/README.md](scripts/README.md).

## Desarrollo

**Stack:** Java 21, JavaFX 21, Maven, JUnit 5 y `jpackage` para Windows. El proyecto distingue los archivos fuente de referencia del estado de reconstrucción virtual y conserva sesiones de trabajo en archivos propios.

- [Guía de arquitectura y Javadoc](docs/GUIA-DE-ARQUITECTURA-CODETRACE.md)
- [Contrato de producto](docs/PRODUCTO.md)
- [Versiones y comandos de la biblioteca](docs/LENGUAJES-Y-COMANDOS.md)
- [Pruebas de aceptación Windows](docs/QA-WINDOWS.md)
- [Condiciones para publicación](docs/CIERRE-V0.3.md)

Para compilar y verificar, en el entorno configurado:

```powershell
mvn -B verify
```

Para generar un ZIP portable, y opcionalmente el instalador con las herramientas necesarias:

```powershell
.\scripts\package-windows.ps1 -Installer
```

**FFmpeg no se redistribuye por defecto.** Cuando está instalado y CodeTrace lo detecta, puede facilitar la vista previa local de formatos que JavaFX no soporta directamente; sus avisos y condiciones de redistribución deben verificarse antes de incluirlo en un instalador.

## Estado del proyecto

**CodeTrace v0.3 — candidato en validación.** La rama `feature/practice-curriculum-20261120` no equivale a una publicación. Están pendientes la aceptación manual del instalador y de las sesiones en Windows limpio, el repaso de atribuciones de terceros, la decisión de licencia y la etiqueta/release definitiva. La biblioteca con comentarios de consola v9 también debe integrarse y validarse antes del corte final.

El código está disponible para consulta, pero **todavía no se ha seleccionado una licencia de uso o redistribución**; que el repositorio sea visible no concede por sí solo una licencia de software libre.

---

<div align="center">

**CodeTrace** — una forma más deliberada de aprender cómo se construye el software.

Desarrollado por Marcos Moreira DEV.

</div>