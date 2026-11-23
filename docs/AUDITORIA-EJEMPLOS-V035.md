# Auditoría de organización de ejercicios — v0.3.5 experimental

**Fecha:** 23 de noviembre de 2026 · **Estado:** revisión estructural y regresión automatizada; inspección visual y validación de cada salida en todos los sistemas no equivalen a una auditoría semántica exhaustiva.

## Alcance y criterio

La biblioteca mantiene **un único catálogo** con **826 archivos de referencia en 14 lenguajes**, repartidos entre fundamentos, estructuras de datos, algoritmos y patrones. Un archivo es una unidad de mecanografía, **no necesariamente un ejercicio autónomo**. El paquete Java 21 contiene **154 archivos**. El catálogo v0.3.4 tenía 813: este cambio suma 13 netos por separar roles Java y sustituir un contenedor de tipos anidados.

La revisión de integridad recorre todos los elementos del catálogo: ruta segura, recurso real, código no vacío, cabecera de versión y comando de compilación/comprobación. Los clientes representativos se compilan y ejecutan mediante los verificadores por plataforma. Esto **no equivale** a validar semánticamente cada línea de los 826 archivos en todos los compiladores.

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

Las nuevas fuentes conservan cabeceras con Java 21, instrucciones de compilación, cliente y salida esperada. Se actualizaron los consumidores para usar los contratos independientes y el catálogo de recursos. La biblioteca pasa a caché `v9` para no reutilizar los archivos obsoletos de `v8`.

## Cómo verificar

- `scripts/verificar-biblioteca.sh`: integridad de las 826 rutas, compilación integral de los Java 21, ejecución de clientes Java y comprobaciones representativas de otros lenguajes.
- `scripts/auditar-biblioteca.ps1`: integridad y estructura desde Windows PowerShell 5.1; con `-EjecutarClientes`, compila y ejecuta los clientes Java seleccionados en un temporal.
- `BuiltInLibraryTest`: versión del catálogo, extracción offline, compilación de todo Java y cabeceras de los ejercicios.
- `PracticeCurriculumStructureAuditTest`: inventario por idioma y diagnóstico de tipos Java de nivel superior o anidados; la detección por texto es **heurística**, no sustituye al compilador.
- Validar en CodeTracer que cada nuevo archivo aparece en el árbol y que puede practicarse independientemente sin introducir análisis sintáctico por lenguaje.

**Límite consciente:** los ejercicios existentes de otros lenguajes conservan su estructura, salvo fallos evidentes detectados por las comprobaciones. Una reorganización masiva de cientos de módulos sin demanda didáctica real sería sobreingeniería y podría romper los comandos y la progresión de aprendizaje.
