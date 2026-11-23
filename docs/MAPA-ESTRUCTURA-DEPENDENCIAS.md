# Mapa de estructura y dependencias — versión 0.3.5

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

### Espaciado adaptativo del mismo grafo

Al activar **Mostrar archivos de código**, el mapa **separa temporalmente
las posiciones de las carpetas**, en filas y columnas según el tamaño de
las islas próximas. Así cada carpeta con archivos dispone de un espacio
más adecuado para mantener su isla cerca sin superponer rótulos. Se conserva
la relación espacial entre carpetas, no se crean otros mapas ni se alteran
las dependencias detectadas. El cálculo es una transformación visual:
la posición original de las carpetas sigue siendo la que se guarda en la
sesión `.ctrace`. Al ocultar los archivos se recuperan esas coordenadas.
Las ramas sin fuentes directas no fuerzan espacio adicional por sí solas.

La distancia final no puede garantizarse para todas las islas cuando la
densidad es extraordinaria o la distribución se personaliza arrastrando.
En esos casos sigue prevaleciendo la prohibición de solapamiento.

### Selección y movimiento

- Un clic sobre una carpeta resalta su isla y las aristas incidentes.
  Un clic en un archivo lo selecciona en el inspector, sin alterar la
  posición del archivo dentro del grupo. El doble clic sobre un archivo
  abre su pestaña, excepto en modo navegación segura.
- **Los archivos individuales no se pueden arrastrar.** Al arrastrar desde
  cualquier punto del rectángulo de una isla (incluido un archivo), se
  desplaza el **contenedor completo**, con los archivos formando un único
  bloque rígido, sin cambiar su distribución interna. Al soltar, ninguna
  carpeta ni otro grupo cambia de posición.
- Las islas no se pueden arrastrar encima de los rótulos de las carpetas,
  otras islas o fuera de las coordenadas admitidas. Una isla movida mantiene
  su enlace de pertenencia con la carpeta correspondiente.
- Los desplazamientos manuales de contenedores persisten en esa pestaña
  mientras esté abierta, incluso al ocultar y volver a mostrar los archivos
  y, si hay espacio, al mover la carpeta madre. No se guardan en `.ctrace`.
  **Restablecer distribución** reconstruye las islas automáticas.
- El vínculo entre la carpeta y la isla termina **antes de su borde**,
  no encima del pictograma del primer archivo. Los enlaces se pintan debajo
  de las superficies opacas del grupo para que no parezcan superpuestos.
- El inspector conserva selección única y distingue hover de selección.

### Límites

Las islas y los archivos se dibujan sobre el Canvas únicamente cuando están
dentro del área visible. Se respeta el límite existente de 1.200 archivos
de código inspeccionados, 1.200 nodos secundarios y 180 carpetas visibles.
Si un límite impide representar todo, se muestra «vista parcial». La
ausencia de flechas no demuestra ausencia de dependencias. Los símbolos
de la leyenda se ocultan cuando solo existe un tipo de relación; la casilla
de navegación permanece disponible.

## Carpetas sin archivos en una vista parcial

Las **líneas discontinuas** son contención de directorios, no herencia
entre clases. Un directorio puede tener **cero archivos de código directos**
y ser padre de otros directorios con código. Además, si se alcanza el límite
de 180 nodos, un directorio antecesor puede mostrarse mientras sus hijos
quedan fuera del mapa. Para evitar un padre aparentemente «desnudo», el
Canvas dibuja una **continuación de jerarquía discontinua** debajo del padre,
con el nombre de una subcarpeta real y el número de las demás omitidas.
Es una rama colapsada, **no una dependencia ni una subcarpeta inventada**.

El inspector enumera las subcarpetas directas **visibles y omitidas**,
diferenciando estas últimas con «fuera del mapa». Con doble clic sobre
una omitida se genera otro mapa del **mismo tipo**, limitado a esa subcarpeta,
para explorar sus descendientes. El mapa global mantiene su límite de
rendimiento; no se añaden nodos falsos para llenar el espacio. Las carpetas
realmente sin hijos no reciben avisos de continuación aunque otra rama del
proyecto haya sido truncada.

## Navegación

Seleccionar un nodo abre el **inspector a la derecha**, con archivos de código
y subcarpetas directas, iconos del mismo servicio que usa el explorador y
listas con el estilo transversal de navegación. Doble clic o Intro sobre
un archivo abre su pestaña normal; sobre una subcarpeta, selecciona su nodo.
El botón de cerrar o Esc quitan la selección y ocultan el inspector.

La leyenda, cuando hay más de un tipo de conexión, permanece fija abajo a
la izquierda durante el paneo y zoom. Sus trazos y flechas vectoriales no
dependen de la fuente del sistema.

**«Solo panear y hacer zoom»** es la casilla debajo de la leyenda.
Activada: arrastrar desde cualquier lugar (sobre nodos, islas o fondo)
mueve exclusivamente la cámara, sin cambiar el diseño del grafo; un
clic normal selecciona, pero el doble clic en un archivo no lo abre.
Desactivada: se permite mover carpetas e islas enteras. **En ambos
modos**, girar la rueda del ratón acerca o aleja el mapa sin necesidad
de Ctrl, centrando el zoom alrededor del puntero. La casilla se aplica
solo a la pestaña del mapa.

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

## Búsqueda dentro del mapa

La raíz se representa en rojo. El cuadro Buscar archivo resalta coincidencias parciales del nombre en naranja, sin distinguir mayúsculas; la selección manual permanece morada. Una búsqueda válida puede mostrar los archivos de código que estaban ocultos. No se desplazan nodos ni se abren archivos al escribir; Escape limpia las coincidencias. La búsqueda está limitada a los nodos de archivos representados por el mapa.
