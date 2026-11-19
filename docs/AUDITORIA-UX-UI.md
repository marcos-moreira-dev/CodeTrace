# CodeTrace — auditoría integral UX/UI, casos de uso y configuración

## Objetivo y alcance
Revisar el **producto completo** antes de cerrar su publicación: interfaz,
jerarquía de acciones, productividad, consistencia visual, aprendizaje,
descubribilidad, navegación, accesibilidad, retroalimentación, preferencias,
comandos, visualizadores de referencias y seguridad. No ampliar el alcance
por entusiasmo técnico: todo cambio debe justificar su beneficio.

El usuario solicita un análisis llamado «IBEX CBI». No se adopta como
metodología formal hasta esclarecer sus siglas; la revisión se instrumenta
como auditoría heurística UX/UI y cobertura de casos de uso.

## Generar evidencia
1. Compilar y abrir CodeTrace desde la rama de cierre.
2. **Modo automático por defecto:** en otra consola, ejecutar
   `powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\auditoria-visual.ps1`.
   Registra hasta 100 cambios visuales significativos de CodeTrace mientras el
   usuario recorre normalmente la interfaz. Termina al cerrar la aplicación o
   al superar el límite de duración. El ZIP se crea en `reports/`.
3. **Modo guiado opcional:** `powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\auditoria-visual.ps1 -Guiado`.
   Presenta **37 escenarios**; Intro captura, S omite, Q finaliza.
4. **Restricción de seguridad:** solo se captura la ventana activa cuando
   pertenece al proceso verificado de CodeTrace; nunca se elige una consola,
   navegador ni ChatGPT por compartir el texto «CodeTrace» en su título.
5. Capturar usando un proyecto ficticio o de demostración; nunca contraseñas,
   tokens, archivos .env ni datos privados de clientes.
6. Revisar las capturas y eliminar las privadas **antes de compartir el ZIP**.
   La herramienta no transmite datos a Internet.

**Alcance real de la automatización:** recoge automáticamente cambios de
pantalla, pestañas y diálogos que el usuario haya abierto. No pulsa controles
ni recorre por sí sola todos los casos de uso. Los escenarios no visitados
deben figurar pendientes, jamás como capturados o aprobados.

## Criterios de conservación de pantallas, opciones y casos de uso

| Criterio | Pregunta de auditoría | Evidencia |
|---|---|---|
| Propósito | ¿Ayuda a explorar, comprender o practicar proyectos? | Escenario real |
| Frecuencia/valor | ¿Se usa a menudo o es infrecuente pero crítico? | Flujo y coste del fallo |
| Descubribilidad | ¿El usuario entiende dónde encontrarlo y qué hace? | Etiquetas, tooltip, menú |
| Coherencia | ¿Usa los mismos controles, iconos y estados que el resto? | Capturas comparativas |
| Carga cognitiva | ¿Hay opciones repetidas, ruido o una elección innecesaria? | Número de decisiones |
| Accesibilidad | ¿Funciona con teclado, foco, contraste, DPI y lector? | Pruebas manuales |
| Retroalimentación | ¿Indica inicio, progreso, éxito, error y recuperación? | Casos de éxito/fallo |
| Seguridad | ¿Protege el original, evita ejecutar comandos involuntarios? | Verificación sin mutación |
| Rendimiento | ¿La espera, zoom, vídeo y grafos permiten estudiar realmente? | Latencia percibida |
| Mantenibilidad | ¿Eliminar o fusionar reduce riesgo sin romper otra función? | Código y pruebas |

## Matriz de decisiones

Cada hallazgo debe registrar: **ID, pantalla, problema, evidencia,
impacto, decisión, esfuerzo, riesgo y prueba de aceptación**.

- **CONSERVAR:** sirve al objetivo, es comprensible y aporta valor.
- **AJUSTAR:** la función sirve, pero su ubicación, etiqueta, icono, orden,
  validación, comportamiento o retroalimentación es deficiente.
- **FUSIONAR:** hay funcionalidades duplicadas que hacen el mismo trabajo.
- **REUBICAR:** función válida, pero secundaria y compite con el flujo principal.
- **RETIRAR:** no tiene un caso de uso defendible y aumenta complejidad o riesgo.
- **INVESTIGAR:** no hay evidencia suficiente para modificarla.

No retirar funciones porque se usan poco: la frecuencia se valora junto con
criticidad, seguridad y propósito didáctico.

## Prioridades y gates

- **P0:** pérdida de datos, modificación de la referencia, bloqueo, cierre
  imposible, ejecución insegura, mensajes erróneos de finalización.
- **P1:** funcionalidad importante inutilizable o fricción grave en vídeo,
  búsqueda, grafos o restauración.
- **P2:** inconsistencias visuales, ergonomía de diálogos, accesibilidad.
- **P3:** acabados cosméticos o preferencias menores.

La auditoría no está «aprobada» simplemente por completar una carpeta
de capturas: cada caso P0/P1 requiere aceptación visual y funcional en Windows.
Cualquier propuesta de eliminar configuración, modo o caso de uso exige
documentar la intención original y probar su reemplazo.

## Regresión obligatoria de mecanografía

- Abrir `variables.ps1` de la biblioteca incorporada.
- Transcribir todas las líneas **visibles**, dejando saltos de línea
  estructurales pendientes si se navega con clics.
- Corregir errores en líneas anteriores o en el último carácter.
- Debe aparecer una vez «Archivo terminado» con felicitación, y sonar
  `success.mp3` cuando Preferencias lo permite.
- Las estadísticas y la barra del archivo usan solo glifos visibles para
  su denominador; la barra debe marcar 100 % al completar.
- Cerrar y reabrir un documento ya terminado no muestra felicitación
  espontánea; volver a modificarlo y terminarlo sí.
- Verificar que el proyecto de referencia permanece intacto y que la sesión
  conserva correctamente los resultados al guardar/reabrir.

## Entregable final de auditoría

Un inventario de pantallas con captura, decisión justificada por caso de uso,
riesgo P0–P3, cambios de UI propuestos, pruebas de aceptación y lista de
funcionalidades que no se tocarán. Evitar rediseños masivos sin evidencia.
