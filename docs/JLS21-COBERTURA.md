# Fundamentos — Especificación oficial (Java 21)

Ejemplos originales basados en la [JLS SE 21](https://docs.oracle.com/javase/specs/jls/se21/html/index.html). No constituyen una publicación oficial de Oracle.

112 unidades, 180 fuentes. [Inventario de los 233 ejemplos numerados](JLS21-INVENTARIO.tsv): 99 identificadores con adaptación inicial; no equivale a cobertura semántica completa. Fragmentos sin numeración pendientes de inventario.

| Sección | Unidad | Estado |
| --- | --- | --- |
| 1.2 | 01-introduccion/01-02-programas-de-ejemplo/ArgumentosJls.java | Verificado en CI de segunda entrega |
| 3.10.1 | 03-estructura-lexica/03-10-01-literales-enteros/LiteralesEnterosJls.java | Verificado en CI de segunda entrega |
| 3.10.2 | 03-estructura-lexica/03-10-02-literales-flotantes/LiteralesFlotantesJls.java | Verificado en CI de segunda entrega |
| 3.10.3 | 03-estructura-lexica/03-10-03-literales-booleanos/LiteralesBooleanosJls.java | Verificado en CI de segunda entrega |
| 3.10.4 | 03-estructura-lexica/03-10-04-literales-caracter/LiteralesCaracterJls.java | Verificado en CI de segunda entrega |
| 3.10.5 | 03-estructura-lexica/03-10-05-literales-cadena/LiteralesCadenaJls.java | Verificado en CI de segunda entrega |
| 3.10.6 | 03-estructura-lexica/03-10-06-bloques-de-texto/BloqueTextoJls.java | Verificado en CI de segunda entrega |
| 3.10.7 | 03-estructura-lexica/03-10-07-secuencias-escape/EscapesJls.java | Verificado en CI de segunda entrega |
| 3.10.8 | 03-estructura-lexica/03-10-08-literal-null/LiteralNullJls.java | Verificado en CI de segunda entrega |
| 4.2.2; ejemplo 4.2.2-1 | 04-tipos-valores-variables/04-02-02-operaciones-enteras/OperacionesEnterasJls.java | Verificado en CI de segunda entrega |
| 5.1.2; ejemplo 5.1.2-1 | 05-conversiones-y-contextos/05-01-02-ampliacion-primitiva/AmpliacionPrimitivaJls.java | Verificado en CI de segunda entrega |
| 5.1.3; ejemplo 5.1.3-2 | 05-conversiones-y-contextos/05-01-03-reduccion-primitiva/ReduccionPrimitivaJls.java | Verificado en CI de segunda entrega |
| 3.10.5; ejemplo 3.10.5-1 | 03-estructura-lexica/03-10-05-identidad-entre-paquetes/saludos/IdentidadCadenas.java | Verificado en CI de segunda entrega |
| 3.10.6; ejemplo 3.10.6-1 | 03-estructura-lexica/03-10-06-01-comparacion-cadenas/TextoEquivalente.java | Verificado en CI de segunda entrega |
| 3.10.6; ejemplo 3.10.6-3 | 03-estructura-lexica/03-10-06-03-orden-transformaciones/TransformacionTexto.java | Verificado en CI de segunda entrega |
| 3.10.6; ejemplo 3.10.6-4 | 03-estructura-lexica/03-10-06-04-tipo-string/BloqueComoCadena.java | Verificado en CI de segunda entrega |
| 4.3.1; ejemplo 4.3.1-2 | 04-tipos-valores-variables/04-03-01-02-identidad/IdentidadValores.java | Verificado en CI de segunda entrega |
| 4.12.3; ejemplo 4.12.3-1 | 04-tipos-valores-variables/04-12-03-clases-de-variables/TiposVariables.java | Verificado en CI de segunda entrega |
| 4.12.4; ejemplo 4.12.4-1 | 04-tipos-valores-variables/04-12-04-final/ReferenciaFinal.java | Verificado en CI de segunda entrega |
| 4.12.5; ejemplo 4.12.5-1 | 04-tipos-valores-variables/04-12-05-valores-iniciales/ValoresIniciales.java | Verificado en CI de segunda entrega |
| 5.1.7 | 05-conversiones-y-contextos/05-01-07-boxing/Empaquetado.java | Verificado en CI de segunda entrega |
| 5.1.8 | 05-conversiones-y-contextos/05-01-08-unboxing-null/DesempaquetadoNulo.java | Verificado en CI de segunda entrega |
| 5.2 | 05-conversiones-y-contextos/05-02-constantes/AsignacionConstante.java | Verificado en CI de segunda entrega |
| 5.2 | 05-conversiones-y-contextos/05-02-error-reduccion/AsignacionInvalida.java | Verificado en CI de segunda entrega |
| 3.10.6; ejemplo 3.10.6-2 | 03-estructura-lexica/03-10-06-02-escapes-en-bloques/EscapeBloque.java | Implementado; confirmar CI de la rama actual |
| 4.3.1; ejemplo 4.3.1-1 | 04-tipos-valores-variables/04-03-01-01-creacion-objetos/CreacionObjetos.java | Implementado; confirmar CI de la rama actual |
| 4.4; ejemplo 4.4-1 | 04-tipos-valores-variables/04-04-01-miembros-variable-tipo/MiembrosTipo.java | Implementado; confirmar CI de la rama actual |
| 4.5.1; ejemplo 4.5.1-1 | 04-tipos-valores-variables/04-05-01-01-comodin-sin-limite/ComodinLibre.java | Implementado; confirmar CI de la rama actual |
| 4.5.1; ejemplo 4.5.1-2 | 04-tipos-valores-variables/04-05-01-02-comodin-acotado/ComodinAcotado.java | Implementado; confirmar CI de la rama actual |
| 4.8; ejemplo 4.8-1 | 04-tipos-valores-variables/04-08-01-tipo-raw/TipoRaw.java | Implementado; confirmar CI de la rama actual |
| 4.12.6; ejemplo 4.12.6-1 | 04-tipos-valores-variables/04-12-06-01-tipo-y-clase/TipoYClase.java | Implementado; confirmar CI de la rama actual |
| 5.0; ejemplo 5.0-2 | 05-conversiones-y-contextos/05-00-02-contextos-conversion/ContextosConversion.java | Implementado; confirmar CI de la rama actual |
| 5.1.3; ejemplo 5.1.3-1 | 05-conversiones-y-contextos/05-01-03-01-infinitos-a-enteros/InfinitosEnteros.java | Implementado; confirmar CI de la rama actual |
| 5.2; ejemplo 5.2-1 | 05-conversiones-y-contextos/05-02-01-asignacion-primitivos/AsignacionPrimitivos.java | Implementado; confirmar CI de la rama actual |
| 5.2; ejemplo 5.2-2 | 05-conversiones-y-contextos/05-02-02-asignacion-referencias/AsignacionReferencias.java | Implementado; confirmar CI de la rama actual |
| 5.2; ejemplo 5.2-3 | 05-conversiones-y-contextos/05-02-03-asignacion-arreglos/AsignacionArreglos.java | Implementado; confirmar CI de la rama actual |
| 5.5; ejemplo 5.5-3 | 05-conversiones-y-contextos/05-05-03-cast-incompatible/CastIncompatible.java | Implementado; confirmar CI de la rama actual |
| 5.6; ejemplo 5.6-1 | 05-conversiones-y-contextos/05-06-01-promocion-unaria/PromocionUnaria.java | Implementado; confirmar CI de la rama actual |
| 5.6; ejemplo 5.6-2 | 05-conversiones-y-contextos/05-06-02-promocion-binaria/PromocionBinaria.java | Implementado; confirmar CI de la rama actual |
| 6.3; ejemplo 6.3-1 | 06-nombres/06-03-01-alcance-clases/AlcanceClases.java | Implementado; confirmar CI de la rama actual |
| 6.3; ejemplo 6.3-2 | 06-nombres/06-03-02-variable-no-inicializada/AlcanceLocalInvalido.java | Implementado; confirmar CI de la rama actual |
| 6.4; ejemplo 6.4-1 | 06-nombres/06-04-01-sombreado-local-invalido/SombreadoInvalido.java | Implementado; confirmar CI de la rama actual |
| 6.4.1; ejemplo 6.4.1-1 | 06-nombres/06-04-01-01-sombreado-campo/SombreadoCampo.java | Implementado; confirmar CI de la rama actual |
| 7.5.1; ejemplo 7.5.1-1 | 07-paquetes-y-modulos/07-05-01-01-importacion-simple/app/ImportacionSimple.java | Implementado; confirmar CI de la rama actual |
| 8.1.1.1; ejemplo 8.1.1.1-1 | 08-clases/08-01-01-01-01-clase-abstracta/ClaseAbstracta.java | Implementado; confirmar CI de la rama actual |
| 8.3.1.1; ejemplo 8.3.1.1-1 | 08-clases/08-03-01-01-01-campos-static/CamposCompartidos.java | Implementado; confirmar CI de la rama actual |
| 8.4.9; ejemplo 8.4.9-1 | 08-clases/08-04-09-01-sobrecarga/SobrecargaMetodos.java | Implementado; confirmar CI de la rama actual |
| 8.9.3; ejemplo 8.9.3-1 | 08-clases/08-09-03-01-recorrer-enum/RecorrerEnum.java | Implementado; confirmar CI de la rama actual |
| 9.8; ejemplo 9.8-1 | 09-interfaces/09-08-01-interfaz-funcional/InterfazFuncional.java | Implementado; confirmar CI de la rama actual |
| 10.2; ejemplo 10.2-2 | 10-arreglos/10-02-02-declaradores-arreglos/DeclaradoresArreglos.java | Implementado; confirmar CI de la rama actual |
| 10.4; ejemplo 10.4-1 | 10-arreglos/10-04-01-acceso-arreglo/AccesoArreglo.java | Implementado; confirmar CI de la rama actual |
| 10.5; ejemplo 10.5-1 | 10-arreglos/10-05-01-almacenamiento-incompatible/AlmacenamientoArreglo.java | Implementado; confirmar CI de la rama actual |
| 10.6; ejemplo 10.6-1 | 10-arreglos/10-06-01-inicializador-arreglo/InicializadorArreglo.java | Implementado; confirmar CI de la rama actual |
| 10.7; ejemplo 10.7-1 | 10-arreglos/10-07-01-clonar-arreglo/ClonarArreglo.java | Implementado; confirmar CI de la rama actual |
| 10.7; ejemplo 10.7-2 | 10-arreglos/10-07-02-subarreglos-compartidos/ClonarMatriz.java | Implementado; confirmar CI de la rama actual |
| 10.8; ejemplo 10.8-2 | 10-arreglos/10-08-02-clases-arreglos/ClaseArreglos.java | Implementado; confirmar CI de la rama actual |
| 11.2.3; ejemplo 11.2.3-1 | 11-excepciones/11-02-03-01-catch-checked/CapturaCheckedInvalida.java | Implementado; confirmar CI de la rama actual |
| 11.3; ejemplo 11.3-1 | 11-excepciones/11-03-01-lanzar-capturar/LanzarCapturar.java | Implementado; confirmar CI de la rama actual |
| 12.4.1; ejemplo 12.4.1-1 | 12-ejecucion/12-04-01-01-orden-inicializacion/OrdenInicializacion.java | Implementado; confirmar CI de la rama actual |
| 14.11.3; ejemplo 14.11.3-1 | 14-bloques-sentencias-patrones/14-11-03-01-switch-caida/SwitchCaida.java | Implementado; confirmar CI de la rama actual |
| 14.14; ejemplo 14.14-1 | 14-bloques-sentencias-patrones/14-14-01-for-mejorado/ForMejorado.java | Implementado; confirmar CI de la rama actual |
| 14.21; ejemplo 14.21-1 | 14-bloques-sentencias-patrones/14-21-01-yield/YieldSwitch.java | Implementado; confirmar CI de la rama actual |
| 15.7.1; ejemplo 15.7.1-1 | 15-expresiones/15-07-01-01-orden-izquierda/OrdenIzquierda.java | Implementado; confirmar CI de la rama actual |
| 15.17.3; ejemplo 15.17.3-1 | 15-expresiones/15-17-03-01-resto-entero/RestoEntero.java | Implementado; confirmar CI de la rama actual |
| 15.18.1; ejemplo 15.18.1-1 | 15-expresiones/15-18-01-01-concatenacion/Concatenacion.java | Implementado; confirmar CI de la rama actual |
| 16; ejemplo 16-1 | 16-asignacion-definida/16-01-asignacion-definida/AsignacionDefinida.java | Implementado; confirmar CI de la rama actual |
| 16; ejemplo 16-2 | 16-asignacion-definida/16-02-asignacion-no-garantizada/AsignacionNoGarantizada.java | Implementado; confirmar CI de la rama actual |
| 16; ejemplo 16-3 | 16-asignacion-definida/16-03-final-ramas/FinalRamas.java | Implementado; confirmar CI de la rama actual |
| 8.1.1.1; ejemplo 8.1.1.1-2 | 08-clases/08-01-01-01-02-abstracta-incompatible/Actualizable.java | Implementado; confirmar CI de la rama actual |
| 8.1.2; ejemplo 8.1.2-1 | 08-clases/08-01-02-01-limites-reciprocos/Convertible.java | Implementado; confirmar CI de la rama actual |
| 8.1.3; ejemplo 8.1.3-1 | 08-clases/08-01-03-01-miembros-estaticos-internos/InternaEstatica.java | Implementado; confirmar CI de la rama actual |
| 8.1.3; ejemplo 8.1.3-2 | 08-clases/08-01-03-02-contexto-estatico-invalido/ContextoEstaticoInvalido.java | Implementado; confirmar CI de la rama actual |
| 8.1.4; ejemplo 8.1.4-1 | 08-clases/08-01-04-01-herencia-final/RaizFinal.java | Implementado; confirmar CI de la rama actual |
| 8.1.4; ejemplo 8.1.4-2 | 08-clases/08-01-04-02-jerarquia/Antepasado.java | Implementado; confirmar CI de la rama actual |
| 8.1.4; ejemplo 8.1.4-3 | 08-clases/08-01-04-03-herencia-circular/CircularA.java | Implementado; confirmar CI de la rama actual |
| 8.1.5; ejemplo 8.1.5-1 | 08-clases/08-01-05-01-interfaz-duplicada/Duplicada.java | Implementado; confirmar CI de la rama actual |
| 8.1.5; ejemplo 8.1.5-3 | 08-clases/08-01-05-03-parametros-incompatibles/Identificable.java | Implementado; confirmar CI de la rama actual |
| 8.1.5; ejemplo 8.1.5-4 | 08-clases/08-01-05-04-metodos-obligatorios/Medible.java | Implementado; confirmar CI de la rama actual |
| 8.3.1.1; ejemplo 8.3.1.1-2 | 08-clases/08-03-01-01-02-ocultar-campo-estatico/BaseEstatica.java | Implementado; confirmar CI de la rama actual |
| 8.3.1.1; ejemplo 8.3.1.1-3 | 08-clases/08-03-01-01-03-ocultar-campo-instancia/BaseInstancia.java | Implementado; confirmar CI de la rama actual |
| 8.3.2; ejemplo 8.3.2-1 | 08-clases/08-03-02-01-inicializar-campos/Coordenada.java | Implementado; confirmar CI de la rama actual |
| 8.3.2; ejemplo 8.3.2-2 | 08-clases/08-03-02-02-referencia-estatica-posterior/ReferenciaPosterior.java | Implementado; confirmar CI de la rama actual |
| 8.3.3; ejemplo 8.3.3-1 | 08-clases/08-03-03-01-referencia-adelantada-invalida/CampoAdelantado.java | Implementado; confirmar CI de la rama actual |
| 8.4.8.1; ejemplo 8.4.8.1-1 | 08-clases/08-04-08-01-01-sobrescritura/Desplazable.java | Implementado; confirmar CI de la rama actual |
| 8.4.8.2; ejemplo 8.4.8.2-1 | 08-clases/08-04-08-02-01-ocultacion-metodo-static/OrigenMetodo.java | Implementado; confirmar CI de la rama actual |
| 8.4.8.3; ejemplo 8.4.8.3-1 | 08-clases/08-04-08-03-01-retorno-covariante/Generador.java | Implementado; confirmar CI de la rama actual |
| 8.4.8.3; ejemplo 8.4.8.3-3 | 08-clases/08-04-08-03-03-throws-incompatible/Trabajo.java | Implementado; confirmar CI de la rama actual |
| 8.4.8.3; ejemplo 8.4.8.3-4 | 08-clases/08-04-08-03-04-borrado-conflicto/IdentidadGenerica.java | Implementado; confirmar CI de la rama actual |
| 8.8; ejemplo 8.8-1 | 08-clases/08-08-01-constructor/ConstructorSimple.java | Implementado; confirmar CI de la rama actual |
| 8.8.7; ejemplo 8.8.7-1 | 08-clases/08-08-07-01-this-y-super/Elemento.java | Implementado; confirmar CI de la rama actual |
| 8.8.7.1; ejemplo 8.8.7.1-1 | 08-clases/08-08-07-01-01-this-antes-super/ConstructorInvalido.java | Implementado; confirmar CI de la rama actual |
| 8.8.9; ejemplo 8.8.9-1 | 08-clases/08-08-09-01-constructor-implicito/RectanguloImplicit.java | Implementado; confirmar CI de la rama actual |
| 8.8.10; ejemplo 8.8.10-1 | 08-clases/08-08-10-01-constructor-privado/SoloClase.java | Implementado; confirmar CI de la rama actual |
| 8.9.2; ejemplo 8.9.2-1 | 08-clases/08-09-02-01-enum-con-campos/Nivel.java | Implementado; confirmar CI de la rama actual |
| 8.9.2; ejemplo 8.9.2-2 | 08-clases/08-09-02-02-enum-referencia-invalida/EstadoInvalido.java | Implementado; confirmar CI de la rama actual |
| 8.9.3; ejemplo 8.9.3-2 | 08-clases/08-09-03-02-switch-enum/Semaforo.java | Implementado; confirmar CI de la rama actual |
| 8.9.3; ejemplo 8.9.3-3 | 08-clases/08-09-03-03-enum-comportamiento/CalculoEnum.java | Implementado; confirmar CI de la rama actual |
| 9.3; ejemplo 9.3-1 | 09-interfaces/09-03-01-campos-ambiguos/TarifaA.java | Implementado; confirmar CI de la rama actual |
| 9.3; ejemplo 9.3-2 | 09-interfaces/09-03-02-campo-heredado-dos-veces/Comun.java | Implementado; confirmar CI de la rama actual |
| 9.3.1; ejemplo 9.3.1-1 | 09-interfaces/09-03-01-01-campo-adelantado-interfaz/CampoInterfazInvalido.java | Implementado; confirmar CI de la rama actual |
| 9.4.2; ejemplo 9.4.2-1 | 09-interfaces/09-04-02-01-sobrecarga-interfaz/Movil.java | Implementado; confirmar CI de la rama actual |
| 9.6.1; ejemplo 9.6.1-1 | 09-interfaces/09-06-01-01-declarar-anotacion/Ficha.java | Implementado; confirmar CI de la rama actual |
| 9.6.1; ejemplo 9.6.1-2 | 09-interfaces/09-06-01-02-anotacion-marcadora/Experimental.java | Implementado; confirmar CI de la rama actual |
| 9.6.2; ejemplo 9.6.2-1 | 09-interfaces/09-06-02-01-anotacion-default/Tarea.java | Implementado; confirmar CI de la rama actual |
| 9.7.3; ejemplo 9.7.3-1 | 09-interfaces/09-07-03-01-anotacion-value/Etiqueta.java | Implementado; confirmar CI de la rama actual |
| 10.2; ejemplo 10.2-1 | 10-arreglos/10-02-01-declarar-arreglos/DeclararArreglos.java | Implementado; confirmar CI de la rama actual |
| 10.8; ejemplo 10.8-1 | 10-arreglos/10-08-01-objeto-class-arreglo/ObjetoClassArreglo.java | Implementado; confirmar CI de la rama actual |
| 12.4.1; ejemplo 12.4.1-2 | 12-ejecucion/12-04-01-02-inicializar-clase-declarante/Declarante.java | Implementado; confirmar CI de la rama actual |
| 12.4.1; ejemplo 12.4.1-3 | 12-ejecucion/12-04-01-03-inicializacion-interfaces/AbuelaI.java | Implementado; confirmar CI de la rama actual |
| 12.5; ejemplo 12.5-2 | 12-ejecucion/12-05-02-despacho-en-constructor/ConstructorBase.java | Implementado; confirmar CI de la rama actual |
| 13.4.23; ejemplo 13.4.23-1 | 13-compatibilidad-binaria/13-04-23-01-agregar-sobrecarga/v1/SalidaBinaria.java | Implementado; confirmar CI de la rama actual |
| 18.5.5; ejemplo 18.5.5-1 | 18-inferencia-de-tipos/18-05-05-01-inferencia-patron-record/Transformador.java | Implementado; confirmar CI de la rama actual |

Pendientes: revisión de fidelidad y casos secundarios, completar ejemplos numerados y fragmentos, migrar carpeta anterior, publicar. El caso de compatibilidad binaria compila y ejecuta versiones sucesivas separadamente. No se incluye el PDF.
