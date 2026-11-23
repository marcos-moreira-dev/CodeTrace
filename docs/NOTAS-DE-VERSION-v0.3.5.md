# CodeTrace v0.3.5 — mapa de dependencias y biblioteca de práctica

Versión oficial para Windows. Incluye los cambios aprobados de la rama experimental y conserva las funciones de v0.3.4.

## Novedades

- Mapa de carpetas reales y dependencias explícitas; distingue contención (línea discontinua) de importaciones o referencias (flecha).
- Islas de archivos opcionales, desplazables como bloque rígido, distribución adaptativa y navegación segura con zoom mediante rueda.
- Raíz del mapa roja, búsqueda instantánea por nombre de archivo en naranja y selección manual morada.
- Inspector de carpetas y archivos, incluidos descendientes fuera de una vista parcial.
- Biblioteca v15: **1.037 archivos de referencia en 14 lenguajes**, no 1.037 ejercicios independientes.
- Colección didáctica Counter-Strike: **198 fuentes en Java 21, C# 12, Python 3.12, C++20 y TypeScript 5.9**; 13 ejercicios de patrones o variantes por lenguaje, simulación automática, MVC y Facade. Comentarios específicos del dominio y salidas de consola. C++ separa las operaciones principales en cabeceras e implementaciones; TypeScript usa tipado estricto.
- Los ejemplos generales permanecen disponibles. Los escenarios de Counter-Strike son ejercicios independientes de código original, sin recursos del videojuego ni afiliación oficial.

## Distribución

Instalador EXE, ZIP portable y archivo de sumas SHA-256 generados desde el mismo commit de main. Incluyen runtime Java; no necesitas JDK ni Maven para abrir la aplicación instalada. El instalador no tiene firma digital; Windows puede mostrar SmartScreen. La consola ejecuta comandos explícitos sobre la referencia real. FFmpeg no se redistribuye por defecto.

## Verificación

La publicación exige la verificación de aplicación (JUnit y smoke JavaFX), biblioteca multilenguaje y empaquetado Windows en el commit exacto de publicación. Esto no equivale a una certificación exhaustiva de todos los equipos, escalas de pantalla o formatos multimedia. La versión anterior v0.3.4 se conserva para recuperación.

Código y documentación originales: MIT © 2026 Marcos Moreira. Consultar LICENSE, LICENSING.md y THIRD-PARTY-NOTICES.md.
