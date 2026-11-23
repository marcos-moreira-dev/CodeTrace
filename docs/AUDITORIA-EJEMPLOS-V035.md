# Auditoría de organización de ejercicios — v0.3.5 experimental

**Fecha:** 23 de noviembre de 2026 · **Estado:** revisión estructural y regresión automatizada; inspección visual y validación de cada salida en todos los sistemas no equivalen a una auditoría semántica exhaustiva.

## Alcance y criterio

La biblioteca mantiene **un único catálogo** con **918 archivos de referencia en 14 lenguajes**, repartidos entre fundamentos, estructuras de datos, algoritmos y patrones. Un archivo es una unidad de mecanografía, **no necesariamente un ejercicio autónomo**. El paquete Java 21 contiene **244 archivos**. El catálogo v0.3.4 tenía 813: este cambio suma 105 netos por separar roles Java y sustituir un contenedor de tipos anidados.

La revisión de integridad recorre todos los elementos del catálogo: ruta segura, recurso real, código no vacío, cabecera de versión y comando de compilación/comprobación. Los clientes representativos se compilan y ejecutan mediante los verificadores por plataforma. Esto **no equivale** a validar semánticamente cada línea de los 918 archivos en todos los compiladores.

## Decisión de diseño didáctico

- **Java 21:** cada contrato o tipo con identidad pedagógica propia —interfaz, clase colaboradora, `record`, `enum`— debe tener su propio archivo y un nombre que coincida con el tipo. La clase `Main...` se mantiene como cliente de consola. Para tipos auxiliares **privados y estrictamente internos** (como un nodo de una lista enlazada), una clase anidada puede ser la representación más natural; no se fragmenta mecánicamente cada implementación.
- **C y C++:** separar archivos de cabecera y de implementación donde ya existe un contrato público; mantener funciones auxiliares locales o privadas dentro de la misma unidad de traducción.
- **Python, JavaScript, TypeScript, Ruby, Kotlin, C#, Go y demás lenguajes:** seguir sus convenciones de módulos, paquetes, ámbitos y ejecutables, **sin aplicar artificialmente la regla Java de un tipo por archivo**.
- **Shell, PowerShell, CMD y NASM:** la unidad principal suele ser un script o un módulo, no una clase. Conservar las funciones y subrutinas que deban estudiarse como un conjunto.
- Nunca añadir archivos auxiliares solo para aumentar artificialmente la cantidad de nodos de CodeTracer. La claridad del ejercicio está por encima de la cantidad.

## Reestructuración aplicada a Java

| Unidad | Antes | Después |
|---|---|---|
| Fábrica simple | `Factory.java` contenía interfaz `Mensaje` y `record Correo` | `Factory.java`, `Mensaje.java`, `Correo.java`, `MainFactoryBasica.java` |
| `record` y `enum` | `RecordsYEnums.java` declaraba `Estado` y `Pedido` internamente | `Estado.java`, `Pedido.java`, `RecordsYEnums.java` |
| Strategy básico | `Descuento` anidada dentro de `Strategy` | `Descuento.java`, `Strategy.java`, `MainStrategy.java` |
| Observer básico | `Escucha` anidada dentro de `Observador` | `Escucha.java`, `Observador.java`, `MainObservadorBasico.java` |
| Command con deshacer | `Comando.java` mezclaba contrato e implementación `AgregarTexto` | `Comando.java`, `AgregarTexto.java`, `Editor.java`, `MainCommand.java` |
| Composite | `Elemento.java` anidaba `Archivo` y `Carpeta` | `Elemento.java`, `Archivo.java`, `Carpeta.java`, `MainComposite.java` |
| Abstract Factory | `FamiliaInterfaz.java` agrupaba cinco roles anidados | `Boton.java`, `Dialogo.java`, `Fabrica.java`, `FabricaClara.java`, `FabricaOscura.java`, `MainAbstractFactory.java` |
| Template Method | `ProcesoReporte.java` incluía otras dos clases de nivel superior | `ProcesoReporte.java`, `ReporteTexto.java`, `ReporteCsv.java`, `MainTemplateMethod.java` |
| Callbacks | `Receptor` estaba dentro del cliente `Callbacks` | `Receptor.java` y `Callbacks.java` |
| Tipos de grafo | `Orientacion` era un `enum` público anidado en `Grafo` | `Orientacion.java`, `Grafo.java`, `MainTiposGrafo.java` |

Las nuevas fuentes conservan cabeceras con Java 21, instrucciones de compilación, cliente y salida esperada. Se actualizaron los consumidores para usar los contratos independientes y el catálogo de recursos. La biblioteca pasa a caché `v13` para no reutilizar los archivos obsoletos de `v8`.

## Hallazgo de la revisión automática de Java

El primer inventario encontró **una fuente con varios tipos de nivel superior**
(`ProcesoReporte.java`) y **22 archivos con tipos anidados**. La fuente de
Template Method ya se dividió. También se extrajeron el contrato `Receptor`
y el `enum Orientacion`, con responsabilidad didáctica explícita.

Los tipos anidados restantes se revisan como **detalles de implementación**,
no como incumplimientos automáticos: `Nodo` privado de una estructura, estado
interno de algoritmos (`Arista`, `Resultado`), datos locales para un
ejemplo y el `Builder` anidado que Java utiliza idiomáticamente.
Separarlos todos por obligación aumentaría archivos sin mejorar su estudio.
La nueva prueba **rechaza** varios tipos de nivel superior por archivo y
comprueba las unidades que sí se separaron.

## Cómo verificar

- `scripts/verificar-biblioteca.sh`: integridad de las 918 rutas, compilación integral de los Java 21, ejecución de clientes Java y comprobaciones representativas de otros lenguajes.
- `scripts/auditar-biblioteca.ps1`: integridad y estructura desde Windows PowerShell 5.1; con `-EjecutarClientes`, compila y ejecuta los clientes Java seleccionados en un temporal.
- `BuiltInLibraryTest`: versión del catálogo, extracción offline, compilación de todo Java y cabeceras de los ejercicios.
- `PracticeCurriculumStructureAuditTest`: inventario por idioma y diagnóstico de tipos Java de nivel superior o anidados; la detección por texto es **heurística**, no sustituye al compilador.
- Validar en CodeTracer que cada nuevo archivo aparece en el árbol y que puede practicarse independientemente sin introducir análisis sintáctico por lenguaje.

**Límite consciente:** los ejercicios existentes de otros lenguajes conservan su estructura, salvo fallos evidentes detectados por las comprobaciones. Una reorganización masiva de cientos de módulos sin demanda didáctica real sería sobreingeniería y podría romper los comandos y la progresión de aprendizaje.

## Ampliación didáctica: Arena Táctica

En la rama experimental, **todos los patrones Java 21 y Python** reciben una
introducción breve sobre el dominio compartido, el objetivo del patrón y un
glosario de términos del videojuego. Los clientes mantienen su carácter de
**ejercicios de consola**, sin motores gráficos ni implementación de armas.
La temática funciona como contexto de ejemplos ya existentes (interfaces,
reportes, inscripciones, eventos, recursos) sin modificar sus algoritmos
innecesariamente.

Se añade **Chain of Responsibility** como ejercicio paralelo en ambos lenguajes:
Java separa interfaz abstracta, handlers, enum, record y Main en **siete
archivos**; Python utiliza un módulo de colaboradores y otro de ejecución (**dos
archivos**), conforme a las convenciones de cada lenguaje. Se comprueba que
sus salidas de consola coincidan. El escenario incluye un rehén *virtual*
y la etiqueta MP9 de equipamiento ficticio, explicados en los comentarios.

Ver [glosario y mapa de patrones](ARENA-TACTICA-PATRONES.md).

## Pista separada de Counter-Strike (Java 21)

Se añade `05-patrones-diseno-counter-strike/` con **67 archivos y 13 clientes de consola** para 13 patrones o variantes de patrones ya representados en la colección general. Mantener ambos recorridos evita destruir los ejemplos originales. Los nuevos ejemplos incluyen glosario de términos del videojuego, tipos por archivo y comentarios **dentro** de listas, iteraciones, llamadas y métodos. [Guía detallada](COUNTER-STRIKE-PATRONES-JAVA.md).

## Ejercicio integrador de Counter-Strike (MVC)

Se incorpora un `MainCounterStrike.java` en la raíz de la pista `05`
y diez archivos de apoyo dentro de `infraestructura/`. El conjunto
conserva **13 patrones independientes** y suma un **simulador automático de
tres rondas** con `Random(42)`, fichas de jugadores y rehenes, reloj,
inventario, créditos, marcador e historial. Su salida completa y
reproducible se añade a `scripts/salidas_practica.json` y se compara
con el resultado de la ejecución Java 21 durante la auditoría Linux.

La separación modelo/vista/controlador permite explicar cada modificación
de estado con comentarios localizados, sin convertir `Main` en una clase
monolítica. [Detalles y glosario](COUNTER-STRIKE-PATRONES-JAVA.md).

## Fachada del simulador

Se incorpora `facade/FachadaCounterStrike.java` como patrón GoF aplicado al cliente integrador raíz. Los 13 clientes individuales se mantienen sin cambios. La pista 05 suma 79 fuentes (67 originales, 11 de integración MVC y una fachada). Los comentarios de modelo, vista y controlador describen los objetos y eventos concretos de Counter-Strike; la muestra de consola del Main recoge las tres rondas. La salida ejecutable no cambia.
