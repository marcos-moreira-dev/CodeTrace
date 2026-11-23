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
**Mostrar archivos de código**. El estado inicial es desactivado para conservar
la visión general de carpetas. Al activarlo, cada archivo de código compatible
ya reconocido por el análisis aparece como un nodo pequeño, unido a su
**carpeta inmediata** mediante una línea fina de pertenencia, **sin flecha**.
No es una dependencia, una llamada, una herencia ni un análisis semántico.

- Los archivos de código se detectan con el catálogo existente del analizador;
  no se añaden analizadores por versión del lenguaje ni se incorporan archivos
  de texto, imágenes o configuración ajenos a ese catálogo.
- Clic sobre un archivo: selecciona su carpeta en el inspector y resalta el
  archivo; doble clic: abre su pestaña habitual de CodeTrace.
- Las carpetas siguen siendo los nodos principales; mostrar/ocultar archivos
  **no modifica** sus posiciones, la disposición elegida ni las sesiones
  `.ctrace`. Las posiciones de archivo son temporales, deterministas y
  se recalculan después de desplazar una carpeta.
- Cada carpeta con archivos tiene una **isla compacta en rejilla** próxima
  al nodo padre. El algoritmo busca un espacio libre alrededor de la carpeta
  sin mover las posiciones originales. Reserva el rectángulo de la isla como
  una unidad: dos islas no mezclan archivos y ninguna cubre los rótulos de
  las carpetas. Si el espacio está ocupado, la isla puede quedar más lejos.
  Una conexión tenue une la carpeta con su isla, evitando centenares de
  líneas que se cruzan. El contorno indica pertenencia, no dependencia.
- Seleccionar una carpeta resalta **en morado suave toda su isla y sus
  archivos**; seleccionar un archivo usa un morado más intenso para ese
  documento. Los demás grupos permanecen neutros. Los archivos usan un
  pequeño pictograma vectorial de documento, también en el Canvas.
- Los archivos e islas se dibujan únicamente en la zona visible. El cálculo
  está limitado a **1.200** archivos secundarios como máximo, y el analizador
  existente también limita su inspección a 1.200 fuentes y el mapa a 180
  carpetas. Si se supera un límite, se indica que la vista es **parcial**;
  no se afirma que el resto de archivos no exista. La ausencia de
  solapamientos se aplica a las **islas y etiquetas**; las líneas entre
  carpetas pueden cruzarse y el usuario puede arrastrar nodos manualmente.
- La leyenda es contextual: **se oculta si hay un solo tipo de conexión** y
  muestra únicamente los tipos presentes (dependencia dirigida, contención
  entre carpetas y pertenencia de archivo). No se crean otras vistas.

## Navegación

Seleccionar un nodo abre el **inspector a la derecha**, con archivos de código
y subcarpetas directas, iconos del mismo servicio que usa el explorador y
listas con el estilo transversal de navegación. Doble clic o Intro sobre
un archivo abre su pestaña normal; sobre una subcarpeta, selecciona su nodo.
El botón de cerrar o Esc quitan la selección y ocultan el inspector.

La leyenda, cuando hay más de un tipo de conexión, permanece fija abajo a
la izquierda durante el paneo y zoom. Sus trazos y flechas vectoriales no
dependen de la fuente del sistema.

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
