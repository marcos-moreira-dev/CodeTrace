# Biblioteca de práctica CodeTrace — criterios y estructura

La biblioteca es una referencia **de solo lectura**, no un compilador: las
fuentes se entregan dentro del programa, se materializan en una carpeta local
propia y la sesión de mecanografía se guarda por separado en .ctrace.

## Organización de aprendizajes

1. **Sintaxis:** variables, tipos, flujo, funciones y peculiaridades del idioma.
2. **Estructuras:** pilas, listas, colas, arreglos y uso de memoria.
3. **Algoritmos:** búsqueda binaria, Euclides, ordenación, **Dijkstra** y
   ejercicios de costo computacional, con complejidad explicada cuando procede.
4. **Patrones y proyectos:** Observer y Factory Method como conjuntos de
   archivos con interfaces, implementaciones, fábricas, observadores y demos.
5. **Integración:** comandos, módulos, scripts y herramientas externas.
6. **Ensambador NASM x86-64:** sintaxis Intel, registros, pila, memoria,
   bucles, Euclides, Fibonacci y llamadas Linux. No prometer que los ejemplos
   con llamadas al sistema Linux son ejecutables Windows sin modificaciones.

### Convenciones de inclusión

- Cada archivo fuente indica en sus primeras líneas **versión de referencia**
  y **comando de compilación/comprobación**.
- Los proyectos multiarhivo permanecen en la MISMA carpeta de caso de uso.
  CodeTrace no copia dependencias de forma mágica ni los ejecuta
  automáticamente: sirven para estudiar relaciones reales entre archivos.
- Los patrones se enseñan donde tienen sentido para el paradigma y el
  alcance. No forzar Factory Method en CMD ni POO ficticia en ensamblador.
- Cada ejemplo distingue el lenguaje, su versión, dependencias y sistema
  operativo (especialmente NASM y scripts Shell).
- Se permite omitir las declaraciones de dependencias del archivo actual,
  pero nunca se omiten en masa llamadas o código arbitrario por heurísticas.
- Los emojis pueden visualizarse como anotaciones; no son caracteres que
  deban teclearse y no suman puntos de velocidad ni bloquean finalizar.
- Los originales de referencia no se sobrescriben durante la práctica.

## Pruebas de aceptación

- Enumerar 14 paquetes, incluidos los 13 previos y NASM, desde interfaz.
- Comprobar integridad del catálogo y existencia de los archivos
  multiarhivo; validar que los Java de la biblioteca compilan juntos.
- Ejecutar manualmente un Factory Method, un Observer y Dijkstra en
  distintos lenguajes, según compiladores disponibles.
- Verificar compilación NASM \`nasm -f elf64\` dentro de WSL/Linux si está
  disponible; no exigir NASM para ejecutar CodeTrace.
- No fusionar en \`main\` ni publicar instalador hasta aceptar esos casos.
