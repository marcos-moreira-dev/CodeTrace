# Fundamentos — Especificación oficial (Java 21)

Ejemplos originales basados en la [JLS SE 21](https://docs.oracle.com/javase/specs/jls/se21/html/index.html). No constituyen una publicación oficial de Oracle.

68 unidades, 84 fuentes. Inventario de los 233 ejemplos numerados: [JLS21-INVENTARIO.tsv](JLS21-INVENTARIO.tsv). 55 identificadores con adaptación inicial; no equivale a cobertura semántica completa. Fragmentos sin numeración todavía pendientes de inventario. Los capítulos teóricos no deben forzarse a contener programas.

| Sección | Unidad | Estado |
| --- | --- | --- |
| 1.2 | 01-introduccion/01-02-programas-de-ejemplo/ArgumentosJls.java | Verificado en CI anterior |
| 3.10.1 | 03-estructura-lexica/03-10-01-literales-enteros/LiteralesEnterosJls.java | Verificado en CI anterior |
| 3.10.2 | 03-estructura-lexica/03-10-02-literales-flotantes/LiteralesFlotantesJls.java | Verificado en CI anterior |
| 3.10.3 | 03-estructura-lexica/03-10-03-literales-booleanos/LiteralesBooleanosJls.java | Verificado en CI anterior |
| 3.10.4 | 03-estructura-lexica/03-10-04-literales-caracter/LiteralesCaracterJls.java | Verificado en CI anterior |
| 3.10.5 | 03-estructura-lexica/03-10-05-literales-cadena/LiteralesCadenaJls.java | Verificado en CI anterior |
| 3.10.6 | 03-estructura-lexica/03-10-06-bloques-de-texto/BloqueTextoJls.java | Verificado en CI anterior |
| 3.10.7 | 03-estructura-lexica/03-10-07-secuencias-escape/EscapesJls.java | Verificado en CI anterior |
| 3.10.8 | 03-estructura-lexica/03-10-08-literal-null/LiteralNullJls.java | Verificado en CI anterior |
| 4.2.2; ejemplo 4.2.2-1 | 04-tipos-valores-variables/04-02-02-operaciones-enteras/OperacionesEnterasJls.java | Verificado en CI anterior |
| 5.1.2; ejemplo 5.1.2-1 | 05-conversiones-y-contextos/05-01-02-ampliacion-primitiva/AmpliacionPrimitivaJls.java | Verificado en CI anterior |
| 5.1.3; ejemplo 5.1.3-2 | 05-conversiones-y-contextos/05-01-03-reduccion-primitiva/ReduccionPrimitivaJls.java | Verificado en CI anterior |
| 3.10.5; ejemplo 3.10.5-1 | 03-estructura-lexica/03-10-05-identidad-entre-paquetes/saludos/IdentidadCadenas.java | Verificado en CI anterior |
| 3.10.6; ejemplo 3.10.6-1 | 03-estructura-lexica/03-10-06-01-comparacion-cadenas/TextoEquivalente.java | Verificado en CI anterior |
| 3.10.6; ejemplo 3.10.6-3 | 03-estructura-lexica/03-10-06-03-orden-transformaciones/TransformacionTexto.java | Verificado en CI anterior |
| 3.10.6; ejemplo 3.10.6-4 | 03-estructura-lexica/03-10-06-04-tipo-string/BloqueComoCadena.java | Verificado en CI anterior |
| 4.3.1; ejemplo 4.3.1-2 | 04-tipos-valores-variables/04-03-01-02-identidad/IdentidadValores.java | Verificado en CI anterior |
| 4.12.3; ejemplo 4.12.3-1 | 04-tipos-valores-variables/04-12-03-clases-de-variables/TiposVariables.java | Verificado en CI anterior |
| 4.12.4; ejemplo 4.12.4-1 | 04-tipos-valores-variables/04-12-04-final/ReferenciaFinal.java | Verificado en CI anterior |
| 4.12.5; ejemplo 4.12.5-1 | 04-tipos-valores-variables/04-12-05-valores-iniciales/ValoresIniciales.java | Verificado en CI anterior |
| 5.1.7 | 05-conversiones-y-contextos/05-01-07-boxing/Empaquetado.java | Verificado en CI anterior |
| 5.1.8 | 05-conversiones-y-contextos/05-01-08-unboxing-null/DesempaquetadoNulo.java | Verificado en CI anterior |
| 5.2 | 05-conversiones-y-contextos/05-02-constantes/AsignacionConstante.java | Verificado en CI anterior |
| 5.2 | 05-conversiones-y-contextos/05-02-error-reduccion/AsignacionInvalida.java | Verificado en CI anterior |
| 3.10.6; ejemplo 3.10.6-2 | 03-estructura-lexica/03-10-06-02-escapes-en-bloques/EscapeBloque.java | Implementado; pendiente CI |
| 4.3.1; ejemplo 4.3.1-1 | 04-tipos-valores-variables/04-03-01-01-creacion-objetos/CreacionObjetos.java | Implementado; pendiente CI |
| 4.4; ejemplo 4.4-1 | 04-tipos-valores-variables/04-04-01-miembros-variable-tipo/MiembrosTipo.java | Implementado; pendiente CI |
| 4.5.1; ejemplo 4.5.1-1 | 04-tipos-valores-variables/04-05-01-01-comodin-sin-limite/ComodinLibre.java | Implementado; pendiente CI |
| 4.5.1; ejemplo 4.5.1-2 | 04-tipos-valores-variables/04-05-01-02-comodin-acotado/ComodinAcotado.java | Implementado; pendiente CI |
| 4.8; ejemplo 4.8-1 | 04-tipos-valores-variables/04-08-01-tipo-raw/TipoRaw.java | Implementado; pendiente CI |
| 4.12.6; ejemplo 4.12.6-1 | 04-tipos-valores-variables/04-12-06-01-tipo-y-clase/TipoYClase.java | Implementado; pendiente CI |
| 5.0; ejemplo 5.0-2 | 05-conversiones-y-contextos/05-00-02-contextos-conversion/ContextosConversion.java | Implementado; pendiente CI |
| 5.1.3; ejemplo 5.1.3-1 | 05-conversiones-y-contextos/05-01-03-01-infinitos-a-enteros/InfinitosEnteros.java | Implementado; pendiente CI |
| 5.2; ejemplo 5.2-1 | 05-conversiones-y-contextos/05-02-01-asignacion-primitivos/AsignacionPrimitivos.java | Implementado; pendiente CI |
| 5.2; ejemplo 5.2-2 | 05-conversiones-y-contextos/05-02-02-asignacion-referencias/AsignacionReferencias.java | Implementado; pendiente CI |
| 5.2; ejemplo 5.2-3 | 05-conversiones-y-contextos/05-02-03-asignacion-arreglos/AsignacionArreglos.java | Implementado; pendiente CI |
| 5.5; ejemplo 5.5-3 | 05-conversiones-y-contextos/05-05-03-cast-incompatible/CastIncompatible.java | Implementado; pendiente CI |
| 5.6; ejemplo 5.6-1 | 05-conversiones-y-contextos/05-06-01-promocion-unaria/PromocionUnaria.java | Implementado; pendiente CI |
| 5.6; ejemplo 5.6-2 | 05-conversiones-y-contextos/05-06-02-promocion-binaria/PromocionBinaria.java | Implementado; pendiente CI |
| 6.3; ejemplo 6.3-1 | 06-nombres/06-03-01-alcance-clases/AlcanceClases.java | Implementado; pendiente CI |
| 6.3; ejemplo 6.3-2 | 06-nombres/06-03-02-variable-no-inicializada/AlcanceLocalInvalido.java | Implementado; pendiente CI |
| 6.4; ejemplo 6.4-1 | 06-nombres/06-04-01-sombreado-local-invalido/SombreadoInvalido.java | Implementado; pendiente CI |
| 6.4.1; ejemplo 6.4.1-1 | 06-nombres/06-04-01-01-sombreado-campo/SombreadoCampo.java | Implementado; pendiente CI |
| 7.5.1; ejemplo 7.5.1-1 | 07-paquetes-y-modulos/07-05-01-01-importacion-simple/app/ImportacionSimple.java | Implementado; pendiente CI |
| 8.1.1.1; ejemplo 8.1.1.1-1 | 08-clases/08-01-01-01-01-clase-abstracta/ClaseAbstracta.java | Implementado; pendiente CI |
| 8.3.1.1; ejemplo 8.3.1.1-1 | 08-clases/08-03-01-01-01-campos-static/CamposCompartidos.java | Implementado; pendiente CI |
| 8.4.9; ejemplo 8.4.9-1 | 08-clases/08-04-09-01-sobrecarga/SobrecargaMetodos.java | Implementado; pendiente CI |
| 8.9.3; ejemplo 8.9.3-1 | 08-clases/08-09-03-01-recorrer-enum/RecorrerEnum.java | Implementado; pendiente CI |
| 9.8; ejemplo 9.8-1 | 09-interfaces/09-08-01-interfaz-funcional/InterfazFuncional.java | Implementado; pendiente CI |
| 10.2; ejemplo 10.2-2 | 10-arreglos/10-02-02-declaradores-arreglos/DeclaradoresArreglos.java | Implementado; pendiente CI |
| 10.4; ejemplo 10.4-1 | 10-arreglos/10-04-01-acceso-arreglo/AccesoArreglo.java | Implementado; pendiente CI |
| 10.5; ejemplo 10.5-1 | 10-arreglos/10-05-01-almacenamiento-incompatible/AlmacenamientoArreglo.java | Implementado; pendiente CI |
| 10.6; ejemplo 10.6-1 | 10-arreglos/10-06-01-inicializador-arreglo/InicializadorArreglo.java | Implementado; pendiente CI |
| 10.7; ejemplo 10.7-1 | 10-arreglos/10-07-01-clonar-arreglo/ClonarArreglo.java | Implementado; pendiente CI |
| 10.7; ejemplo 10.7-2 | 10-arreglos/10-07-02-subarreglos-compartidos/ClonarMatriz.java | Implementado; pendiente CI |
| 10.8; ejemplo 10.8-2 | 10-arreglos/10-08-02-clases-arreglos/ClaseArreglos.java | Implementado; pendiente CI |
| 11.2.3; ejemplo 11.2.3-1 | 11-excepciones/11-02-03-01-catch-checked/CapturaCheckedInvalida.java | Implementado; pendiente CI |
| 11.3; ejemplo 11.3-1 | 11-excepciones/11-03-01-lanzar-capturar/LanzarCapturar.java | Implementado; pendiente CI |
| 12.4.1; ejemplo 12.4.1-1 | 12-ejecucion/12-04-01-01-orden-inicializacion/OrdenInicializacion.java | Implementado; pendiente CI |
| 14.11.3; ejemplo 14.11.3-1 | 14-bloques-sentencias-patrones/14-11-03-01-switch-caida/SwitchCaida.java | Implementado; pendiente CI |
| 14.14; ejemplo 14.14-1 | 14-bloques-sentencias-patrones/14-14-01-for-mejorado/ForMejorado.java | Implementado; pendiente CI |
| 14.21; ejemplo 14.21-1 | 14-bloques-sentencias-patrones/14-21-01-yield/YieldSwitch.java | Implementado; pendiente CI |
| 15.7.1; ejemplo 15.7.1-1 | 15-expresiones/15-07-01-01-orden-izquierda/OrdenIzquierda.java | Implementado; pendiente CI |
| 15.17.3; ejemplo 15.17.3-1 | 15-expresiones/15-17-03-01-resto-entero/RestoEntero.java | Implementado; pendiente CI |
| 15.18.1; ejemplo 15.18.1-1 | 15-expresiones/15-18-01-01-concatenacion/Concatenacion.java | Implementado; pendiente CI |
| 16; ejemplo 16-1 | 16-asignacion-definida/16-01-asignacion-definida/AsignacionDefinida.java | Implementado; pendiente CI |
| 16; ejemplo 16-2 | 16-asignacion-definida/16-02-asignacion-no-garantizada/AsignacionNoGarantizada.java | Implementado; pendiente CI |
| 16; ejemplo 16-3 | 16-asignacion-definida/16-03-final-ramas/FinalRamas.java | Implementado; pendiente CI |

Pendientes: revisar fidelidad y casos secundarios, completar los ejemplos numerados y fragmentos, migrar la carpeta anterior y publicar. No se incluye el PDF.
