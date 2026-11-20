# CodeTrace v0.3 — cierre de la primera publicación

Actualizado: 20 de noviembre de 2026.

## Alcance

La interfaz y las funciones esenciales se encuentran en fase de aceptación manual. Las capturas del autor muestran biblioteca, transcripción, Markdown renderizado, grafo y consola. Esto acredita esas vistas, **no** el funcionamiento en todos los equipos.

## Inventario de entregables

| Entregable | Situación comprobada | Pendiente |
|---|---|---|
| Código fuente | Rama candidata `feature/practice-curriculum-20261120` | Integrar cambios v9, revisión y merge tras aceptación |
| Biblioteca v9 | 813 fuentes documentadas en 14 lenguajes, 813 entradas en catálogo e integración en rama candidata | Ejecutar CI final y validar salidas inferidas en entornos faltantes |
| Portable ZIP de Windows | Artefacto de CI construido y con checksum, incluye runtime Java | Probar en Windows limpio, sin JDK/Maven |
| Instalador EXE | Artefacto de CI generado, sin firma digital | Instalar/desinstalar y comprobar accesos e icono `.ctrace` |
| README promocional | Preparado con cinco capturas seleccionadas y rutas anonimizadas | Integrar imágenes y Markdown en GitHub |
| Tests automatizados | CI del commit base `3143ca0e...` terminó verde | Ejecutar CI del commit final posterior a v9 |
| Licencia | **MIT seleccionada; © 2026 Marcos Moreira** | Verificar los avisos de terceros realmente empaquetados antes de release |
| Versionado | `0.3.0-SNAPSHOT` | Fijar `0.3.0`, generar artefactos con etiqueta final |
| Firma del instalador | No firmada | Opcional; sin firma, SmartScreen puede advertir |

## Orden recomendado de cierre (sin ampliar funcionalidades)

1. Revisar la integración v9 en una copia limpia de la rama candidata. Ejecutar `python scripts/documentar_consola_practica.py --check`, Maven verify y la verificación del currículo.
2. Revisar el README completo **con las imágenes efectivamente presentes**; comprobar que no incluye nombres de usuario ni rutas personales.
3. Verificar manualmente los casos esenciales de `docs/QA-WINDOWS.md`: crear/retomar sesión, audio/vídeo y Markdown, grafo, comando que se puede detener, vista a diferentes DPI.
4. Probar instalador y portable en Windows 11 sin JDK ni Maven. Probar instalación, asociación de `.ctrace`, apertura, guardado, desinstalación y copia portable.
5. Revisar licencias de dependencias, tipografías, iconos, recursos y FFmpeg opcional. Elegir licencia de CodeTrace y generar avisos completos.
6. Aprobar el PR, integrar al branch principal, actualizar `pom.xml` a `0.3.0`, ejecutar CI sobre el commit definitivo, generar paquetes finales y comprobar SHA-256.
7. Crear etiqueta Git `v0.3.0`, GitHub Release con ZIP y EXE, hash SHA-256 y notas de versión. Mantener en el repo fuente y documentación; **no commitear** `target/`, `dist/`, ZIP, EXE, informes ni secretos.

## Qué no es imprescindible para v0.3

- Autocompletado o edición real de referencias.
- LSP, análisis semántico perfecto de imports o integración como IDE.
- Añadir más lenguajes o ejercicios únicamente para elevar el conteo.
- Incluir FFmpeg en los ejecutables: el fallback instalado por usuario es optativo.
- Firmar digitalmente el EXE: deseable para distribución amplia, pero no necesario para probar; debe comunicarse claramente la ausencia de firma.

## Criterios para declarar cerrado

- CI de la versión final verde y dos distribuciones disponibles (ZIP/EXE), con sus SHA-256.
- Confirmación de prueba real de Windows (apertura, instalación, sesiones, medios y CLI) sin fallos bloqueantes.
- Capturas y README visibles en GitHub; instrucciones de descarga sencillas.
- Licencias y atribuciones resueltas; cero dependencias binarias redistribuidas sin revisión.
- `main` correspondiente al commit publicado, `git status` limpio y `v0.3.0` apuntando al commit correcto.