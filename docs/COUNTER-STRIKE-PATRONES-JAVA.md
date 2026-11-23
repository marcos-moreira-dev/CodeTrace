# Counter-Strike — patrones de diseño para Java 21

**Pista didáctica experimental:** `java21/05-patrones-diseno-counter-strike/`.
**Hay 13 clientes de patrones individuales y un cliente integrador que utiliza Facade.**

Esta pista conserva el nombre del videojuego **Counter-Strike** como
contexto de aprendizaje. Es material educativo independiente: no es
una publicación oficial ni incorpora software, mapas, modelos, logotipos,
imágenes ni música del juego. Los nombres de escenarios se usan solo
como cadenas de texto.

La carpeta **`04-patrones-diseno/` permanece intacta** como colección de
ejemplos generales. Esta nueva carpeta `05` presenta los mismos
conceptos en un universo único y contiene **13 ejercicios, 67 archivos
Java y 13 clientes de consola**. Java 21 conserva un tipo nominal por
archivo: interfaces, records, enums, colaboradores y clases `Main`.

## Guía de lectura

Cada archivo tiene un encabezado con el patrón, el contexto concreto y
un glosario breve. Además, los métodos y los clientes contienen
**comentarios dentro del código**; explican por qué se crean listas de
casos, qué representa cada objeto, qué hacen los ciclos y por qué se
delega o se intercambia un componente.

No confundir contexto con implementación completa de un videojuego:
los casos manipulan cadenas, eventos y datos virtuales.

## Glosario

- **Partida:** encuentro completo entre dos equipos.
- **Ronda:** etapa de una partida; puede modificar el marcador.
- **CT / T:** abreviaturas habituales de los equipos antiterrorista y
  terrorista del videojuego, respectivamente.
- **Rehén:** personaje virtual que debe ser rescatado en algunos mapas.
- **MP9:** nombre de un subfusil representado como objeto de inventario
  del juego; el ejemplo no trata de funcionamiento ni empleo real.
- **Eco:** ronda de ahorro de créditos virtuales del juego.
- **Inventario:** listado de objetos disponibles para un personaje.
- **HUD:** información visual de la pantalla, como marcador o inventario.
- **Minimapa:** vista reducida del escenario virtual.
- **Ping:** tiempo de latencia de una conexión de red.
- **Mapa:** escenario donde transcurren las rondas; `de_dust2`,
  `de_inferno` y `cs_office` se usan únicamente como nombres textuales.
- **CSV:** formato de datos separado por comas, para exportar reportes.

## Relación entre el patrón y el ejemplo

| Patrón | Escenario de Counter-Strike | Elemento que estudiar |
|---|---|---|
| Abstract Factory | HUD claro y oscuro, marcador e inventario | Crear una familia completa de productos |
| Adapter | Ping del servidor en unidades antiguas | Traducir una interfaz legada |
| Builder | Configuración de una sala con mapa y rondas | Construcción escalonada con validación |
| Chain of Responsibility | Avisos de rehén, rival e inventario | Atender o delegar una petición |
| Command | Registrar y deshacer un punto CT | Encapsular una acción reversible |
| Composite | Árbol de recursos de mapas | Tratar archivos y carpetas uniformemente |
| Decorator | Etiquetas de aviso y número de ronda | Añadir capas sin cambiar el objeto base |
| Simple Factory | Crear un mensaje de chat | Centralizar construcción de objetos |
| Factory Method | Exportadores de puntuaciones | Elegir el producto mediante subclases |
| Observer | Marcador que actualiza el HUD | Notificar a varios suscriptores |
| Strategy | Política eco o de equipo | Intercambiar una decisión |
| Strategy (variante) | Puntuación casual o competitiva | Reutilizar un algoritmo intercambiable |
| Template Method | Resumen de ronda en texto o CSV | Fijar una secuencia y variar pasos |

## Cómo probarlos

Desde la raíz de una copia del repositorio, con Java 21 instalado:

```powershell
$out = Join-Path $env:TEMP "ct-practica-java"
New-Item -ItemType Directory -Force -Path $out | Out-Null
javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem .\src\main\resources\practice\java21 -Recurse -Filter *.java).FullName
java -cp $out codetrace.practica.counterstrike.cadena.MainCadenaCS
```

El ejemplo de cadena de responsabilidad debe mostrar:

```text
Rescate: rehén virtual liberado
Reconocimiento: rival marcado en el minimapa
Inventario: MP9 registrado como objeto virtual
Sin responsable: evento del servidor no reconocido
```

Los demás 12 clientes están bajo sus carpetas respectivas. La auditoría
Linux ejecuta los trece y compara sus salidas con
`scripts/salidas_practica.json`; la auditoría de Windows los ejecuta
con `-EjecutarClientes`. Los comentarios de cada archivo incluyen un
ejemplo de consola y las salidas de su cliente.

La pista Python existente continúa en `python/04-patrones-diseno/`; no
se ha reemplazado ni duplicado todavía con este recorrido `05`.

## Ejercicio final: una partida completa narrada por consola

La carpeta raíz `05-patrones-diseno-counter-strike/` contiene
`MainCounterStrike.java`. No es un ejemplo aislado adicional: es el
**cliente integrador**. No solicita entradas ni abre interfaces gráficas.
Ejecuta automáticamente tres rondas ficticias en `cs_office` con semilla
`42`, lo que permite repetir las mismas pruebas y salidas.

```text
05-patrones-diseno-counter-strike/
├── MainCounterStrike.java             <- entrada única del simulador
├── facade/
│   └── FachadaCounterStrike.java    <- entrada de alto nivel al subsistema MVC
├── infraestructura/
│   ├── Bando.java                   <- CT / T
│   ├── EstadoRehen.java             <- retenido / escoltado / rescatado
│   ├── ObjetoVirtual.java           <- inventario, coste y capacidad ficticios
│   ├── Jugador.java                 <- vida, armadura, créditos, compras
│   ├── Equipo.java                  <- miembros y bando
│   ├── Rehen.java                   <- ficha de NPC y progreso de rescate
│   ├── MapaPartida.java             <- cs_office y reloj ilustrativo
│   ├── EstadoPartida.java           <- estado y marcador de las rondas (modelo)
│   ├── VistaConsola.java            <- narra y muestra las fichas (vista)
│   └── ControladorPartida.java       <- coordina eventos y patrones
├── chain-of-responsibility/
├── observer/
├── strategy/
└── ... los restantes patrones originales de la pista 05
```

### Facade y MVC colaboran

`MainCounterStrike` llama a `FachadaCounterStrike.jugarPartida(42L)`. La fachada construye una vista y un controlador nuevos, y delega la ejecución. Así el cliente desconoce el ensamblaje de la sesión; MVC conserva sus responsabilidades internas. La fachada devuelve el estado final para que otro cliente pueda consultar el resultado sin interpretar la consola.

### Por qué usamos MVC

- **Modelo:** conserva jugadores, rehenes, equipos, inventario, reloj,
  puntuación e historial. No decide cómo imprimir el resultado.
- **Vista:** narra lo sucedido. No puede adjudicar una victoria ni inventar
  créditos. Explica qué significa la integridad, calma, progreso y estado
  de cada rehén.
- **Controlador:** aplica una semilla pseudoaleatoria, avanza las rondas
  y llama a los colaboradores de patrones existentes: Builder, Adapter,
  Composite, Strategy, Chain of Responsibility, Decorator, Command,
  Observer, Abstract Factory y Factory Method cuando corresponda.

**Glosario ampliado:** un rehén es un NPC (personaje no controlado por el
jugador) que en el modo rescate debe llegar a una zona segura. Aquí tiene
`nombre`, `ubicacion`, `integridad` (indicador 0–100), `calma`
(0–100), `escolta` (alias del CT acompañante), `progreso` (0–100 %)
y `EstadoRehen`. `RETENIDO → ESCOLTADO → RESCATADO` es una transición
simplificada. La propiedad `integridad` no representa reglas oficiales;
es material de práctica Java. Los jugadores tienen alias, bando,
vida/armadura ilustrativas, créditos e inventario de objetos virtuales.

Los comentarios son **locales y específicos**: por ejemplo, explican por
qué el bucle reinicia los rehenes y la vida entre rondas pero conserva el
marcador y los créditos; por qué la lista del `Main` de Chain of
Responsibility representa solicitudes y no responsables; qué papel
cumple `Random(42)`; y por qué el HUD consulta el inventario real del
jugador en vez de mostrar una MP9 antes de comprarla.

Ejecutar solo el cliente integrador después de compilar todo Java 21:

```powershell
java -cp $out codetrace.practica.counterstrike.MainCounterStrike
```

El simulador no necesita un motor de juego, físicas, imágenes ni librerías
adicionales. El propósito es la **mecanografía, orientación a objetos y
colaboración entre patrones**. Los resultados de la narración se cotejan
con `scripts/salidas_practica.json` durante la auditoría automatizada.

### Comentarios ligados al dominio

Las fichas de Elena y Omar se registran en la lista de la partida; la variable `elegido` apunta al mismo NPC que consulta la vista. El atributo `ubicacion` conserva su lugar de aparición, mientras `progreso` y `estado` representan la evacuación abstracta. El historial guarda una descripción de la ronda antes de que se reinicien las fichas. El cliente raíz contiene un extracto real de las tres rondas en comentarios; el manifiesto conserva las 52 líneas completas para la comparación automática.
