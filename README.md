# CodeTrace

**Reconstrucción manual de proyectos de software, como práctica de escritura y organización.**

CodeTrace es una aplicación de escritorio que permite escoger una carpeta de referencia de solo lectura y reconstruir virtualmente su estructura y archivos de texto. La práctica consiste en escribir **directamente sobre la plantilla**: los caracteres correctos se señalan en verde y los incorrectos en rojo, sin bloquear el avance.

No pretende ser un IDE, un compilador, un curso ni un asistente de inteligencia artificial. Su propósito es hacer más agradable la práctica manual y fortalecer la familiaridad con el proceso de construir software.

## Plataforma

- **JDK 21 — Eclipse Temurin 21**, instalado como SDK del proyecto.
- **JavaFX 21** para la aplicación de escritorio.
- **Maven** para dependencias, compilación y pruebas.
- Paquete base: `com.mmdev.codetrace`.
- Sistema inicial: Windows 11; se evita acoplar el dominio a Windows.

## Estado

**Inicio del desarrollo.** La experiencia completa de transcripción, los archivos virtuales y la persistencia `.ctrace` están especificados, pero todavía no se han implementado.

## Contrato de producto

- La referencia se consulta sin modificarla y se comprueba por archivo mediante SHA-256.
- La reconstrucción vive en memoria y se guarda en una sesión propia, no en una segunda carpeta física.
- La escritura ocurre sobre la plantilla; los errores no bloquean el cursor; no hay autocompletado ni edición libre.
- Las estadísticas positivas cuentan únicamente caracteres introducidos correctamente; las omisiones se registran por separado.
- La interfaz prioriza un editor claro y amplio, paneles secundarios plegables, controles rectangulares y progreso global en la franja inferior.
- Los módulos de lenguajes aportarán reconocimiento, resaltado e imports opcionalmente omitibles, sin duplicar el motor de mecanografía.
- Markdown podrá alternar fuente y renderizado; la consola ejecutará comandos externos que el usuario configure explícitamente.

## Diseño y documentación

- [Especificación del producto](docs/PRODUCTO.md)
- [Decisiones técnicas](docs/ARQUITECTURA.md)

La arquitectura responde al dominio del producto. No se añadirán capas, patrones o elementos de interfaz por conveniencia técnica.

## Licencia

Pendiente de definir. **No asumir una licencia de código abierto hasta que exista un archivo LICENSE.**
