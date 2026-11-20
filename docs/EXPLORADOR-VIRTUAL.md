# Explorador virtual de CodeTrace — refactor experimental

## Problema observado

En una referencia de aproximadamente 11.700 archivos y 1.021 carpetas,
la expansión recursiva realizada sobre `TreeItem<Path>` provocó
una expansión medida de 13,87 segundos y una pausa de JavaFX
de 7,89 segundos al desplazarse. Reducir el tamaño de lotes y
conservar árboles expandidos mejoraba parcialmente el problema,
pero no atacaba el coste de actualizar el número de filas y las
jerarquías de controles en el hilo gráfico.

**Esta rama no modifica el grafo de dependencias, las sesiones `.ctrace`
ni la escritura de archivos originales.**

## Contrato de arquitectura

| Componente | Responsabilidad |
| --- | --- |
| `ReferenceTreeIndex` | Índice inmutable de rutas, hijos y totales; se construye fuera de JavaFX |
| `VirtualChildrenIndex` | Instantánea de hijos del workspace virtual; no accede al disco |
| `ExplorerRows` | Proyección pura: rutas relativas + profundidad + tipo; ninguna dependencia JavaFX |
| `VirtualExplorerView` | `ListView` con celdas recicladas; pinta las filas visibles mediante VirtualFlow |
| `ReferenceTree` | Adaptador de compatibilidad: acciones, selección, filtros y sincronización con la aplicación |

**Expandir todo** y la expansión recursiva de una rama grande
(más de 40 carpetas o 1.000 archivos indexados) utilizan la proyección
lógica. Los menús de expansión de proyectos pequeños conservan el
comportamiento tradicional e inmediato de JavaFX.

**Expandir todo** computa el conjunto de directorios expandidos
y una lista ordenada de filas fuera del hilo JavaFX. Solo al terminar se
publica la lista de una vez. El `TreeView` convencional queda oculto y
un `ListView` virtualizado presenta la proyección. **Contraer todo**
restaura inmediatamente el explorador superficial tradicional.
Los archivos de referencia nunca se escriben.

Las acciones de una carpeta en la lista virtual cambian exclusivamente
el conjunto lógico de rutas expandidas. Se genera otra proyección fuera
del hilo JavaFX y se conserva la selección cuando la fila continúa visible.
Los menús y los controles de progreso utilizan la misma ruta absoluta de
referencia que antes del refactor.

### Invariantes

- Nunca crear un `TreeItem` por cada archivo para `Expandir todo`.
- Ningún recorrido de proyección consulta el disco ni decodifica archivos.
- Cancelar el trabajo previo y descartar resultados obsoletos mediante
  identificador de generación (cambio de referencia, búsqueda o salida).
- Filtrar rutas con el índice disponible, sin acceder a datos del archivo.
- Las rutas virtuales creadas por el usuario se mezclan con las originales,
  sin duplicarlas ni modificar los originales.
- Límite de seguridad: 150.000 filas lógicas en una proyección.
- Memoria O(filas lógicas), tiempo O(rutas visibles + ordenación de hermanos);
  la cantidad de objetos gráficos se mantiene acotada por el viewport.
- Mantener el árbol convencional para la navegación manual sencilla,
  evitando una migración disruptiva de los menús existentes.

### Estado de carga y publicación

La primera apertura puede mostrar un árbol vacío **mientras se crea el índice**,
porque no se construyen miles de `TreeItem` con datos incompletos. Para hacer
visible ese estado, `CodeTraceApp` muestra un `ProgressIndicator` centrado
sobre el explorador con el mensaje «Preparando el explorador...». El indicador
del encabezado continúa informando expansiones y trabajos posteriores; no
se impide hacer scroll sobre filas que ya estén presentadas.

`ReferenceTree.isIndexing()` distingue el escaneo inicial de otras tareas:
las actualizaciones de la proyección masiva conservan su representación
previa hasta instalar las filas nuevas. Una transición breve permite a JavaFX
realizar el siguiente renderizado antes de ocultar el indicador central.
Mientras haya trabajo pendiente, el estado contextual no debe afirmar
«Proyecto listo» por el mero hecho de haberse activado una referencia.

### Medición y regresiones

Un ZIP de diagnóstico del 19 de noviembre de 2026 mostró en una ejecución
de Windows: indexación topológica de **11.867 archivos en 1.640 ms**,
catálogo de progreso de **10.427 entradas en 2.407 ms** y proyección lógica
de **12.896 filas en 63,6 ms**. Son tiempos observados de un único entorno,
no promedios ni garantías de latencia. Confirman que la generación de filas
no es por sí sola el cuello de botella percibido al cargar la interfaz.
El log también contiene avisos internos de `VirtualFlow` sobre tamaño
de celdas; conviene seguirlos mediante profiling sin concluir que sean
una excepción fatal.


Con `-Dcodetrace.profile=true`, `Performance explorer-projection`
mide el cálculo de filas, independientemente de `reference-tree`,
`reference-catalogue` y los posibles `fx-frame-gap`.
Las cifras anteriores pertenecen a la versión previa y **no** se pueden
reutilizar para afirmar una aceleración de esta versión.

Pruebas automatizadas:
- `ExplorerRowsTest` comprueba orden carpeta-antes-archivo, profundidad,
  filtrado, cierre lógico y miles de entradas virtuales sin archivos físicos.
- `CodeTraceAppSmokeTest` comprueba que la orden de expansión global
  no fuerce expandir las ramas físicas del `TreeView`.
- Permanecen las pruebas originales de menús de contexto, progreso, sesión,
  rutas, búsqueda y construcción de Windows.

Validación manual obligatoria antes de integrar en la rama de cierre:

1. Abrir la referencia habitual de Descargas con más de 11.000 archivos.
2. Expandir todo, hacer scroll desde arriba hasta abajo y regresar al inicio;
   comprobar que no aparezca «No responde».
3. Contraer todo, expandir nuevamente y comprobar que ninguna visualización
   vieja se publique fuera de orden.
4. Contraer y expandir una rama dentro de la vista virtual, comprobar que se
   conserva el contexto de la selección.
5. Abrir archivos de código y de consulta desde la lista; comprobar menús
   de contexto, `Solo`, exclusiones y búsquedas.
6. Guardar y volver a abrir un proyecto `.ctrace` existente; comprobar
   que no se pierde progreso y que el original queda intacto.
7. Cerrar CodeTrace para generar el ZIP; comparar los registros
   `explorer-projection`, `fx-frame-gap` y el heap usado.

La implementación se mantiene aislada en
`refactor/virtual-explorer-20261119` hasta completar esa validación.
