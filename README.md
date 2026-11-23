<div align="center">

<img src="docs/media/00-portada-codetrace.jpg" alt="CodeTrace: símbolo de reconstrucción de código y logotipo oficial" width="100%">

# CodeTrace

### Aprende código reconstruyéndolo. Explora proyectos sin alterar sus originales.

**Un espacio de práctica de mecanografía para código real, con explorador, lectura, progreso y mapa de dependencias.**

Java 21 · JavaFX · Windows 11 · 14 lenguajes en la biblioteca de práctica · [Licencia MIT](LICENSE)

[Qué puedes hacer](#qué-puedes-hacer) · [Capturas](#capturas-de-pantalla) · [Instalación](#instalación-y-primeros-pasos) · [Desarrollo](#desarrollo) · [Estado del proyecto](#estado-del-proyecto)

</div>

![CodeTrace mostrando una clase Java de práctica y el explorador virtual](docs/media/01-biblioteca-java.jpg)

> **Lee el código. Reconstruye su estructura. Escríbelo con precisión.** CodeTrace no pretende sustituir tu editor o tu IDE. Está pensado para estudiar la organización de programas y practicar su escritura, sin modificar los archivos que sirven de referencia.

## Qué puedes hacer

- **Personalizar lo que practicas.** Activa o desactiva «Practicar comentarios» para concentrarte en el código de los lenguajes admitidos; los comentarios permanecen visibles. El progreso se recalcula sin eliminar lo ya mecanografiado.
- **Practicar sobre una plantilla real.** Los caracteres de referencia permanecen visibles mientras escribes: los aciertos, errores y caracteres pendientes tienen estados diferenciados. Puedes seleccionar líneas, ajustar el tamaño de letra y usar un modo de concentración.
- **Reconstruir un proyecto de manera virtual.** Reproduce su estructura de carpetas y archivos, manualmente o mediante reglas automáticas, sin escribir encima de la referencia original.
- **Seguir el progreso.** Consulta el avance por archivo y proyecto; filtra carpetas y categorías, registra aciertos y velocidad, y guarda sesiones `.ctrace` para retomarlas más adelante.
- **Explorar proyectos existentes.** Navega el árbol de archivos, busca contenido y consulta código o archivos de configuración de solo lectura. La navegación está optimizada para proyectos grandes.
- **Explorar la dimensión del código (rama experimental v0.3.5).** El mapa representa carpetas y dependencias explícitas detectadas. Desde su menú contextual puedes **mostrar u ocultar archivos de código** como nodos secundarios, conectados a la carpeta que los contiene. Doble clic abre el archivo; no se realizan análisis semánticos nuevos. El dibujo está limitado para proteger el rendimiento y señala las vistas parciales. [Comportamiento del mapa](docs/MAPA-ESTRUCTURA-DEPENDENCIAS.md).
- **Estudiar recursos complementarios.** Abre README con Markdown renderizado e imágenes locales; consulta imágenes, audio y vídeo compatibles, y utiliza una consola para ejecutar **comandos explícitos** sobre el proyecto real.

## Identidad de CodeTrace

El símbolo de la aplicación utiliza ventanas de código superpuestas y una barra
de progreso; es un PNG transparente integrado en la aplicación y el instalador.
Las sesiones `.ctrace` tienen un **icono de documento independiente**, con
indicadores pastel de práctica correcta e incorrecta. Ambos se convierten a
ICO multirresolución durante el empaquetado Windows. El autor y editor del
programa es **Marcos Moreira**, también identificado en sus metadatos.
[Guía de identidad visual](docs/IDENTIDAD-VISUAL.md).

## Capturas de pantalla

### Aprende con una biblioteca organizada

![Biblioteca Java con fundamentos, estructuras de datos, algoritmos y patrones](docs/media/01-biblioteca-java.jpg)

La biblioteca integrada comprende **14 lenguajes y 813 archivos de referencia** en v0.3.4. Son *archivos*, no 813 ejercicios independientes. Sus contenidos cubren fundamentos, estructuras de datos, algoritmos y patrones de diseño; los ejemplos incluyen guías de consola y salidas esperadas, algunas todavía pendientes de validación cruzada en todos los entornos.

**En la rama experimental v0.3.5**, el catálogo interno `v9` incluye **830 archivos**, con contratos, `record`, `enum` y clases colaboradoras Java 21 separados donde aportan claridad. Los otros lenguajes conservan las estructuras idiomáticas de sus módulos. [Auditoría de los ejemplos y criterio didáctico](docs/AUDITORIA-EJEMPLOS-V035.md).

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

**Descargas oficiales:** [CodeTrace v0.3.4 — Windows](https://github.com/marcos-moreira-dev/CodeTrace/releases/tag/v0.3.4).

- **Instalador `.exe`:** instalación normal y asociación de sesiones `.ctrace`.
- **Archivo `.zip` portable:** descomprime y ejecuta `CodeTrace.exe` sin instalar Java por separado.
- **SHA-256:** se publica junto a ambos archivos para verificar sus descargas.

Esta versión 0.3.4 corrige la coherencia de **Reglas de práctica**: la barra de progreso utiliza como denominador únicamente los caracteres realmente exigidos, incluyendo las omisiones por Turbo, comentarios y dependencias. «Omitir dependencias de código» se convierte en una opción reversible con estado visible; desaparece el control redundante «Todos». Al desactivar «Autocrear archivos y carpetas» se retiran solo los nodos virtuales creados automáticamente durante la sesión actual que estén vacíos y sin trabajo del usuario. Las sesiones `.ctrace` siguen siendo compatibles y los archivos reales no se modifican.

CodeTrace es una **versión de prueba**. El instalador no tiene firma digital; Windows puede mostrar advertencias de SmartScreen. Se han realizado pruebas automatizadas y uso real del autor, pero no una certificación exhaustiva en todos los equipos. Consulta [los límites y las pruebas](docs/CIERRE-V0.3.md).

Para probar el código fuente necesitas **Eclipse Temurin JDK 21**, **Maven** y un entorno gráfico de Windows:

```powershell
git clone https://github.com/marcos-moreira-dev/CodeTrace.git
cd CodeTrace
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

**CodeTrace v0.3.4 — primera versión pública de prueba.** El autor verificó informalmente las funciones habituales y el repositorio realiza pruebas automatizadas; no se afirma aceptación exhaustiva en todos los equipos Windows. La biblioteca contiene 813 archivos de referencia en 14 lenguajes, con anotaciones de consola. Algunas salidas de otros lenguajes se infirieron a partir del código y requieren comprobación en su entorno específico.

**Código fuente original:** licencia [MIT](LICENSE), © 2026 Marcos Moreira. Puedes usarlo, modificarlo y distribuirlo, incluso comercialmente, conservando el aviso y la licencia; no debes mostrar ningún contacto personal. Los elementos gráficos originales y los componentes de terceros tienen alcances distintos: [identidad y recursos](LICENSING.md) y [avisos de terceros](THIRD-PARTY-NOTICES.md). El nombre CodeTrace no se declara marca registrada.

---

<div align="center">

**CodeTrace** — una forma más deliberada de aprender cómo se construye el software.

Desarrollado por Marcos Moreira.

</div>