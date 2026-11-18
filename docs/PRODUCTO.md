# CodeTrace — producto planificado

CodeTrace propone una práctica tranquila de reconstrucción manual de software, sin curso ni asistente. La referencia es una carpeta real que CodeTrace no modifica. La reconstrucción es un árbol virtual persistible en \`.ctrace\`, no una copia física.

## Escritura prevista
- Plantilla **inmutable**: todos los glifos visibles siempre proceden del archivo original. Al escribir, únicamente cambian los estilos del carácter que se está calcando; no aparece el carácter erróneo en su lugar.
- Un clic en cualquier línea selecciona la franja rectangular completa e inicia la práctica desde el principio de esa línea, sin modificarla.
- Control de tamaño de letra vertical, a la derecha del workspace; pestañas estilizadas de forma transversal.
- Verde para coincidencia y rojo para diferencia; los errores no bloquean el avance.
- Edición mínima natural: selección, cursor, Retroceso, Supr, deshacer.
- Sin autocompletado, cierre automático de llaves o reformato.
- Comparación sobre caracteres confirmados (acentos, composición Unicode, teclas muertas).
- Omisiones explícitas como imports se separan de los caracteres realmente escritos.
- Estadísticas positivas: solo caracteres escritos correctamente, sin duplicar corregidos.

## Árbol y referencia
- Crear explícitamente carpetas y archivos virtuales que coincidan con los originales. Una creación es un hito y no requiere opciones destructivas en el menú contextual.
- Hash SHA-256 por archivo de referencia; actualización manual y conservación de progreso ante cambios.
- Archivos binarios solo visibles y abribles externamente, nunca transcritos byte a byte.

## Interfaz
- JavaFX escritorio claro; azul acero \`#4A6076\`, gris \`#E7EDF1\`, verde \`#26834B\` y rojo \`#B63E41\`.
- Controles rectangulares, editor central amplio, explorador lateral, inspector inicialmente oculto, consola inferior plegable, progreso global inferior.
- Pantalla completa y concentración independientes; paneles secundarios redimensionables, ocultables y restaurables.
- Fuente monoespaciada configurable, sonido opcional, teclado latinoamericano y composición correcta.
- Menús convencionales, iconografía reconocible, sin mensajes decorativos ni microcopias motivacionales.

## Futuro opcional
- Resaltadores internos por lenguaje (Java, Python, C/C++, TypeScript, etc.).
- Markdown fuente/renderizado en una misma pestaña.
- Comandos externos guardados por proyecto; no validación ni compilación obligatoria.
- Catálogo de pequeños proyectos de referencia por temas.

## Galería visual de referencia (noviembre de 2026)
- Los caracteres de la plantilla permanecen idénticos al original.
- Cada posición ya tecleada recibe un fondo rectangular verde pálido si coincide y rosa/rojo pálido si no coincide, como la referencia gráfica de mecanografía.
- Los caracteres pendientes permanecen sin fondo coloreado. El cursor se distingue por un subrayado azul fino y la línea actual sigue seleccionada discretamente.
- El fondo se aplica también a espacios: el usuario debe distinguir los aciertos y errores de espacios, llaves, puntuación y letras, sin sustituir su glifo.
- El tamaño de letra y la fuente mantienen dimensiones fijas por estado; un error nunca desplaza los símbolos siguientes.


## Auditoría UX/UI: composición de escritorio (noviembre 2026)
- Control global de tamaño del texto: **Slider horizontal dentro de la barra de estado**, con botones discretos y valor en puntos. Se eliminó el riel vertical que robaba espacio al editor.
- Los scrollbars mantienen funcionalidad vertical/horizontal, pero adoptan un acabado estrecho y rectangular compartido por todos los paneles, incluidos árbol, editor, listas y preferencias.
- La toolbar contiene operaciones de práctica frecuentes, **no** un interruptor de sonido. Sonidos de acierto/error y su prueba manual pertenecen a Herramientas → Preferencias → Sonido.
- Preferencias tiene navegación lateral constante, contenido organizado por tarea, tipografía y espaciado coherentes, muestra de código para seleccionar fuentes y acciones estándar Aceptar/Cancelar/Aplicar.
- Ningún estilo o microcopy debe revelar detalles de implementación o introducir elementos puramente decorativos.

## UX/UI: controles de progreso y preparación (noviembre 2026)
- En la esquina del encabezado Proyecto aparecen los botones de contraer y expandir. La expansión es limitada en proyectos enormes para no congelar JavaFX.
- Cada archivo o carpeta de referencia se distingue con estado **○ pendiente** o **✓ creado virtualmente**. El visto significa existencia de la estructura virtual, no que se haya terminado su mecanografía.
- El menú contextual de carpetas permite que el progreso global mida todo el proyecto o solamente los archivos de una carpeta y sus descendientes. El ámbito por carpeta es una selección temporal del workspace.
- El botón **Filtro** junto al progreso controla qué categorías de texto se tienen en cuenta; Markdown/documentación, archivos de configuración y otros textos están desactivados inicialmente.
- El porcentaje global tiene tres decimales y se calcula solo a partir de caracteres escritos correctamente. Los fragmentos omitidos y los errores no aportan progreso, aunque su estado se conserva en la sesión.
- El Slider horizontal de opacidad de Preferencias afecta únicamente a los caracteres pendientes de la plantilla, con lectura numérica auxiliar. Debe mantenerse el contraste de los aciertos y los errores.
- El selector de tipografía muestra junto al nombre una muestra de la misma fuente. El control transversal de tamaño continúa en el extremo derecho de la barra inferior.
- **Símbolos** copia el carácter; **Ctrl+V** en la superficie de práctica lo coloca en el próximo espacio de la plantilla. La selección de línea existente no debe reiniciar el cursor. Se rechaza el pegado de bloques largos para conservar el ejercicio de mecanografía.

## Reconstrucción virtual y paneles redimensionables (noviembre 2026)
- Una carpeta de referencia pendiente tiene la acción «Crear esta carpeta»; el menú de cualquier carpeta también muestra «Crear subcarpeta...» y «Crear archivo...». Crear descendientes exige haber creado antes el padre virtual; el mensaje explica ese orden.
- Un archivo de referencia pendiente tiene «Crear este archivo». Los elementos que no existan en el árbol de referencia se rechazan con diálogo legible, sin escribir archivos en disco.
- Las carpetas muestran «(N archivos)»; N incluye archivos de todas sus subcarpetas. El tooltip aclara esa semántica.
- La consola se aloja en un segundo SplitPane vertical; el usuario redimensiona el área del editor y la consola arrastrando un divisor horizontal. Cerrarla devuelve todo el espacio al editor.

## Refinamiento del explorador y foco (noviembre de 2026)
- Render del árbol compuesto: ícono independiente del texto del nombre, indicador de creación y conteo recursivo. Si el nombre es largo, puede requerir desplazamiento horizontal, pero el ícono no se sustituye por puntos suspensivos.
- Solo se ofrecen operaciones de creación y consulta: los archivos creados permanecen como hitos. El menú de archivo incluye «Mostrar en el Explorador», que revela su referencia real en Windows; otros sistemas abren la carpeta contenedora.
- Al abrir o volver a seleccionar una pestaña, la práctica recibe el foco del teclado mediante el pulso siguiente de JavaFX. Nunca se crea automáticamente un archivo, que sigue siendo requisito para mecanografiar.
- La opacidad deja de ser un cuadro numérico y pasa a Slider horizontal de 20 % a 100 % con valor porcentual legible.
