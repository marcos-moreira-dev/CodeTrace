# CodeTrace — Biblioteca de práctica: auditoría de consola

**Fecha:** 20 de noviembre de 2026. **Base GitHub:** `feature/practice-curriculum-20261120` en el commit `3143ca0e39399c1ab926655d10cad0b5489ecc5e`.

## Resultado

- **813/813 archivos de código** en **14 lenguajes** contienen un comentario al final con un comando PowerShell reproducible desde la carpeta del lenguaje, referencia a cliente si el archivo es una dependencia y **salida esperada concreta** (o explícita ausencia de stdout).
- **490 puntos de entrada** tienen una instantánea de resultado. **385** de esas instantáneas están respaldadas por ejecución previa en el entorno disponible; **105** se derivan del código y de ejemplos equivalentes, sin ejecución local completa. No deben tratarse como certificación de todas las plataformas.
- Java 21: se compilaron las 141 fuentes y se ejecutaron **70 puntos de entrada** correctamente en esta auditoría.
- Se verificaron directamente otros **49 puntos de entrada** no cubiertos inicialmente (C, C++, JS, TypeScript, Python, Ruby y Go), todos con resultado correcto.
- Se eliminaron comentarios previos de salida genérica o ficticia cuando fueron detectados. La nueva anotación no afecta lógica, firmas ni instrucciones de programa.
- **Clases auxiliares**: se muestra el comando que compila y ejecuta el cliente de su unidad; donde no hay cliente, se aclara que compilar/verificar sintaxis no escribe salida estándar.
- **NASM x86-64**: usa WSL y documenta el valor impreso por el comando envolvente `codigo-de-salida:0`; no confunde el valor de retorno del proceso con stdout del ensamblador.

## Desglose por lenguaje

| Lenguaje | Fuentes |
|---|---:|
| c | 31 |
| cmd | 13 |
| cpp | 94 |
| csharp | 80 |
| go | 17 |
| java21 | 141 |
| javascript | 69 |
| kotlin | 83 |
| nasm-x64 | 13 |
| powershell | 13 |
| python | 85 |
| ruby | 69 |
| shell | 13 |
| typescript | 92 |


## Limitaciones de la auditoría

- No se dispuso de `.NET 8`, `pwsh` ni del entorno Windows `cmd` para ejecutar directamente todos esos casos. Para Kotlin se requiere compilador 2.2; el disponible localmente era anterior. NASM/WSL también requiere verificación en Windows/Linux real.
- Las salidas del inventario que figuran como `inferida_del_codigo` no se han observado en el entorno de destino. Una salida dependiente del SO/versión puede tener diferencias de formato.
- Este paquete **no es una publicación de CodeTrace** y no reemplaza la aceptación manual de la interfaz JavaFX en Windows.

## Aplicación a una copia local del repositorio

1. En PowerShell, situarse en la raíz del clone de `feature/practice-curriculum-20261120`.
2. Comprobar que el árbol de trabajo está limpio: `git status --short`.
3. Extraer este ZIP encima de la raíz del repo conservando la estructura de carpetas.
4. Ejecutar `python .\scripts\documentar_consola_practica.py --check --root .\src\main\resources\practice --outcomes .\scripts\salidas_practica.json`.
5. Ejecutar `bash scripts/verificar-biblioteca.sh` (si existe bash/WSL); para abrir la app en Windows: `.\scripts\diagnostico.bat`.
6. Revisar `git diff --stat` y el inventario antes de confirmar modificaciones.

La entrega incluye el validador idempotente, las instantáneas de consola y el inventario con verificación/derivación; el ZIP es un **paquete para aplicar a la rama**, no un commit subido de forma automática.

## Corrección de compatibilidad Windows

Se separó el programa autónomo `pila-capacidad-fija/Pila.c` de `pila/pila.c` para evitar una colisión por mayúsculas/minúsculas en Windows. Su salida `8` fue ejecutada con GCC C17.
