# CodeTrace v0.3.0 — notas de publicación (BORRADOR)

> Aún no publicar: faltan aprobación del autor, validación manual de Windows y licencia.

## Una nueva forma de practicar código

CodeTrace permite reconstruir virtualmente proyectos completos mientras se estudia su código fuente, sin alterar la referencia original. Integra un explorador optimizado, un editor de práctica con estados de error/acierto, sesiones `.ctrace`, vista Markdown, mapa de dependencias y consola para comandos explícitos.

### Destacados

- Biblioteca curricular: 14 lenguajes, 813 archivos de referencia (no equivalentes a 813 ejercicios).
- Progreso, filtrado por carpeta, modo concentración y preferencias de ergonomía.
- Vista previa de archivos multimedia compatibles, con FFmpeg opcional del usuario.
- Windows 11: distribución portable (runtime incluido) e instalador EXE.

### Instalación

**Portable:** extraer el ZIP y abrir `CodeTrace.exe`.

**Instalador:** ejecutar el EXE, siguiendo el asistente de Windows. El ejecutable todavía no está firmado digitalmente.

### Integridad

Anexar enlaces oficiales y huellas SHA-256 reales del commit publicado. No reutilizar hashes de candidatos anteriores.

### Limitaciones conocidas

- CodeTrace no es un IDE ni reemplaza un compilador.
- El mapa de dependencias es análisis estático básico.
- Audio/vídeo dependen de los códecs disponibles, o de FFmpeg local cuando corresponda.
- La consola trabaja sobre archivos reales de referencia; verificar los comandos antes de ejecutarlos.

### Licencias y colaboradores

Añadir licencia seleccionada y atribuciones exactas de terceros antes de liberar este borrador.