# Identidad visual de CodeTrace

**Autor y editor: Marcos Moreira** · Identidad gráfica para **v0.3.1**.

## Recursos canónicos

| Elemento | Ruta en el repositorio | Uso |
| --- | --- | --- |
| Portada con nombre CodeTrace | `docs/media/00-portada-codetrace.jpg` | Cabecera del README y comunicación |
| Símbolo transparente de CodeTrace | `src/main/resources/icons/codetrace-window.png` | Ventana, ejecutable, barra de tareas, icono de instalación |
| Símbolo para Acerca de | `src/main/resources/icons/codetrace-about-hires.png` | Imagen de mayor tamaño dentro de la aplicación |
| Hoja de sesión (PNG transparente) | `src/main/resources/icons/codetrace-session.png` | Archivos `.ctrace` guardados |

Los dos iconos son **PNG RGBA de 512 × 512 píxeles**. El primero expresa
ventanas de código superpuestas y progreso; el segundo, un documento de
práctica con marcas verdes y rosas, hoja gris y símbolo de programación.

## Windows

No mantener archivos `.ico` duplicados y desactualizados. El script
`scripts/crear-icono-sesion.ps1` produce automáticamente iconos multirresolución
de **16, 24, 32, 48, 64, 128 y 256 px** a partir de las fuentes PNG del repo.
`scripts/package-windows.ps1` los inserta en el ejecutable y en la asociación
`.ctrace` registrada por el instalador. Las imágenes originales tienen
transparencia real; ningún relleno blanco debe ocultar el escritorio.

El instalador y la aplicación indican **Marcos Moreira** como editor,
`Copyright (c) 2026 Marcos Moreira` y versión de producto.

## Alcance legal

La licencia MIT de código original no concede automáticamente derechos
sobre los elementos gráficos originales: consultar `LICENSING.md`.
No se declara que `CodeTrace` sea una marca registrada.
