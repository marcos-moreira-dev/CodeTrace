# Rendimiento y escalabilidad de CodeTrace

## Contrato que no se debe romper

CodeTrace trabaja sobre una **referencia de solo lectura** y reconstruye una versión virtual.
Las optimizaciones nunca deben escribir ni eliminar los originales, omitir comprobaciones
de seguridad de rutas, alterar el formato de los archivos `.ctrace`, publicar resultados
de tareas canceladas o presentar métricas parciales como definitivas.

Las clases Java se documentan con **Javadoc** (`/** ... */`) para contratos públicos.
Los comentarios `//` explican decisiones difíciles (por qué se hace algo), costes
asintóticos, invariantes de seguridad y razones de una caché; se evita narrar instrucciones
obvias. Ante una optimización, conservar comentarios sobre invalidación, cancelación y
limitaciones. Los números de rendimiento se publican como **mediciones**, nunca como
promesas sin datos.

## Variables para medir

* `n`: número de entradas de la referencia; `d`: profundidad de carpeta.
* `B`: bytes realmente leídos del disco; `B_t`: bytes de textos examinados.
* `k`: hijos directos de una carpeta; `p`: referencias/imports por resolver.
* `U`: caracteres/graphemes de un archivo de práctica.
* Costes de disco y antivirus de Windows pueden dominar el tiempo de CPU.

No confundir **tiempo para mostrar el explorador** con **tiempo hasta terminar
el catálogo de progreso**. El usuario debe poder inspeccionar el árbol y abrir
archivos antes de que concluyan cálculos secundarios.

## Auditoría por subsistema

| Subsistema | Situación y coste teórico | Dirección |
| --- | --- | --- |
| Árbol de referencia | Recorrido de entradas y ordenación de hermanos: `O(n + Σ k log k)` con agregados por directorio | Índice inmutable fuera del hilo JavaFX, carga diferida |
| Catálogo de práctica | Recorrido `O(n)`, lectura y verificación de archivos elegibles `O(B_practicable)` | No leer contenido de multimedia/archivos excluidos |
| Estado de transcripción | Métricas por pulsación deben evitar recorrer `n` y `U` de nuevo | Mantener contadores e incrementos por archivo |
| Consulta secundaria | Ordenar hijos de la carpeta abierta: `O(k log k)` | Mantener lectura en segundo plano, rechazar symlinks |
| Buscar texto | Recorrido y lectura hasta límites de resultados | Búsquedas cancelables, sin bloquear JavaFX |
| Grafo de dependencias | Importaciones y resolución entre rutas | Construir mapa de rutas antes de resolver imports |
| Verificación de cambios | SHA de archivos registrados, por bytes | Conservar verificación de contenido donde afecte progreso |

Las operaciones interactivas merecen especial atención: una operación `O(n)`
periódica es muy distinta a repetir `O(n)` **por cada carácter**.

## Instrumentación reproducible (Windows / JDK 21)

1. Instalar JDK 21 (incluye `jcmd`, `jfr`) y abrir CodeTrace normalmente.
2. En otra ventana PowerShell, ejecutar `jcmd -l` para localizar el PID Java de CodeTrace.
3. Iniciar una grabación delimitada:
   `jcmd <PID> JFR.start name=codetrace settings=profile duration=60s filename=codetrace-rendimiento.jfr`
   (sustituir `<PID>` por el número real).
4. Abrir una referencia grande, navegar, buscar y escribir durante la captura.
5. Inspeccionar la grabación con Java Mission Control, `jfr summary` o `jfr print`.
   Revisar CPU, asignaciones, GC y eventos de lectura de archivos. Algunas
   métricas de E/S requieren habilitar eventos específicos.
6. No subir grabaciones con rutas personales, muestras de código privado o datos
   de uso a repositorios públicos.

Registrar por escenario: tiempo hasta mostrar raíz, tiempo hasta catálogo completo,
latencia p50/p95 del hilo JavaFX al escribir, bytes leídos, memoria máxima,
coste de búsqueda, cancelación y comportamiento de archivos grandes.

| Escenario | Tamaño sugerido | Comprobaciones |
| --- | --- | --- |
| Proyecto pequeño | 1.000 archivos | Inicio, navegación y progreso |
| Proyecto mediano | 10.000 archivos | Búsqueda de nombres, escritura sostenida |
| Proyecto grande | 50.000 archivos | Memoria, scroll y cancelación |
| Mixto | 100 MB fuente + 10 GB media | No leer multimedia para SHA de progreso |
| Rutas especiales | symlinks, inaccesibles, nombres Unicode | Seguridad y fallos parciales |
| Sesión existente | `.ctrace` de una versión anterior | Integridad de recuperación |

No se han publicado tiempos absolutos en esta guía: deben obtenerse en la máquina
de destino, repetidos y acompañados por características de disco y CPU.

## Invariantes de regresión

- La suma de los caracteres correctos no puede superar el total esperado.
- Saltar un carácter no equivale a escribirlo correctamente; puede resolver el ejercicio.
- Cambiar el alcance o excluir carpetas invalida los agregados de progreso.
- Cambiar o reemplazar un archivo practicable invalida su huella previa.
- Los binarios no son objetivos de mecanografía y no deben leerse completos para
  calcular progreso.
- El explorador de referencia muestra archivos aunque no sean practicables.
- Todo trabajo largo acepta cancelación; un resultado de solicitud obsoleta no
  reemplaza el resultado vigente.
- Una optimización de hashes nunca permite modificar originales ni alterar archivos
  existentes `.ctrace`.

## Decisión de ingeniería: RAM a cambio de menos disco

Los índices de nombres, rutas, totales y dependencias viven en RAM; no se
precarga por defecto el contenido de miles de archivos. El disco se consulta
cuando una operación concreta necesita leer bytes, y se evita repetir esa
consulta si ya existen metadatos reutilizables. La caché del sistema operativo
también contribuye: no se debe duplicar indiscriminadamente en la JVM.

| Cambio | Situación implementada | Coste/cautela |
| --- | --- | --- |
| Catálogo | SHA-256 solo para UTF-8 elegible y acotado; usa los bytes ya leídos | O(bytes de código), no O(bytes multimedia) |
| Índice del árbol | Acumulación postorden de totales y elegibilidad | O(n + carpetas); no guardar cargas binarios |
| Progreso global | Agregado por archivo, actualizable cuando cambia una sesión | O(1) contable por pulsación, reconstruir al invalidar |
| Progreso local | Contadores de grafemas correctos y resueltos mantenidos por transición | O(1) para un grafema; restauración O(U) |
| Dependencias | Índice temporal de sufijos y paquetes Go sobre un máximo de 1.200 fuentes | O(n × profundidad) RAM/creación, consulta hash promedio |
| Tiempos | `PerformanceProbe` solo si `codetrace.profile=true` | Sin rutas/nombres en los logs |

Los índices son proporcionales al número de entradas y no al tamaño en bytes
de los archivos. Para previews de código abiertos, mantener las restricciones
actuales de lectura y evitar nuevas cachés ilimitadas; un LRU de contenidos
solamente tendría sentido si JFR demuestra repeticiones de I/O relevantes y
si se define un presupuesto de memoria y una invalidación segura.

Para activar el perfilado ligero desde PowerShell en una sesión de prueba:

```powershell
$env:JAVA_TOOL_OPTIONS = "-Dcodetrace.profile=true"
mvn javafx:run
Remove-Item Env:JAVA_TOOL_OPTIONS
```

Los registros del perfilado solo incluyen la operación, el tiempo transcurrido
y el número de entradas. Para comparar cambios de código, medir también p95,
uso de heap, CPU e I/O con JFR y repetir los escenarios sobre el mismo equipo.

## Interfaz reactiva: colapsar y distribuir grafos

Algunas operaciones que parecían de disco eran **mutaciones de miles de
TreeItem de JavaFX** realizadas en su hilo gráfico. Colapsar todo ya no
atraviesa cada descendiente para emitir miles de eventos de expansión:
sustituye una sola raíz por un nodo nuevo de carga diferida y mantiene la
referencia/indexación intacta. Colapsar una rama reemplaza solo el nodo de esa
rama, conservando el estado de los demás hermanos. Volver a expandir no debe
restaurar por accidente los descendientes de la rama colapsada.

El diseño organizado del grafo conserva una cuadrícula de ocupación para
reservar el rectángulo completo de cada nombre. Después de la simulación
de fuerzas, los nodos con dos o más vecinos se acercan a su **baricentro**
(minimizando la suma de distancias al cuadrado), sin colocar ninguna etiqueta
sobre una celda ocupada. Las posiciones movidas manualmente y guardadas en
sesión prevalecen hasta que el usuario elige **Restablecer distribución**.
Eso respeta la personalización y evita que una actualización sobrescriba
el trabajo del usuario.

En modo de diagnóstico (`-Dcodetrace.profile=true`) se registran eventos
`Performance collapse-all`, `collapse-branch`, `graph-organized`,
`graph-grid`, `reference-tree` y `reference-catalogue` en
`logs/javafx-run.log` dentro del ZIP. Son tiempos de pared de cada operación,
no tiempos de CPU ni mediciones de FPS. El script
`scripts/diagnostico.bat` habilita temporalmente el perfilado al abrir la
aplicación y restaura las opciones Java preexistentes cuando sale.

### Escenario manual para reproducir la incidencia

1. Abre una referencia con miles de archivos y carpetas anidadas.
2. Expande varias ramas; usa **Colapsar todas** y después reexpándelas.
   La interfaz no debería mostrar «No responde» ni reabrir ramas sin pedirlo.
3. Abre un mapa de dependencias conectado, elige **Restablecer distribución**,
   y comprueba que los nodos con múltiples vecinos se acercan entre sí sin
   superponer las etiquetas. Mueve un nodo manualmente, guarda la sesión y
   comprueba que se restaura su posición personalizada.
4. Cierra CodeTrace y comparte el ZIP generado. Revisar el archivo antes
   de enviarlo: puede contener rutas o capturas sensibles.

### Presupuesto de RAM: medir antes de fijar un límite

El grupo «OpenJDK Platform binary» de Windows puede incluir dos JVM y no
equivale por sí solo al heap de CodeTrace. Registrar por separado la memoria
de CodeTrace, la de Maven/otros procesos Java, el heap de la JVM y el uso
nativo. No deducir un promedio de uso de memoria a partir de una sola captura
del Administrador de tareas. Para estudiar el heap, `jcmd <PID> GC.heap_info`
puede complementar el Administrador de tareas. La métrica relevante es la
variación al ampliar la referencia, no solo el tamaño absoluto del proceso.

## Evolución posterior, sujeta a perfiles

Consolidar los recorridos iniciales del árbol y del catálogo requiere un
contrato de metadatos compartido y pruebas de cambios durante el escaneo.
No introducir una base de datos persistente, hilos masivos ni un índice invertido
de búsqueda sin perfilar la necesidad. Evaluar el coste y la invalidación del
índice antes de añadirlo.
