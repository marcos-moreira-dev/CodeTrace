# CodeTrace v0.3 — condiciones de cierre y publicación

**Estado:** candidato de publicación, **no publicado**. La biblioteca de práctica **v8 (813 archivos de referencia en 14 lenguajes)**
forma parte del candidato en esta rama, sin equivaler a 813 ejercicios
independientes. Se valida junto a la aplicación antes de publicar.

La rama de trabajo `refactor/virtual-explorer-20261119` se basa en
`fix/media-preview-and-release-20261118`; ninguna reemplaza todavía `main`.
No fusionar, quitar `-SNAPSHOT` ni crear una etiqueta/release antes de
completar los controles manuales y resolver la licencia.

## Evidencia que sí existe

- Pruebas Java/JUnit, verificación GUI bajo JavaFX y empaquetado en GitHub Actions:
  [Verify](https://github.com/marcos-moreira-dev/CodeTrace/actions/workflows/verify.yml)
  y [Windows portable package](https://github.com/marcos-moreira-dev/CodeTrace/actions/workflows/package-windows.yml).
- La expansión masiva utiliza `ExplorerRows` y `VirtualExplorerView`; proyecta
  rutas lógicas fuera del hilo gráfico y utiliza `ListView` virtualizado.
  [Contrato y pruebas](EXPLORADOR-VIRTUAL.md).
- Sesiones `.ctrace` v1–v4: lectura compatible y validación de encabezados,
  tamaño, UTF-8, rutas y recuentos. El guardado reemplaza atómicamente el
  archivo, usa temporal en la misma carpeta, comprueba interrupciones y
  preserva la última sesión válida ante un fallo de guardado.
- La consola requiere ejecución explícita y tiene pruebas de cancelación.
  Los comandos externos se ejecutan en la **referencia original**,
  no en el workspace virtual: ese riesgo debe quedar visible.
- El paquete Windows tiene comprobaciones de estructura, runtime y
  huella `SHA-256`; ningún resultado automático garantiza que una GPU,
  un códec, una fuente o un selector nativo funcionen bien en el PC final.

**No confundir las pruebas con aceptación:** un pipeline verde no comprueba
el funcionamiento real de todos los códecs, el instalador en un Windows
sin herramientas de desarrollo, ni el uso con pantalla de 125/150 % DPI.

## Puertas obligatorias para la primera publicación

| Puerta | Estado | Prueba de aceptación |
| --- | --- | --- |
| Compilación, JUnit y smoke JavaFX | Automatizada | `Verify` verde en commit de publicación |
| ZIP portable, runtime Java y checksum | Automatizada | `package-windows` verde; `scripts/verificar-paquete.ps1` |
| Biblioteca v8: catálogo, clientes y salidas esperadas | CI y revisión didáctica en curso | `practice-curriculum` verde; revisar ejemplos y documentación contra ejecución real |
| Explorador masivo / trabajo habitual | Prueba de usuario parcialmente favorable | Expandir, desplazar, contraer y volver a expandir con >11.000 archivos; sin «No responde» |
| Sesiones y recuperación | Falta cierre manual | Nueva/antigua/truncada, guardar/abrir, original modificado, progreso y grafo persistidos |
| Audio, vídeo, Markdown y recursos | Falta cierre manual | MP3/WAV/MP4, MKV/FLAC opcionales, cierre y cancelación FFmpeg, sin modificación de originales |
| Consola y comandos externos | Falta cierre manual | Comando inofensivo, proceso largo, detener, cambiar de referencia y cerrar |
| UX, accesibilidad y escalas Windows | Falta cierre manual | 100 %, 125 % y 150 %; teclado, foco, pestañas, diálogo y mensajes |
| Instalación en Windows sin JDK/Maven | Falta cierre manual | ZIP portable limpio; instalador, asociación y icono `.ctrace` |
| Licencia de CodeTrace y avisos de terceros | **Decisión pendiente** | Elegir licencia, incorporar archivo LICENSE y avisos verificables antes de publicar |
| Versión y GitHub Release | Pendiente | Quitar `-SNAPSHOT`, etiqueta `v0.3.0`, notas y artefactos validados |

## Reproducir y registrar el cierre en Windows

Desde la rama candidata, ejecutar `scripts/diagnostico.bat`. El ZIP en
`reports/` contiene logs y pruebas automatizadas; no representa por sí solo
una aceptación manual. Para las funciones gráficas, ejecutar también
`scripts/auditoria-visual.ps1` y seguir [QA-WINDOWS.md](QA-WINDOWS.md).

En el informe de cada defecto incluir: commit, sistema y DPI, secuencia de
pasos, resultado esperado, resultado observado, pantalla correspondiente,
si es reproducible y archivo ZIP **revisado por privacidad**.

Para validar el ZIP generado por `package-windows.ps1`:
```powershell
.\scripts\verificar-paquete.ps1
Get-FileHash .\dist\CodeTrace-Windows-portable.zip -Algorithm SHA256
```
Comparar con `dist\CodeTrace-Windows-portable.zip.sha256`.

## Delimitación funcional y legal

- El programa no es un IDE: no añadir editor completo, autocompletado,
  ejecución automática de comandos o análisis semántico total del grafo
  en esta versión.
- No distribuir FFmpeg con CodeTrace sin una revisión separada de la
  licencia del **build exacto** y los avisos/fuentes exigibles. El fallback
  mediante FFmpeg instalado por el usuario puede permanecer opcional.
- `docs/ICONOS-Y-ATRIBUCIONES.md` registra algunas fuentes e iconos,
  pero **no sustituye** un inventario completo de licencias redistribuidas.
- El repositorio público **no** concede automáticamente permisos de
  reutilización del código. La licencia será una decisión de autor:
  evaluar MIT o Apache-2.0 y verificar los avisos de terceros.
- La biblioteca v8 ya está integrada en la rama candidata, organizada por
  algoritmos, estructuras y patrones, con fuentes C/C++ para bibliotecas
  estáticas y dinámicas. No ampliar el temario por volumen durante el cierre:
  corregir contratos, ejemplos y salidas esperadas que no correspondan a la ejecución.

## Orden final de trabajo

1. Resolver regresiones verificables P0/P1; evitar cambios cosméticos no pedidos.
2. Ejecutar pruebas manuales de QA-WINDOWS y anotar PASS/FAIL/N/A.
3. Revisar licencias, empaquetar/instalar sin herramientas de desarrollo.
4. Decidir `v0.3.0`, fusionar rama y publicar Release cuando todo esté aprobado.
5. Mantener el temario v8 congelado; cualquier futura expansión del currículo
   será una entrega independiente después del cierre de la versión.
