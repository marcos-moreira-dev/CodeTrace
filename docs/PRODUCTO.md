# CodeTrace — producto planificado

CodeTrace propone una práctica tranquila de reconstrucción manual de software, sin curso ni asistente. La referencia es una carpeta real que CodeTrace no modifica. La reconstrucción es un árbol virtual persistible en \`.ctrace\`, no una copia física.

## Escritura prevista
- Código fantasma y caracteres tecleados **sobre la misma superficie**, nunca dos campos de edición.
- Verde para coincidencia y rojo para diferencia; los errores no bloquean el avance.
- Edición mínima natural: selección, cursor, Retroceso, Supr, deshacer.
- Sin autocompletado, cierre automático de llaves o reformato.
- Comparación sobre caracteres confirmados (acentos, composición Unicode, teclas muertas).
- Omisiones explícitas como imports se separan de los caracteres realmente escritos.
- Estadísticas positivas: solo caracteres escritos correctamente, sin duplicar corregidos.

## Árbol y referencia
- Crear, renombrar, copiar, mover, eliminar elementos virtuales.
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
