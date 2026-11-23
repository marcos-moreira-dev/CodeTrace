# Arena Táctica — universo didáctico de patrones Java y Python

**Estado:** rama experimental v0.3.5. **Alcance:** la sección
`04-patrones-diseno/` de Java 21 y Python.

## Idea

Arena Táctica es una **partida competitiva ficticia por equipos**, una
temática inspirada en videojuegos tácticos, sin dependencia de marcas,
personajes, recursos de terceros ni bibliotecas de videojuegos.
Los ejercicios son programas de **consola**: estudian software y sus
relaciones, no técnicas reales de combate. Las salidas existentes se
conservan salvo en el nuevo ejercicio de cadena de responsabilidad.

En cada archivo encontrarás un comentario corto con **dominio**, **patrón**
y **glosario**. Así puedes practicar mecanografía de archivos individuales
sin tener que buscar el significado de términos de juegos.
No hay que aprender estos términos de memoria para comprender el patrón.

## Glosario básico del dominio

- **Partida:** encuentro completo entre dos equipos en el videojuego.
- **Ronda:** etapa corta que contribuye al resultado de una partida.
- **Equipo:** conjunto de jugadores que comparten un objetivo virtual.
- **Mapa:** escenario digital donde se desarrolla una ronda.
- **Rehén:** personaje virtual retenido que, según las reglas del juego,
  debe ser rescatado. Aquí aparece solo como un **evento de objetivo**.
- **MP / MP5 / MP7 / MP9:** nombres asociados a **subfusiles** que pueden
  aparecer en los inventarios de videojuegos tácticos. En los ejercicios
  funcionan únicamente como **etiquetas de objetos virtuales**, sin
  instrucciones de uso, rendimiento ni comportamiento de armas reales.
- **Inventario:** lista de objetos virtuales asignados a un personaje.
- **Minimapa:** vista reducida del escenario, útil para interpretar
  información de la interfaz, no para analizar movimientos reales.
- **HUD:** indicadores visuales superpuestos a la pantalla, como tiempo,
  marcador o estado del jugador.
- **Marcador:** registro de puntuaciones y resultados de las rondas.
- **Torneo:** competición que agrupa varias partidas.
- **Servidor de partida:** software o computadora que coordina una sesión.
- **Reporte:** resumen de estadísticas o sucesos del juego.
- **Inscripción:** registro de un equipo o jugador en el torneo.

## Qué estudia cada patrón

| Patrón | Ejemplo de Arena Táctica | Concepto de software |
|---|---|---|
| Abstract Factory | Componentes visuales de un HUD | Familias compatibles de objetos |
| Adapter | Telemetría de servidor con unidades antiguas | Adaptar una interfaz externa |
| Builder | Solicitud de estadísticas de torneo | Construcción por etapas |
| Command | Edición de notas de ronda con deshacer | Acciones encapsuladas |
| Composite | Árbol de mapas y recursos | Composición hoja/contenedor |
| Decorator | Notificaciones de equipo con prefijos | Comportamiento agregado |
| Factory / Factory Method | Mensajes y exportadores de resultados | Separar creación de uso |
| Observer | Marcador con espectadores suscritos | Propagación de cambios |
| Strategy | Políticas de inscripción o descuentos | Intercambio de algoritmos |
| Template Method | Reportes de torneo en varios formatos | Esqueleto común con variantes |
| **Chain of Responsibility** | Reportes de objetivo, reconocimiento e inventario | Delegar hasta hallar un responsable |

**Diferencia pedagógica deliberada:** Java conserva un tipo nominal
por archivo cuando aporta claridad. Python puede reunir varios tipos de
colaboración en un módulo y utilizar `dataclass`, `Enum` y herencia
ligera. La equivalencia está en el **problema y el comportamiento**, no
en forzar una estructura de archivos idéntica.

## Cadena de responsabilidad: resultado esperado en ambos lenguajes

```text
Objetivo: rehén virtual rescatado
Reconocimiento: rival visto en el minimapa
Equipamiento: MP9 registrado en inventario virtual
Sin responsable: señal desconocida
```

La última petición demuestra qué ocurre cuando ningún eslabón reconoce
el evento. No tiene relación con una acción real: es una prueba de
delegación entre objetos.

Los verificadores ejecutan ambos clientes y comparan exactamente sus
cuatro líneas. No requieren dependencias externas al JDK 21 y Python.
