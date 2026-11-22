# CodeTrace v0.3.3 — comentarios opcionales y mejoras del explorador

Actualización de la versión pública de prueba para Windows. Mantiene la
licencia MIT, la autoría de Marcos Moreira y los iconos oficiales.

## Cambios
- **Reglas de práctica → Practicar comentarios:** activado inicialmente para
  respetar el comportamiento de versiones anteriores. Al desactivarlo,
  los comentarios siguen visibles, pero se omiten en la mecanografía y en el
  porcentaje de caracteres exigidos, en todos los lenguajes reconocidos de la
  biblioteca, incluidos PowerShell, Shell, CMD y NASM.
- La selección no modifica los archivos de referencia ni los caracteres
  anteriormente escritos. Se recuerda como preferencia y las sesiones
  antiguas .ctrace siguen siendo compatibles.
- **Progreso: todo el proyecto** disponible desde el menú contextual cuando
  se está contabilizando una carpeta específica.
- La casilla del explorador se denomina **Solo esta carpeta**.
- Dimensiones y recorte gráfico controlados para reducir restos transitorios
  de iconos al contraer y actualizar filas virtualizadas.

## Instalación
- EXE para Windows y ZIP portable con runtime Java incluido.
- SHA-256 de ambos artefactos disponible junto a la publicación.
- Instalador sin firma digital; SmartScreen puede mostrar avisos.

## Advertencias
La omisión de comentarios se basa en análisis léxico conservador, no en un
compilador completo. Lenguajes o construcciones no reconocidas mantienen sus
caracteres como exigidos, para evitar omisiones incorrectas. La consola
integrada ejecuta comandos sobre archivos reales de la referencia: úsala
con precaución. Se trata de una versión de prueba, no de una certificación
exhaustiva de todos los entornos Windows.

Código original: MIT · © 2026 Marcos Moreira. Los componentes de terceros
mantienen sus licencias y avisos en THIRD-PARTY-NOTICES.md.
