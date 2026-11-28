# Java integral — implementación por etapas

Fundamentos 00–05 congelados: el commit c6362835 superó la CI curricular, Verify y Windows. No se modifica su código.

Plan acordado: 06 matemáticas, 07 datos y memoria (sin hardware), 08 archivos/E/S, 09 procesos, 10 concurrencia, 11 redes, 12 persistencia, 13 seguridad, 14 comunicación con dispositivos, 15 multimedia, 16 intérpretes, 17 distribuidos, 18 diagnóstico, 19 IA, 20 arquitectura de software. Arquitecturas con dominios cotidianos: verdulería, biblioteca, panadería, heladería, centro ecuestre, peluquería canina, cabañas, restaurante, supermercado y alquiler de bicicletas. Sin frameworks ni dependencias externas. Java clásico, un concepto por ejercicio; modelos explícitos y pruebas fuera de las fuentes.

Plan completo: `JAVA-INTEGRAL-PLAN.md`. Inventario por fuente: `JAVA-INTEGRAL-INVENTARIO.tsv`.

## Avance

- 06: 9 unidades, 20 clientes implementados; CI aprobada en 32ad8838.
- 07: 18 unidades, 44 fuentes implementadas (33 clientes ejecutables); modelos explícitos para memoria y reemplazo de páginas. CI aprobada en 32ad8838.
- 08: 20 unidades, 40 fuentes (38 clientes ejecutables); archivos temporales propios, variantes dependientes del sistema y canales estándar. Verificación Java integral aprobada en c75d0385; pruebas generales fallaron por recuentos desactualizados, corregidos aquí.
- 09: 20 unidades, 47 fuentes; procesos hijos acotados y modelos explícitos de planificación. Verificación Java integral aprobada.
- 10: 31 unidades, 80 fuentes; hilos, memoria compartida, sincronización, problemas clásicos, modelos de exclusión/deadlock, pools y paralelismo. Verificación Java integral aprobada en b13eb28f.
- 11: 24 unidades, 64 fuentes; direccionamiento, conmutación, rutas, TCP/UDP, fiabilidad, servidores concurrentes, NIO, HTTP y timeouts. Pendiente CI.
- 12: 9 unidades, 25 fuentes; registros, formatos, índice, consultas, joins, modelos de transacciones/aislamiento/WAL y contrato JDBC. Pendiente CI.
- 13: 11 unidades, 17 fuentes; hashes, HMAC, aleatoriedad, PBKDF2, AES-GCM, Ed25519, X25519, certificado local y autorización. Pendiente CI.
- 14: 7 unidades, 16 fuentes Java y 1 README documental; tramas, paridad, CRC, Hamming, canal y telemetría simulados. Pendiente CI.
- 15: 7 unidades, 16 fuentes; imágenes, transformaciones, filtros, rasterización, gráficos 2D y WAV locales. Pendiente CI.
- 16: 9 unidades, 21 fuentes; compilación, bytecode, JAR, carga de clases, lexer, parser, AST y comandos. Pendiente CI.
- 17: 10 unidades, 24 fuentes; mensajería, relojes, reintentos, idempotencia, detección de fallos, elección, replicación y exclusión distribuida simuladas. Pendiente CI.
- 18: 14 unidades, 23 fuentes; contratos, pruebas, trazas, JDB, JUL, medición, diagnóstico, JFR y empaquetado. Pendiente CI.
- 19: 12 unidades, 13 fuentes; patrones, dígitos, KNN, perceptrón, red neuronal, K-means, A*, genéticos, regresión, evaluación y minimax. Pendiente CI.
- 20: pendiente.
- Verificador separado: scripts/verificar-java-integral.py, manifiesto scripts/java-integral-casos.json. Compila solo las fuentes declaradas por cliente y compara salida; verifica cobertura de archivos. Los scripts históricos excluyen nuevas áreas de la compilación conjunta.
- Catálogo v35: 2048 entradas.

No se publica instalador ni se modifica C o TypeScript en este cambio.
