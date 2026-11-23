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

## Navegación

Seleccionar un nodo abre el **inspector a la derecha**, con archivos de código
y subcarpetas directas, iconos del mismo servicio que usa el explorador y
listas con el estilo transversal de navegación. Doble clic o Intro sobre
un archivo abre su pestaña normal; sobre una subcarpeta, selecciona su nodo.
El botón de cerrar o Esc quitan la selección y ocultan el inspector.

La leyenda permanece fija abajo a la izquierda durante el paneo y zoom.
Sus trazos y puntas de flecha son vectoriales, independientes de la fuente
del sistema; la flecha indica dependencia y la línea discontinua indica
contención sin dirección.

Un clic primario o central en el área de trabajo cierra el menú contextual
«Centrar y ajustar a ventana / Restablecer distribución», sin interferir
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
