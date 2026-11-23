# Mapa de estructura y dependencias — avance 0.3.5

## Alcance

El mapa trabaja **exclusivamente con la carpeta seleccionada y sus descendientes**.
Cada nodo representa una carpeta que contiene archivos de código compatibles o
es antecesora de otra carpeta que los contiene. El nombre visible corresponde a
la carpeta real; no se infiere que un directorio sea un paquete del lenguaje.

## Conexiones

- **Línea discontinua sin punta:** carpeta padre contiene subcarpeta inmediata.
- **Flecha continua A → B:** al menos un archivo situado directamente en A
  declara una referencia explícita que CodeTrace consigue resolver a un
  archivo o paquete local situado en B. A depende de B.
- Las relaciones dentro de una misma carpeta no producen flechas circulares.
- Varias importaciones de A hacia B se agregan en una sola flecha dirigida.
- No se dibujan referencias externas a la carpeta analizada.

La ausencia de flechas **no demuestra ausencia de dependencias**: CodeTrace no
compila el proyecto, no ejecuta sus archivos y no resuelve todos los usos
implícitos de símbolos. El mapa es una exploración estática limitada.

## Archivos como nodos secundarios (opcional)

Desde el menú contextual del **único mapa**, activar o desactivar
**Mostrar archivos de código**. Por defecto se ocultan para conservar la
visión general de carpetas. Los archivos reconocidos por el analizador
actual se muestran como nodos secundarios: **no se añade análisis semántico**.

### Isla y proximidad

- Cada carpeta con código tiene una isla en rejilla. Se reservan primero las
  islas pequeñas para evitar que una carpeta enorme relegue a una de un solo
  archivo hasta el extremo del mapa. Se busca la posición libre más cercana
  entre ocho orientaciones alrededor de la carpeta.
- El rectángulo completo de una isla se reserva para que no se mezcle con
  otras islas ni cubra etiquetas de carpetas. Si el proyecto está muy lleno,
  algunos grupos todavía pueden quedar lejos: es la contrapartida de impedir
  solapamientos **sin mover las carpetas existentes**.
- Una línea **continua, sin punta de flecha** une la carpeta con su isla.
  No representa dependencia ni importación; una flecha continua sí indica
  dependencia detectada y una línea discontinua, contención de subcarpetas.

### Selección y movimiento

- Clic sobre una carpeta: se resalta su isla en morado y se engrosan sus
  aristas incidentes (dependencias, jerarquía y pertenencia).
- Clic sobre un archivo: se resalta en morado intenso y se enfatiza el
  vínculo **carpeta → borde de la isla**, sin trazar una línea gruesa que
  atraviese otras etiquetas del grupo. Los archivos movidos individualmente
  sí conservan un vínculo directo hasta su posición manual. Doble clic abre
  el archivo en su pestaña habitual cuando está desactivado el modo de navegación.
- El inspector derecho tiene **selección única**. El color de hover es más
  tenue y distinto del de la selección para que no parezcan dos archivos
  seleccionados al mismo tiempo.
- **Arrastrar un archivo** lo mueve individualmente; las otras carpetas e
  islas no se mueven. Si sale de su isla aparece su enlace directo a la
  carpeta. El movimiento que cubra otra etiqueta de carpeta, archivo o isla
  ajena se rechaza para preservar la legibilidad.
- El desplazamiento manual del archivo se guarda **solo mientras la pestaña
  del mapa está abierta**, incluso al ocultar y volver a mostrar archivos.
  Si se mueve su carpeta, el archivo intenta seguirla conservando su
  posición relativa, siempre que el destino permanezca libre.
  **Restablecer distribución** o cerrar la pestaña descarta estos ajustes:
  no se guardan en sesiones `.ctrace`. Las posiciones normales de las
  carpetas sí siguen guardándose como antes.
- Todas las conexiones del grafo se dibujan **debajo** de las islas y
  etiquetas de archivos. Sus fondos opacos evitan que líneas de dependencia,
  contención o pertenencia tapen visualmente nombres de documentos.

### Límites

Las islas y los archivos se dibujan sobre el Canvas únicamente cuando están
dentro del área visible. Se respeta el límite existente de 1.200 archivos
de código inspeccionados, 1.200 nodos secundarios y 180 carpetas visibles.
Si un límite impide representar todo, se muestra «vista parcial». La
ausencia de flechas no demuestra ausencia de dependencias. Los símbolos
de la leyenda se ocultan cuando solo existe un tipo de relación; la casilla
de navegación permanece disponible.

## Navegación

Seleccionar un nodo abre el **inspector a la derecha**, con archivos de código
y subcarpetas directas, iconos del mismo servicio que usa el explorador y
listas con el estilo transversal de navegación. Doble clic o Intro sobre
un archivo abre su pestaña normal; sobre una subcarpeta, selecciona su nodo.
El botón de cerrar o Esc quitan la selección y ocultan el inspector.

La leyenda, cuando hay más de un tipo de conexión, permanece fija abajo a
la izquierda durante el paneo y zoom. Sus trazos y flechas vectoriales no
dependen de la fuente del sistema.

**«Solo panear y desplazar»**, casilla situada debajo de la leyenda, activa
la exploración segura: puedes arrastrar desde cualquier lugar del Canvas
(incluso sobre nodos y archivos) para mover la **cámara**, y usar la rueda
sin Ctrl para acercar o alejar. Los clics simples siguen seleccionando
elementos y muestran el inspector, pero los arrastres no mueven nodos ni
archivos y el doble clic en un archivo no abre otra pestaña por accidente.
Desactivarla restaura los arrastres normales y Ctrl+rueda para el zoom.
La preferencia afecta solo a la pestaña actual del mapa.

Un clic primario o central en el área de trabajo cierra el menú contextual
«Centrar y ajustar a ventana / Restablecer distribución /
Mostrar archivos de código», sin interferir
con el arrastre, zoom o selección. El clic secundario mantiene disponible
ese menú.

El cálculo es solo de lectura y mantiene límites de seguridad de archivos,
nodos, dependencias, cancelación y carpetas generadas. La posición del grafo
se guarda en sesiones .ctrace; la huella del nuevo modelo impide reutilizar
posiciones antiguas de nodos que representaban archivos.

## Versiones y reversión

Este desarrollo se realiza en una rama separada identificada como
**0.3.5-SNAPSHOT**. El tag y la publicación **v0.3.4** continúan disponibles,
y la rama main no debe sustituirse antes de aprobar la nueva interfaz.
Ningún ejercicio de la biblioteca se modifica ni se actualizan las versiones
didácticas de sus lenguajes.
