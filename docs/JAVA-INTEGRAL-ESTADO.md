# Java integral — implementación por etapas

Fundamentos 00–05 congelados: el commit c6362835 superó la CI curricular, Verify y Windows. No se modifica su código.

Plan acordado: 06 matemáticas, 07 datos y memoria (sin hardware), 08 archivos/E/S, 09 procesos, 10 concurrencia, 11 redes, 12 persistencia, 13 seguridad, 14 comunicación con dispositivos, 15 multimedia, 16 intérpretes, 17 distribuidos, 18 diagnóstico, 19 IA, 20 arquitectura de software. Arquitecturas con dominios cotidianos: verdulería, biblioteca, panadería, heladería, centro ecuestre, peluquería canina, cabañas, restaurante, supermercado y alquiler de bicicletas. Sin frameworks ni dependencias externas. Java clásico, un concepto por ejercicio; modelos explícitos y pruebas fuera de las fuentes.

Plan completo: `JAVA-INTEGRAL-PLAN.md`. Inventario por fuente: `JAVA-INTEGRAL-INVENTARIO.tsv`.

## Avance

- 06: 9 unidades, 20 clientes implementados. Pruebas pendientes de CI.
- 07: 18 unidades, 44 fuentes implementadas (33 clientes ejecutables); modelos explícitos para memoria y reemplazo de páginas. Pruebas pendientes de CI.
- 08–20: pendientes.
- Verificador separado: scripts/verificar-java-integral.py, manifiesto scripts/java-integral-casos.json. Compila solo las fuentes declaradas por cliente y compara salida; verifica cobertura de archivos. Los scripts históricos excluyen nuevas áreas de la compilación conjunta.
- Catálogo v21: 1661 entradas.

No se publica instalador ni se modifica C o TypeScript en este cambio.
