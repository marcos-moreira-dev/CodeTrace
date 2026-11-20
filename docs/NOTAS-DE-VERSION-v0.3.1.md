# CodeTrace v0.3.1 — actualización visual de la versión pública de prueba

CodeTrace es una herramienta de escritorio para **aprender la estructura y la
escritura de código reconstruyendo proyectos virtualmente**, sin modificar sus
archivos originales. No sustituye a un IDE ni ejecuta comandos de forma
automática.

## Novedades de v0.3.1

- Icono principal de CodeTrace en alta resolución y fondo transparente,
  reutilizado en ventanas, escritorio e instalador.
- Icono propio de sesiones `.ctrace`, formal y diferenciado del de la aplicación,
  con hoja gris y líneas de práctica verde/rosa pastel.
- Portada oficial del repositorio en GitHub y autoría **Marcos Moreira**
  en metadatos Maven y Windows.
- Mismo flujo de aplicación y mismos ejercicios que v0.3.0;
  no se cambian las funcionalidades ni el formato de sesión.

## Destacados

- Editor de práctica con plantilla inmutable, aciertos y errores visibles,
  cronómetro, progreso, zoom y preferencias.
- Explorador de proyectos con reconstrucción virtual de carpetas y archivos.
- Biblioteca integrada de 14 lenguajes y **813 archivos de referencia**
  organizados por fundamentos, estructuras de datos, algoritmos y patrones.
  La cifra representa archivos, no ejercicios independientes.
- Sesiones `.ctrace` para retomar la práctica.
- Markdown con imágenes locales, lectura de archivos, vistas multimedia,
  mapa de dependencias estáticas y consola de comandos explícitos.

## Windows 11

**ZIP portable:** descomprimir y abrir `CodeTrace.exe`; incluye el runtime
necesario y no requiere JDK ni Maven.

**Instalador EXE:** instala CodeTrace, crea accesos y registra la asociación de
archivos `.ctrace`. **El instalador no está firmado digitalmente**, por lo
que Windows puede presentar advertencias.

Se entrega un archivo SHA-256 con las huellas del ZIP y del EXE. Los dos
paquetes deben proceder de esta misma compilación de GitHub Actions.

## Límites conocidos y seguridad

- Se trata de una primera versión pública de prueba: existen pruebas
  automatizadas y uso real del autor, pero no una certificación exhaustiva
  de cada entorno, dispositivo gráfico o códec.
- CodeTrace no modifica los archivos que abre como referencia. Sin embargo,
  los **comandos de la consola integrada sí operan sobre el directorio real**:
  usa únicamente comandos que conozcas y preferiblemente copias de trabajo.
- El mapa de dependencias es un análisis estático aproximado.
- Formatos de audio/vídeo adicionales pueden necesitar FFmpeg instalado
  por el usuario. **FFmpeg no está incluido** en los paquetes publicados.

## Licencia

Código original: **MIT — © 2026 Marcos Moreira**. Consultar `LICENSE`.
Los recursos originales de identidad visual y las dependencias de terceros
están delimitados en `LICENSING.md` y `THIRD-PARTY-NOTICES.md`. Los paquetes
incluyen avisos legales y licencias redistribuidas de los componentes usados.
La denominación CodeTrace no se declara marca registrada.
