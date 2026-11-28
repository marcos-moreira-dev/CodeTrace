# CodeTrace — Java integral: plan curricular consolidado

**Estado:** implementación de 06–20 completa (523 fuentes Java, 2 documentos, 320 clientes), validada en Linux v37; pendiente de matriz reforzada v38 e integración final. Consultar `JAVA-INTEGRAL-ESTADO.md` y el inventario para el estado vivo. Fundamentos 00–05 congelados; PR #7 sigue siendo borrador.

15 áreas nuevas (06–20), 214 unidades de planificación (2 documentales), 523 rutas Java y 2 rutas documentales previstas. Son un inventario de alcance, no una cuota de código. Unidades con varios clientes independientes se prueban por separado; no compilar todo Java integral en un único comando. Se conservan los números del borrador donde es posible; los saltos reflejan descartes deliberados.

## Criterio pedagógico y fuentes

Java 21 clásico: bucles, arreglos, matrices y métodos explícitos; clases pequeñas, un tipo superior por archivo. Usar APIs estándar cuando sean el mecanismo estudiado (NIO, sockets, locks, criptografía); no ocultar algoritmos con utilidades. No Spring, Maven/Gradle obligatorio, JavaFX, frameworks, bases de datos externas, dispositivos, circuitos ni simulaciones de CPU. Inyección manual solo donde enseña arquitectura. Comentarios españoles del concepto, comandos locales y resultado esperado; bibliografía fuera del código. R = ejecución real/algoritmo, T = modelo con supuestos, P = dependiente de plataforma, M = mezcla explícita, D = documentación.

Base docente: Programación de Sistemas CCPG1051, unidades 1–6 de ESPOL; la malla original y sus objetivos se han revisado para no traducir C literalmente. U1 shell/archivos, U2 compilación y herramientas, U3 datos/memoria, U4 bibliotecas, U5 TCP/IP y E/S, U6 procesos y concurrencia (productor-consumidor, lectores-escritores, prethreading, grafos de progreso). Complementos: CS2023, OSTEP, Java SE 21 API y JLS 21. Este plan adapta, no reproduce, esas fuentes. No se han revalidado todas las páginas ni cada referencia externa en esta intervención.

## Inventario por área

| Área | Unidades | Java previstos |
|---|---:|---:|
| 06-matematicas-computacionales | 9 | 20 |
| 07-representacion-de-datos-y-memoria | 18 | 44 |
| 08-sistemas-de-archivos-y-entrada-salida | 20 | 40 |
| 09-sistemas-operativos-y-procesos | 20 | 47 |
| 10-concurrencia-y-paralelismo | 31 | 80 |
| 11-redes-y-protocolos-de-comunicacion | 24 | 64 |
| 12-persistencia-y-gestion-de-datos | 9 | 25 |
| 13-criptografia-y-seguridad | 11 | 17 |
| 14-comunicacion-con-dispositivos | 7 | 16 |
| 15-graficos-y-multimedia | 7 | 16 |
| 16-interpretes-y-procesamiento-de-lenguajes | 9 | 21 |
| 17-sistemas-distribuidos | 10 | 24 |
| 18-pruebas-depuracion-y-rendimiento | 14 | 23 |
| 19-inteligencia-artificial-fundamental | 12 | 13 |
| 20-arquitectura-de-software | 13 | 73 |

## Árbol de rutas

```text
java21/
├── 00-fundamentos-especificacion-oficial/
├── 01-fundamentos-en-accion/
├── 02-estructuras-datos/
├── 03-algoritmos/
├── 04-patrones-diseno/
├── 05-patrones-diseno-counter-strike/
├── 06-matematicas-computacionales/
│   ├── 01-logica-proposicional/
│   │   ├── TablaVerdad.java
│   │   └── Implicacion.java
│   ├── 02-conjuntos/
│   │   └── OperacionesConjuntos.java
│   ├── 03-combinatoria/
│   │   ├── Permutaciones.java
│   │   └── Combinaciones.java
│   ├── 04-matrices/
│   │   ├── SumaMatrices.java
│   │   ├── ProductoMatrices.java
│   │   └── Transpuesta.java
│   ├── 05-vectores/
│   │   ├── ProductoEscalar.java
│   │   ├── NormaVector.java
│   │   └── DistanciaEuclidiana.java
│   ├── 06-probabilidad/
│   │   ├── ProbabilidadFrecuencia.java
│   │   └── MonteCarlo.java
│   ├── 07-estadistica/
│   │   ├── MediaVarianza.java
│   │   └── Mediana.java
│   ├── 08-precision-numerica/
│   │   ├── ErrorRedondeo.java
│   │   └── BigDecimalExacto.java
│   └── 09-metodos-numericos/
│       ├── Biseccion.java
│       ├── NewtonRaphson.java
│       └── IntegracionTrapecios.java
├── 07-representacion-de-datos-y-memoria/
│   ├── 01-bases-numericas/
│   │   ├── ConversionBases.java
│   │   └── ConversionManual.java
│   ├── 02-enteros-sin-signo/
│   │   └── EnteroSinSigno.java
│   ├── 03-complemento-a-dos/
│   │   ├── ComplementoDos.java
│   │   └── DesbordamientoEntero.java
│   ├── 04-punto-flotante/
│   │   ├── BitsFloat.java
│   │   └── BitsDouble.java
│   ├── 05-orden-bytes/
│   │   ├── LittleBigEndian.java
│   │   └── OrdenNativo.java
│   ├── 06-operaciones-bits/
│   │   ├── OperacionesBits.java
│   │   └── Desplazamientos.java
│   ├── 07-mascaras/
│   │   ├── ActivarDesactivarBits.java
│   │   ├── ExtraerCampoBits.java
│   │   └── EmpaquetarCampos.java
│   ├── 08-codificacion-texto/
│   │   ├── AsciiUnicode.java
│   │   ├── Utf8Bytes.java
│   │   └── PuntosCodigo.java
│   ├── 15-referencias-java/
│   │   ├── PasoPorValor.java
│   │   ├── AliasReferencias.java
│   │   └── IntercambioValores.java
│   ├── 18-datos-compuestos/
│   │   ├── RegistroBinario.java
│   │   └── MainRegistroBinario.java
│   ├── 19-stack-heap/
│   │   ├── PilaLlamadas.java
│   │   ├── ObjetosHeap.java
│   │   └── VidaReferencias.java
│   ├── 20-memoria-dinamica/
│   │   └── ReservaLiberacionSimulada.java
│   ├── 21-asignacion-bloques/
│   │   ├── Bloque.java
│   │   ├── FirstFit.java
│   │   ├── BestFit.java
│   │   ├── WorstFit.java
│   │   └── MainAsignacion.java
│   ├── 22-fragmentacion/
│   │   ├── FragmentacionInterna.java
│   │   ├── FragmentacionExterna.java
│   │   └── Coalescencia.java
│   ├── 23-memoria-virtual/
│   │   ├── DireccionVirtual.java
│   │   ├── TablaPaginas.java
│   │   └── MainTraduccion.java
│   ├── 24-reemplazo-paginas/
│   │   ├── FIFO.java
│   │   ├── LRU.java
│   │   ├── OPT.java
│   │   └── MainReemplazo.java
│   ├── 25-errores-memoria/
│   │   └── FugaPorRetencion.java
│   └── 26-recoleccion-basura/
│       ├── Alcanzabilidad.java
│       └── MainMarcarBarrer.java
├── 08-sistemas-de-archivos-y-entrada-salida/
│   ├── 01-rutas/
│   │   ├── RutasAbsolutasRelativas.java
│   │   ├── NormalizarRuta.java
│   │   └── ResolverRuta.java
│   ├── 02-directorios/
│   │   ├── CrearListarDirectorio.java
│   │   └── RecorrerArbol.java
│   ├── 03-comodines/
│   │   └── BuscarConGlob.java
│   ├── 04-operaciones-archivos/
│   │   ├── CopiarArchivo.java
│   │   ├── MoverArchivo.java
│   │   └── EliminarArchivo.java
│   ├── 05-metadatos/
│   │   ├── AtributosArchivo.java
│   │   └── TiemposArchivo.java
│   ├── 06-enlaces/
│   │   ├── EnlaceDuro.java
│   │   └── EnlaceSimbolico.java
│   ├── 07-permisos/
│   │   ├── MascarasOctales.java
│   │   ├── PermisosPosix.java
│   │   └── PropietarioArchivo.java
│   ├── 08-archivos-temporales/
│   │   └── DirectorioTemporal.java
│   ├── 09-flujos-estandar/
│   │   └── EntradaSalidaError.java
│   ├── 10-flujos-bytes/
│   │   ├── LecturaBytes.java
│   │   ├── EscrituraBytes.java
│   │   └── LecturaParcial.java
│   ├── 11-flujos-texto/
│   │   ├── LecturaTexto.java
│   │   ├── EscrituraTexto.java
│   │   └── CharsetExplicito.java
│   ├── 12-buffering/
│   │   ├── FlujoConBuffer.java
│   │   └── VaciadoBuffer.java
│   ├── 13-modos-apertura/
│   │   ├── ModosApertura.java
│   │   └── AnexarArchivo.java
│   ├── 14-acceso-aleatorio/
│   │   ├── AccesoAleatorio.java
│   │   └── PosicionCanal.java
│   ├── 15-buffers-nio/
│   │   ├── EstadoByteBuffer.java
│   │   └── FlipClearCompact.java
│   ├── 16-lectura-binaria/
│   │   ├── RegistroBinario.java
│   │   └── MainLecturaRegistro.java
│   ├── 17-mapear-archivo/
│   │   └── ArchivoMapeado.java
│   ├── 18-bloqueos-archivo/
│   │   ├── BloqueoArchivo.java
│   │   └── ClienteBloqueo.java
│   ├── 19-monitoreo-directorios/
│   │   └── VigilarDirectorio.java
│   └── 20-compresion/
│       ├── GzipTexto.java
│       └── ZipArchivos.java
├── 09-sistemas-operativos-y-procesos/
│   ├── 01-capas-sistema/
│   │   └── EntornoSistema.java
│   ├── 02-argumentos-consola/
│   │   ├── OpcionesCortas.java
│   │   └── OpcionesLargas.java
│   ├── 03-entorno/
│   │   ├── VariablesEntorno.java
│   │   └── DirectorioTrabajo.java
│   ├── 04-identidad-proceso/
│   │   ├── ProcesoActual.java
│   │   └── ProcesoPadre.java
│   ├── 05-crear-proceso/
│   │   ├── Padre.java
│   │   └── Hijo.java
│   ├── 06-aislamiento-procesos/
│   │   ├── Padre.java
│   │   └── Hijo.java
│   ├── 07-esperar-proceso/
│   │   ├── Padre.java
│   │   └── Hijo.java
│   ├── 08-varios-hijos/
│   │   ├── Padre.java
│   │   └── Hijo.java
│   ├── 11-estados-procesos/
│   │   ├── EstadosProceso.java
│   │   └── CambioContexto.java
│   ├── 13-redireccion/
│   │   ├── Padre.java
│   │   └── Hijo.java
│   ├── 14-pipeline/
│   │   ├── Generador.java
│   │   ├── Filtro.java
│   │   ├── Consumidor.java
│   │   └── MainPipeline.java
│   ├── 15-comunicacion-pipes/
│   │   ├── Padre.java
│   │   └── Hijo.java
│   ├── 16-timeout-proceso/
│   │   ├── Padre.java
│   │   └── Hijo.java
│   ├── 17-notificacion-eventos/
│   │   ├── Temporizador.java
│   │   └── CancelacionCooperativa.java
│   ├── 19-terminacion-jvm/
│   │   ├── Padre.java
│   │   └── HijoConShutdownHook.java
│   ├── 20-servicio-segundo-plano/
│   │   ├── ServicioTemporal.java
│   │   └── MainServicio.java
│   ├── 21-planificacion-fcfs/
│   │   ├── ProcesoSimulado.java
│   │   ├── FCFS.java
│   │   └── MainFCFS.java
│   ├── 22-planificacion-sjf-srtf/
│   │   ├── ProcesoSimulado.java
│   │   ├── SJF.java
│   │   ├── SRTF.java
│   │   └── MainPlanificacion.java
│   ├── 23-planificacion-round-robin/
│   │   ├── ProcesoSimulado.java
│   │   ├── RoundRobin.java
│   │   └── MainRoundRobin.java
│   └── 24-planificacion-prioridades/
│       ├── ProcesoSimulado.java
│       ├── Prioridades.java
│       ├── Aging.java
│       └── MainPrioridades.java
├── 10-concurrencia-y-paralelismo/
│   ├── 01-hilos/
│   │   ├── CrearHilo.java
│   │   ├── RunnableBasico.java
│   │   └── JoinHilos.java
│   ├── 02-estados-hilo/
│   │   └── EstadosHilo.java
│   ├── 03-estado-compartido/
│   │   ├── VariablesCompartidas.java
│   │   ├── EstadoLocal.java
│   │   └── ThreadLocalDemo.java
│   ├── 04-intercalaciones/
│   │   ├── IntercalacionIncremento.java
│   │   └── CarreraControlada.java
│   ├── 05-seccion-critica/
│   │   ├── ContadorInseguro.java
│   │   └── ContadorSincronizado.java
│   ├── 06-visibilidad/
│   │   └── PublicacionSegura.java
│   ├── 07-atomicos/
│   │   ├── AtomicIntegerDemo.java
│   │   └── CompareAndSet.java
│   ├── 09-locks/
│   │   ├── ReentrantLockDemo.java
│   │   ├── TryLockTimeout.java
│   │   └── ConditionDemo.java
│   ├── 10-semaforos/
│   │   ├── SemaforoBinario.java
│   │   ├── SemaforoContador.java
│   │   └── OperacionesPV.java
│   ├── 11-funciones-seguras/
│   │   ├── FuncionNoSegura.java
│   │   ├── FuncionThreadSafe.java
│   │   └── FuncionReentrante.java
│   ├── 12-productor-consumidor-01/
│   │   ├── BufferCircular.java
│   │   ├── Productor.java
│   │   ├── Consumidor.java
│   │   └── MainProductorConsumidor.java
│   ├── 13-productor-consumidor-02/
│   │   ├── Productor.java
│   │   ├── Consumidor.java
│   │   └── MainBlockingQueue.java
│   ├── 14-lectores-escritores-01/
│   │   ├── RecursoCompartido.java
│   │   ├── Lector.java
│   │   ├── Escritor.java
│   │   └── MainPrioridadLectores.java
│   ├── 15-lectores-escritores-02/
│   │   ├── RecursoCompartido.java
│   │   ├── Lector.java
│   │   ├── Escritor.java
│   │   └── MainPrioridadEscritores.java
│   ├── 16-lectores-escritores-03/
│   │   ├── RecursoCompartido.java
│   │   └── MainLectoresEscritoresEquitativo.java
│   ├── 17-filosofos-comensales/
│   │   ├── Mesa.java
│   │   ├── Filosofo.java
│   │   └── MainFilosofos.java
│   ├── 18-barbero-dormilon/
│   │   ├── Barberia.java
│   │   ├── Barbero.java
│   │   ├── Cliente.java
│   │   └── MainBarberia.java
│   ├── 19-fumadores-cigarrillos/
│   │   ├── MesaIngredientes.java
│   │   ├── Agente.java
│   │   ├── Fumador.java
│   │   └── MainFumadores.java
│   ├── 20-peterson/
│   │   ├── PetersonSimulado.java
│   │   └── MainPeterson.java
│   ├── 21-panaderia-lamport/
│   │   ├── PanaderiaSimulada.java
│   │   └── MainPanaderia.java
│   ├── 22-progress-graphs/
│   │   ├── Intercalacion.java
│   │   ├── GrafoProgreso.java
│   │   └── MainGrafoProgreso.java
│   ├── 23-deadlock-modelo/
│   │   ├── EstadoBloqueos.java
│   │   └── MainDeadlock.java
│   ├── 24-deadlock-prevencion/
│   │   ├── OrdenGlobalLocks.java
│   │   └── RecuperacionTryLock.java
│   ├── 25-banquero/
│   │   ├── Banquero.java
│   │   └── MainBanquero.java
│   ├── 26-inanicion-equidad/
│   │   ├── ModeloInanicion.java
│   │   └── SemaforoEquitativo.java
│   ├── 27-coordinacion/
│   │   ├── CountDownLatchDemo.java
│   │   ├── CyclicBarrierDemo.java
│   │   └── PhaserDemo.java
│   ├── 28-pools/
│   │   ├── PoolFijo.java
│   │   ├── PoolColaAcotada.java
│   │   └── MainPrethreading.java
│   ├── 29-tareas-asincronas/
│   │   ├── CallableFuture.java
│   │   ├── CompletableFutureDemo.java
│   │   └── CancelarTarea.java
│   ├── 30-paralelismo/
│   │   ├── SumaSecuencialParalela.java
│   │   └── ForkJoinSuma.java
│   ├── 31-hilos-virtuales/
│   │   └── CompararHilosBloqueantes.java
│   └── 32-colecciones-concurrentes/
│       ├── ConcurrentHashMapDemo.java
│       └── ConcurrentLinkedQueueDemo.java
├── 11-redes-y-protocolos-de-comunicacion/
│   ├── 01-modelo-cliente-servidor/
│   │   ├── Cliente.java
│   │   ├── Servidor.java
│   │   └── MainClienteServidor.java
│   ├── 02-interfaces-red/
│   │   ├── InterfacesLocales.java
│   │   └── Loopback.java
│   ├── 03-direccionamiento-ip/
│   │   ├── IPv4Bytes.java
│   │   └── IPv6Bytes.java
│   ├── 04-subredes/
│   │   ├── MascaraCIDR.java
│   │   ├── PerteneceSubred.java
│   │   └── RedYBroadcast.java
│   ├── 05-mac-y-tramas/
│   │   ├── DireccionMAC.java
│   │   └── TramaEthernet.java
│   ├── 06-conmutacion/
│   │   ├── SwitchSimulado.java
│   │   └── MainSwitch.java
│   ├── 07-enrutamiento/
│   │   ├── Ruta.java
│   │   ├── TablaRutas.java
│   │   └── MainPrefijoMasLargo.java
│   ├── 08-orden-red/
│   │   └── NetworkByteOrder.java
│   ├── 09-puertos/
│   │   ├── PuertoLocal.java
│   │   └── DireccionSocket.java
│   ├── 10-dns/
│   │   ├── ResolverLocalhost.java
│   │   └── CacheDNSSimulada.java
│   ├── 11-tcp-echo/
│   │   ├── Servidor.java
│   │   ├── Cliente.java
│   │   └── MainEcho.java
│   ├── 12-tcp-framing/
│   │   ├── CodificadorTrama.java
│   │   ├── Servidor.java
│   │   ├── Cliente.java
│   │   └── MainFraming.java
│   ├── 13-tcp-lectura-parcial/
│   │   ├── Servidor.java
│   │   ├── Cliente.java
│   │   └── MainLecturaParcial.java
│   ├── 14-udp/
│   │   ├── Receptor.java
│   │   ├── Emisor.java
│   │   └── MainDatagrama.java
│   ├── 15-udp-fiabilidad-modelo/
│   │   ├── CanalSimulado.java
│   │   ├── StopAndWait.java
│   │   └── MainStopAndWait.java
│   ├── 16-ventana-deslizante/
│   │   ├── VentanaDeslizante.java
│   │   └── MainVentana.java
│   ├── 17-congestion-y-tasa/
│   │   ├── TokenBucket.java
│   │   └── MainTokenBucket.java
│   ├── 18-servidor-multicliente/
│   │   ├── Servidor.java
│   │   ├── Cliente.java
│   │   └── MainMultiCliente.java
│   ├── 19-servidor-pool/
│   │   ├── Servidor.java
│   │   ├── Cliente.java
│   │   └── MainServidorPool.java
│   ├── 20-servidor-virtual/
│   │   ├── Servidor.java
│   │   ├── Cliente.java
│   │   └── MainServidorVirtual.java
│   ├── 21-sockets-nio/
│   │   ├── ServidorSelector.java
│   │   ├── Cliente.java
│   │   └── MainSelector.java
│   ├── 22-http/
│   │   ├── ServidorHttp.java
│   │   ├── ClienteHttp.java
│   │   └── MainHttp.java
│   ├── 23-protocolos-aplicacion/
│   │   ├── Solicitud.java
│   │   ├── Respuesta.java
│   │   ├── ProtocoloTexto.java
│   │   └── MainProtocolo.java
│   └── 24-timeouts/
│       ├── ServidorLento.java
│       ├── ClienteTimeout.java
│       └── MainTimeout.java
├── 12-persistencia-y-gestion-de-datos/
│   ├── 01-registros/
│   │   ├── Registro.java
│   │   ├── ArchivoRegistros.java
│   │   └── MainRegistros.java
│   ├── 02-formatos-texto/
│   │   ├── CsvSimple.java
│   │   ├── MainCsv.java
│   │   └── PropertiesDemo.java
│   ├── 03-indexacion/
│   │   ├── IndiceArchivo.java
│   │   └── MainIndice.java
│   ├── 04-consultas/
│   │   ├── FiltrarRegistros.java
│   │   ├── ProyectarCampos.java
│   │   └── AgruparRegistros.java
│   ├── 05-join/
│   │   ├── NestedLoopJoin.java
│   │   ├── HashJoin.java
│   │   └── MainJoin.java
│   ├── 06-transacciones/
│   │   ├── Cuenta.java
│   │   ├── TransaccionSimulada.java
│   │   └── MainTransaccion.java
│   ├── 07-aislamiento/
│   │   ├── LecturaSuciaSimulada.java
│   │   ├── LecturaNoRepetible.java
│   │   └── PerdidaActualizacion.java
│   ├── 08-write-ahead-log/
│   │   ├── EntradaLog.java
│   │   ├── AlmacenSimulado.java
│   │   └── MainRecuperacion.java
│   └── 09-jdbc-api/
│       ├── ContratoJdbc.java
│       └── MainConsultaSimulada.java
├── 13-criptografia-y-seguridad/
│   ├── 01-integridad/
│   │   ├── HashSHA256.java
│   │   └── VerificarArchivo.java
│   ├── 02-checksum/
│   │   └── ChecksumSuma.java
│   ├── 03-autenticacion-mensajes/
│   │   ├── HmacSHA256.java
│   │   └── VerificarHmac.java
│   ├── 04-aleatoriedad/
│   │   └── SecureRandomDemo.java
│   ├── 05-contrasenas/
│   │   └── PBKDF2Demo.java
│   ├── 06-cifrado-simetrico/
│   │   └── AesGcmDemo.java
│   ├── 07-claves-publicas/
│   │   ├── ParClaves.java
│   │   └── FirmaDigital.java
│   ├── 08-intercambio-claves/
│   │   └── AcuerdoX25519.java
│   ├── 09-certificados/
│   │   ├── LeerCertificado.java
│   │   └── MainCertificado.java
│   ├── 10-validacion-entradas/
│   │   ├── ValidarLongitud.java
│   │   └── ValidarRuta.java
│   └── 11-control-acceso/
│       ├── PermisosRoles.java
│       └── MainControlAcceso.java
├── 14-comunicacion-con-dispositivos/
│   ├── 03-transmision-serial/
│   │   └── TramaSerial.java
│   ├── 04-paridad/
│   │   ├── ParidadPar.java
│   │   └── ParidadImpar.java
│   ├── 05-crc-y-hamming/
│   │   ├── CRCModelo.java
│   │   ├── Hamming74.java
│   │   └── MainHamming.java
│   ├── 06-canal-serial/
│   │   ├── CanalBytesSimulado.java
│   │   ├── Emisor.java
│   │   ├── Receptor.java
│   │   └── MainSerial.java
│   ├── 08-mensajes-medicion-y-control/
│   │   ├── MedicionSimulada.java
│   │   └── ControlPorUmbral.java
│   ├── 11-telemetria/
│   │   ├── TramaMedicion.java
│   │   ├── Codificador.java
│   │   ├── Decodificador.java
│   │   └── MainTelemetria.java
│   └── 12-interoperabilidad-nativa/
│       └── README.md
├── 15-graficos-y-multimedia/
│   ├── 01-pixeles/
│   │   ├── ColorRGB.java
│   │   └── ImagenPixeles.java
│   ├── 02-archivos-imagen/
│   │   └── LeerEscribirPNG.java
│   ├── 03-transformaciones/
│   │   ├── Traslacion.java
│   │   ├── Rotacion2D.java
│   │   └── Escalado.java
│   ├── 04-procesamiento-imagen/
│   │   ├── EscalaGrises.java
│   │   ├── Umbral.java
│   │   └── Convolucion.java
│   ├── 05-rasterizacion/
│   │   ├── LineaBresenham.java
│   │   └── MainRaster.java
│   ├── 06-graficos-2d/
│   │   ├── DibujarFormas.java
│   │   └── MainGrafico.java
│   └── 07-audio-digital/
│       ├── MuestreoSeno.java
│       ├── CuantizacionPCM.java
│       └── EscribirWav.java
├── 16-interpretes-y-procesamiento-de-lenguajes/
│   ├── 01-cadena-compilacion/
│   │   └── FuenteEjemplo.java
│   ├── 02-bytecode/
│   │   └── Operacion.java
│   ├── 05-biblioteca-jar/
│   │   ├── lib/
│   │   │   └── Operaciones.java
│   │   └── app/
│   │       └── Cliente.java
│   ├── 07-carga-clases/
│   │   ├── ClaseEjemplo.java
│   │   └── MainCargaClase.java
│   ├── 10-lexer/
│   │   ├── Token.java
│   │   ├── Lexer.java
│   │   └── MainLexer.java
│   ├── 11-parser-descenso-recursivo/
│   │   ├── Token.java
│   │   ├── Lexer.java
│   │   ├── Nodo.java
│   │   ├── Parser.java
│   │   └── MainParser.java
│   ├── 12-shunting-yard/
│   │   ├── ShuntingYard.java
│   │   └── MainPostfija.java
│   ├── 13-evaluacion-ast/
│   │   ├── Expresion.java
│   │   ├── Evaluador.java
│   │   └── MainEvaluador.java
│   └── 14-maquina-estados-comandos/
│       ├── ReconocerComando.java
│       └── MaquinaEstados.java
├── 17-sistemas-distribuidos/
│   ├── 01-mensajeria/
│   │   ├── Mensaje.java
│   │   ├── Buzon.java
│   │   └── MainMensajeria.java
│   ├── 02-relojes-logicos/
│   │   ├── RelojLamport.java
│   │   └── MainLamport.java
│   ├── 03-relojes-vectoriales/
│   │   ├── RelojVectorial.java
│   │   └── MainCausalidad.java
│   ├── 04-reintentos/
│   │   ├── CanalSimulado.java
│   │   ├── ReintentoAcotado.java
│   │   └── MainReintento.java
│   ├── 05-idempotencia/
│   │   ├── Peticion.java
│   │   ├── ServidorIdempotente.java
│   │   └── MainDuplicados.java
│   ├── 06-heartbeat/
│   │   ├── Nodo.java
│   │   ├── DetectorFallos.java
│   │   └── MainHeartbeat.java
│   ├── 07-eleccion-lider/
│   │   ├── EleccionAnillo.java
│   │   └── MainEleccion.java
│   ├── 08-replicacion/
│   │   ├── Replica.java
│   │   └── MainReplicacion.java
│   ├── 09-consistencia/
│   │   ├── LecturaEventual.java
│   │   └── QuorumSimulado.java
│   └── 10-exclusion-distribuida/
│       ├── TokenAnillo.java
│       └── MainTokenAnillo.java
├── 18-pruebas-depuracion-y-rendimiento/
│   ├── 01-contratos/
│   │   ├── PrePostcondiciones.java
│   │   └── Invariantes.java
│   ├── 02-pruebas-unitarias/
│   │   ├── Calculadora.java
│   │   └── PruebaCalculadora.java
│   ├── 03-pruebas-limites/
│   │   └── PruebaCasosLimite.java
│   ├── 04-pruebas-propiedades/
│   │   └── PruebaPropiedades.java
│   ├── 05-pruebas-concurrencia/
│   │   ├── PruebaContador.java
│   │   └── PruebaTimeout.java
│   ├── 06-trazas/
│   │   ├── TrazaExcepcion.java
│   │   └── CausasExcepcion.java
│   ├── 07-depuracion-jdb/
│   │   └── ProgramaDepurable.java
│   ├── 08-registro-eventos/
│   │   └── RegistroJul.java
│   ├── 09-medicion-tiempo/
│   │   ├── MedirNanoTime.java
│   │   └── CompararComplejidad.java
│   ├── 10-memoria-jvm/
│   │   ├── ConsultarHeap.java
│   │   └── MainRetencion.java
│   ├── 11-diagnostico-hilos/
│   │   ├── ConsultarHilos.java
│   │   └── DetectarDeadlockSimulado.java
│   ├── 12-flight-recorder/
│   │   ├── GrabarJfr.java
│   │   └── LeerEventosJfr.java
│   ├── 13-herramientas-jdk/
│   │   ├── ProcesoInspeccionable.java
│   │   └── MainDiagnostico.java
│   └── 14-empaquetado/
│       └── Aplicacion.java
├── 19-inteligencia-artificial-fundamental/
│   ├── 01-comparacion-de-patrones/
│   │   └── CompararMatrices.java
│   ├── 02-reconocimiento-de-digitos/
│   │   └── ReconocerDigito.java
│   ├── 03-distancias/
│   │   └── DistanciaManhattan.java
│   ├── 04-clasificacion-knn/
│   │   └── ClasificadorKNN.java
│   ├── 05-perceptron/
│   │   └── Perceptron.java
│   ├── 06-red-neuronal-elemental/
│   │   └── RedNeuronalElemental.java
│   ├── 07-agrupamiento-kmeans/
│   │   └── KMeans.java
│   ├── 08-busqueda-heuristica/
│   │   └── BusquedaAEstrella.java
│   ├── 09-algoritmos-geneticos/
│   │   └── SeleccionMutacion.java
│   ├── 10-regresion-lineal/
│   │   └── RegresionLineal.java
│   ├── 11-evaluacion-modelos/
│   │   ├── MatrizConfusion.java
│   │   └── MetricasClasificacion.java
│   └── 12-minimax/
│       └── Minimax.java
└── 20-arquitectura-de-software/
    ├── 01-en-capas-verduleria/
    │   ├── dominio/
    │   │   └── Verdura.java
    │   ├── persistencia/
    │   │   └── InventarioEnMemoria.java
    │   ├── servicio/
    │   │   └── ServicioVentas.java
    │   ├── presentacion/
    │   │   └── ConsolaVentas.java
    │   └── Main.java
    ├── 02-mvc-biblioteca/
    │   ├── modelo/
    │   │   ├── Libro.java
    │   │   └── Biblioteca.java
    │   ├── vista/
    │   │   └── VistaBiblioteca.java
    │   ├── controlador/
    │   │   └── ControladorPrestamos.java
    │   └── Main.java
    ├── 03-mvp-panaderia/
    │   ├── modelo/
    │   │   └── PedidoPan.java
    │   ├── vista/
    │   │   ├── VistaPedidos.java
    │   │   └── ConsolaPedidos.java
    │   ├── presentador/
    │   │   └── PresentadorPedidos.java
    │   └── Main.java
    ├── 04-mvvm-heladeria/
    │   ├── modelo/
    │   │   └── Helado.java
    │   ├── viewmodel/
    │   │   └── HeladoViewModel.java
    │   ├── vista/
    │   │   └── VistaHeladeria.java
    │   └── Main.java
    ├── 05-hexagonal-centro-ecuestre/
    │   ├── dominio/
    │   │   ├── Caballo.java
    │   │   └── Reserva.java
    │   ├── aplicacion/
    │   │   ├── puertos/
    │   │   │   ├── ServicioReservas.java
    │   │   │   └── RepositorioCaballos.java
    │   │   └── ReservarCaballo.java
    │   ├── infraestructura/
    │   │   ├── entrada/
    │   │   │   └── ConsolaReservas.java
    │   │   └── salida/
    │   │       └── RepositorioEnMemoria.java
    │   └── Main.java
    ├── 06-clean-peluqueria-canina/
    │   ├── entidades/
    │   │   └── Cita.java
    │   ├── casosdeuso/
    │   │   ├── AgendarCita.java
    │   │   ├── EntradaCita.java
    │   │   ├── SalidaCita.java
    │   │   └── RepositorioCitas.java
    │   ├── adaptadores/
    │   │   ├── ControladorCitas.java
    │   │   └── PresentadorCitas.java
    │   ├── infraestructura/
    │   │   └── CitasEnMemoria.java
    │   └── Main.java
    ├── 07-onion-cabanas/
    │   ├── dominio/
    │   │   ├── Cabana.java
    │   │   └── RepositorioCabanas.java
    │   ├── aplicacion/
    │   │   └── ReservarCabana.java
    │   ├── infraestructura/
    │   │   └── CabanasEnMemoria.java
    │   ├── presentacion/
    │   │   └── ConsolaCabanas.java
    │   └── Main.java
    ├── 08-eventos-restaurante/
    │   ├── dominio/
    │   │   └── Pedido.java
    │   ├── eventos/
    │   │   ├── PedidoListo.java
    │   │   └── BusEventos.java
    │   ├── cocina/
    │   │   └── Cocina.java
    │   ├── mostrador/
    │   │   └── AvisadorPedidos.java
    │   └── Main.java
    ├── 09-monolito-modular-supermercado/
    │   ├── inventario/
    │   │   ├── Inventario.java
    │   │   └── Existencias.java
    │   ├── ventas/
    │   │   └── Ventas.java
    │   ├── compras/
    │   │   └── Compras.java
    │   └── Main.java
    ├── 10-microservicios-bicicletas/
    │   ├── disponibilidad/
    │   │   └── ServicioDisponibilidad.java
    │   ├── reservas/
    │   │   └── ServicioReservas.java
    │   ├── cliente/
    │   │   └── ClienteAlquiler.java
    │   └── Main.java
    └── 11-comparacion-verduleria/
        ├── 01-en-capas/
        │   └── README.md
        ├── 02-hexagonal/
        │   ├── dominio/
        │   │   └── Verdura.java
        │   ├── aplicacion/
        │   │   ├── VenderVerdura.java
        │   │   └── puertos/
        │   │       ├── Venta.java
        │   │       └── Inventario.java
        │   ├── infraestructura/
        │   │   ├── ConsolaVentas.java
        │   │   └── InventarioEnMemoria.java
        │   └── Main.java
        └── 03-clean/
            ├── entidades/
            │   └── Verdura.java
            ├── casosdeuso/
            │   ├── VenderVerdura.java
            │   ├── EntradaVenta.java
            │   ├── SalidaVenta.java
            │   └── Inventario.java
            ├── adaptadores/
            │   ├── ControladorVentas.java
            │   └── PresentadorVentas.java
            ├── infraestructura/
            │   └── InventarioEnMemoria.java
            └── Main.java
```

## Objetivos y modalidad por unidad

### 06-matematicas-computacionales

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 01-logica-proposicional | T | Tablas de verdad, conectores e implicación. |
| 02-conjuntos | R | Unión, intersección, diferencia y pertenencia. |
| 03-combinatoria | R | Conteo sin generar todas las posibilidades innecesariamente. |
| 04-matrices | R | Operaciones y dimensiones compatibles. |
| 05-vectores | R | Representación geométrica y magnitud. |
| 06-probabilidad | T | Frecuencias y simulación reproducible con semilla. |
| 07-estadistica | R | Medidas descriptivas y dispersión. |
| 08-precision-numerica | R | Error de representación y aritmética decimal explícita. |
| 09-metodos-numericos | R | Aproximación, convergencia y tolerancia. |

### 07-representacion-de-datos-y-memoria

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 01-bases-numericas | R | Binario, decimal y hexadecimal; descomposición posicional. |
| 02-enteros-sin-signo | R | Interpretación sin signo usando las operaciones de Integer y Byte. |
| 03-complemento-a-dos | R | Representación de negativos, límites y overflow. |
| 04-punto-flotante | R | Signo, exponente, mantisa y valores especiales. |
| 05-orden-bytes | R | ByteBuffer, orden explícito y orden de la plataforma. |
| 06-operaciones-bits | R | AND, OR, XOR, NOT, desplazamiento aritmético y lógico. |
| 07-mascaras | R | Manipulación y codificación de campos sin recorrer cada bit. |
| 08-codificacion-texto | R | Diferencia entre bytes, unidades UTF-16 y puntos de código. |
| 15-referencias-java | R | Java siempre pasa argumentos por valor; referencias copiadas, no punteros C. |
| 18-datos-compuestos | R | Empaquetar campos y comparar composición con struct; layout explícito. |
| 19-stack-heap | R | Llamadas y referencias observables; sin prometer ubicación física ni tamaño de objetos. |
| 20-memoria-dinamica | T | Modelo de reserva y liberación de bloques; el arreglo dinámico ya existe en 02. |
| 21-asignacion-bloques | T | Políticas de asignación con lista de bloques libres. |
| 22-fragmentacion | T | Espacio desperdiciado, bloques contiguos y fusión. |
| 23-memoria-virtual | T | Páginas, marcos y traducción de direcciones. |
| 24-reemplazo-paginas | T | Comparación de fallos de página sobre una secuencia fija. |
| 25-errores-memoria | R | Retención accidental de referencias sin provocar agotamiento real de memoria; índice inválido y referencia nula ya se estudian en 00. |
| 26-recoleccion-basura | T | Grafo de referencias, raíces y simulación mark-and-sweep; no forzar el GC real. |

### 08-sistemas-de-archivos-y-entrada-salida

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 01-rutas | R | Path, raíz, padre, directorio de trabajo y resolución. |
| 02-directorios | R | Crear, listar y recorrer árboles temporales. |
| 03-comodines | R | PathMatcher y patrones glob; no expansión automática del shell. |
| 04-operaciones-archivos | R | Operaciones sobre un directorio temporal propio. |
| 05-metadatos | R | BasicFileAttributes, tamaño y tiempos del sistema. |
| 06-enlaces | P | Distinguir alias de archivo y referencia simbólica; detectar soporte/permisos. |
| 07-permisos | M | Modelar rwx y consultar/cambiar permisos de archivos propios si el FS lo permite. |
| 08-archivos-temporales | R | Crear, usar y eliminar un directorio temporal; archivo temporal simple ya está en 01/archivos-y-nio. |
| 09-flujos-estandar | R | System.in/out/err; entrada fija para pruebas. |
| 10-flujos-bytes | R | Abrir, leer, escribir y cerrar; respetar el número de bytes devueltos. |
| 11-flujos-texto | R | Reader/Writer y codificación explícita. |
| 12-buffering | R | BufferedInputStream/OutputStream y flush; no asumir persistencia física. |
| 13-modos-apertura | R | StandardOpenOption, truncar, crear y anexar. |
| 14-acceso-aleatorio | R | RandomAccessFile/FileChannel y offset de archivo. |
| 15-buffers-nio | R | Posición, límite, capacidad y transferencia parcial. |
| 16-lectura-binaria | R | DataInputStream/DataOutputStream con formato pequeño y documentado. |
| 17-mapear-archivo | R | FileChannel.map; no prometer liberación inmediata del mapeo. |
| 18-bloqueos-archivo | P | FileLock y límites dependientes del sistema; solo archivos propios. |
| 19-monitoreo-directorios | R | WatchService con eventos no garantizados individualmente y espera acotada. |
| 20-compresion | R | Comprimir y descomprimir datos locales; validar rutas al extraer. |

### 09-sistemas-operativos-y-procesos

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 01-capas-sistema | R | Hardware, kernel, JVM y aplicaciones; propiedades os.name/os.arch. |
| 02-argumentos-consola | R | Interpretar opciones y argumentos explícitamente; main(args) ya está en 00/01. |
| 03-entorno | R | Variables seleccionadas y directorio de trabajo; no volcar secretos del entorno. |
| 04-identidad-proceso | R | PID, ProcessHandle y padre opcional. |
| 05-crear-proceso | R | Iniciar una JVM hija mediante ProcessBuilder, sin shell ni fork. |
| 06-aislamiento-procesos | R | Variables independientes y comunicación explícita; comparar con hilos. |
| 07-esperar-proceso | R | waitFor, onExit y código de salida. |
| 08-varios-hijos | R | Crear un número acotado de hijos y recoger sus resultados. |
| 11-estados-procesos | T | Modelo de estados y alternancia de ejecución; no afirmar acceso a registros reales. |
| 13-redireccion | R | Redirigir stdin, stdout y stderr con archivos temporales. |
| 14-pipeline | R | ProcessBuilder.startPipeline y streams entre procesos Java. |
| 15-comunicacion-pipes | R | Intercambio de mensajes a través de streams, cerrar los extremos. |
| 16-timeout-proceso | R | waitFor con timeout; terminar exclusivamente el hijo creado. |
| 17-notificacion-eventos | R | ScheduledExecutorService e interrupción de hilos; no equivalen a señales UNIX. |
| 19-terminacion-jvm | R | Shutdown hook en una JVM hija y límites de terminación abrupta. |
| 20-servicio-segundo-plano | R | Proceso hijo de duración limitada, arranque, consulta y cierre por el padre; no instalar daemon ni servicio persistente. |
| 21-planificacion-fcfs | T | Orden de llegada, espera y retorno. |
| 22-planificacion-sjf-srtf | T | Trabajo más corto y expulsión. |
| 23-planificacion-round-robin | T | Quantum y cola de listos. |
| 24-planificacion-prioridades | T | Prioridad, inanición y envejecimiento. |

### 10-concurrencia-y-paralelismo

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 01-hilos | R | Crear, iniciar y esperar hilos. |
| 02-estados-hilo | R | Observar estados de Thread con coordinación acotada; interrupción básica ya se enseña en EstadoInterrupcion.java. |
| 03-estado-compartido | R | Datos compartidos y estado local por hilo. |
| 04-intercalaciones | M | Leer-modificar-escribir y pérdida de actualización reproducible. |
| 05-seccion-critica | R | Exclusión mutua en un contador compartido. |
| 06-visibilidad | R | Aplicar visibilidad a publicación segura y comparar con bloqueo; volatile básico y lecturas separadas ya existen en 00. |
| 07-atomicos | R | Operaciones atómicas y CAS. |
| 09-locks | R | Bloqueo explícito, espera acotada y condiciones. |
| 10-semaforos | R | P/V como acquire/release; permisos y finally. |
| 11-funciones-seguras | R | Estado compartido, copias defensivas y reentrancia. |
| 12-productor-consumidor-01 | R | Buffer finito, tres semáforos: slots, items y mutex; terminación explícita. |
| 13-productor-consumidor-02 | R | Solución estándar con ArrayBlockingQueue y cierre definido. |
| 14-lectores-escritores-01 | R | Dos semáforos y contador; explicar inanición del escritor. |
| 15-lectores-escritores-02 | R | Restringir entrada de lectores nuevos si esperan escritores; no prometer equidad absoluta. |
| 16-lectores-escritores-03 | R | ReentrantReadWriteLock(true); política aproximadamente por llegada, no FIFO absoluta. |
| 17-filosofos-comensales | R | Recursos compartidos, orden total o árbitro y finalización acotada. |
| 18-barbero-dormilon | R | Cola limitada, espera y señalización; cierre de servicio. |
| 19-fumadores-cigarrillos | R | Sincronización condicional y semáforos; problema clásico abstracto. |
| 20-peterson | T | Exclusión mutua de dos participantes bajo memoria secuencialmente consistente; no usar campos plain como implementación correcta Java. |
| 21-panaderia-lamport | T | Tickets y orden lexicográfico con supuestos de memoria explícitos. |
| 22-progress-graphs | T | Enumerar trayectorias, zonas inseguras y estados alcanzables para un modelo discreto. |
| 23-deadlock-modelo | T | Espera circular sin colgar la JVM. |
| 24-deadlock-prevencion | R | Orden único de adquisición y recuperación mediante timeout. |
| 25-banquero | T | Estado seguro, recursos disponibles, asignación y necesidad. |
| 26-inanicion-equidad | M | Distinguir posibilidad de inanición de una política equitativa aproximada. |
| 27-coordinacion | R | Coordinación de fases y llegada de tareas. |
| 28-pools | R | ExecutorService, ThreadPoolExecutor, cola y política de rechazo. |
| 29-tareas-asincronas | R | Resultados, composición, excepciones y cancelación. |
| 30-paralelismo | R | Dividir y combinar; comparación correcta de resultados. |
| 31-hilos-virtuales | R | Comparar resultado de un lote acotado de tareas bloqueantes con hilos de plataforma y virtuales; sin prometer aceleración ni repetir ConcurrenciaVirtual.java. |
| 32-colecciones-concurrentes | R | Operaciones thread-safe y acciones compuestas. |

### 11-redes-y-protocolos-de-comunicacion

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 01-modelo-cliente-servidor | T | Roles, petición, procesamiento y respuesta sin red todavía. |
| 02-interfaces-red | R | NetworkInterface e InetAddress; solo direcciones necesarias, no escaneo. |
| 03-direccionamiento-ip | R | Representación de 32 y 128 bits. |
| 04-subredes | T | Operaciones con prefijos IPv4; no configuración real. |
| 05-mac-y-tramas | T | Dirección MAC, trama abstracta y campos; sin sockets crudos. |
| 06-conmutacion | T | Aprendizaje de MAC y reenvío simulado. |
| 07-enrutamiento | T | Selección de prefijo más largo sobre rutas ficticias. |
| 08-orden-red | R | Big-endian para campos multibyte de protocolos. |
| 09-puertos | R | Dirección y puerto local, puerto efímero asignado por el SO. |
| 10-dns | M | Resolución mediante InetAddress y modelo de caché/TTL sin depender de Internet. |
| 11-tcp-echo | R | Bind, accept, connect, streams, EOF y cierre en loopback. |
| 12-tcp-framing | R | Longitud de mensaje; TCP es flujo de bytes, no conserva límites de mensajes. |
| 13-tcp-lectura-parcial | R | Procesar bytes acumulados y detectar truncamiento; no asumir tamaño de cada read. |
| 14-udp | R | DatagramSocket, límites de datagrama y timeout. |
| 15-udp-fiabilidad-modelo | T | ACK, pérdida y retransmisión con secuencia fija. |
| 16-ventana-deslizante | T | Flujo, números de secuencia y confirmaciones; modelo no TCP completo. |
| 17-congestion-y-tasa | T | Limitar tasa con reloj simulado; no confundir con control de congestión TCP. |
| 18-servidor-multicliente | R | Un hilo por cliente con número limitado de conexiones. |
| 19-servidor-pool | R | Pool precreado y cola acotada; relacionar con productor-consumidor. |
| 20-servidor-virtual | R | Hilo virtual por conexión, límite de recursos y cierre. |
| 21-sockets-nio | R | Canales no bloqueantes, selector y lecturas/escrituras parciales. |
| 22-http | R | HttpServer del JDK y HttpClient en loopback; método, estado, cabeceras y cuerpo. |
| 23-protocolos-aplicacion | T | Gramática de mensajes, validación, errores y límites. |
| 24-timeouts | R | Timeout de conexión/lectura y cierre ante fallo. |

### 12-persistencia-y-gestion-de-datos

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 01-registros | R | Persistencia de registros de longitud fija con archivos. |
| 02-formatos-texto | R | Formato explícito y delimitadores; CSV de subconjunto documentado. |
| 03-indexacion | R | Índice clave a offset y búsqueda sin escanear todo el archivo. |
| 04-consultas | T | Selección, proyección, agrupamiento sobre datos en memoria. |
| 05-join | T | Equivalencia de resultados y costes de unión relacional. |
| 06-transacciones | T | Atomicidad, commit y rollback en modelo didáctico; no motor ACID real. |
| 07-aislamiento | T | Anomalías de transacciones mediante intercalaciones controladas. |
| 08-write-ahead-log | T | Escritura previa y recuperación simulada. |
| 09-jdbc-api | T | Connection, PreparedStatement y ResultSet como contrato conceptual; no conexión real sin driver, usar dobles explícitos sin fingir una BD. |

### 13-criptografia-y-seguridad

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 01-integridad | R | Huella criptográfica e integridad; hash no equivale a autenticación. |
| 02-checksum | R | Checksum elemental y sus colisiones; CRC se estudia en 14. No es una función criptográfica. |
| 03-autenticacion-mensajes | R | Autenticidad e integridad con clave compartida. |
| 04-aleatoriedad | R | Aleatoriedad criptográfica; no exigir valores exactos de salida. |
| 05-contrasenas | R | Sal aleatoria y derivación; parámetros de práctica, no receta de producción. |
| 06-cifrado-simetrico | R | Cifrado autenticado, nonce único y verificación de etiqueta. |
| 07-claves-publicas | R | Generación de claves y firma/verificación, no usar claves reales. |
| 08-intercambio-claves | R | Acuerdo de secreto; no constituye autenticación por sí solo. |
| 09-certificados | R | Interpretar certificado de prueba empaquetado o generado localmente; no confiar solo por leerlo. |
| 10-validacion-entradas | R | Límites de mensajes y confinamiento de rutas en temporales. |
| 11-control-acceso | T | Autorización por roles simulada; distinta de sincronización lectores-escritores. |

### 14-comunicacion-con-dispositivos

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 03-transmision-serial | T | Modelar trama serial con inicio, datos, paridad y parada como secuencia de bits, sin implementar controlador físico. |
| 04-paridad | T | Detectar cambios de paridad y límites de detección. |
| 05-crc-y-hamming | T | Implementar CRC elemental y Hamming(7,4) para detectar/corregir errores; comparar con CRC32 del JDK solo como comprobación. |
| 06-canal-serial | T | Flujo de bytes, delimitadores y recepción fragmentada. |
| 08-mensajes-medicion-y-control | T | Procesar una medición recibida y producir un comando textual; sin sensor, GPIO ni hardware. |
| 11-telemetria | T | Campos binarios, checksum, límites y reensamblaje. |
| 12-interoperabilidad-nativa | D | Explicar límites de la API Java 21 para puertos seriales y JNI/FFM; no integrar drivers, C ni API preview obligatoria. |

### 15-graficos-y-multimedia

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 01-pixeles | R | Componentes RGB/ARGB y coordenadas con BufferedImage. |
| 02-archivos-imagen | R | ImageIO y archivos temporales, sin imágenes externas. |
| 03-transformaciones | T | Transformaciones geométricas y matrices. |
| 04-procesamiento-imagen | R | Filtros de píxeles sobre imágenes generadas. |
| 05-rasterizacion | T | Aproximación de líneas en cuadrícula. |
| 06-graficos-2d | R | Graphics2D sobre BufferedImage, sin ventana ni servidor gráfico. |
| 07-audio-digital | R | Muestreo, amplitud y generación de archivo WAV sin reproducir audio. |

### 16-interpretes-y-procesamiento-de-lenguajes

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 01-cadena-compilacion | R | Compilar una clase mínima con javac y distinguir fuente y clase; comandos en comentarios, sin escribir un gestor de compilación. |
| 02-bytecode | R | Inspeccionar bytecode con javap -c; no simular CPU. |
| 05-biblioteca-jar | R | Compilar biblioteca mínima y consumir un JAR con jar y classpath; sin Maven. |
| 07-carga-clases | R | Observar carga e inicialización de una clase local con Class.forName; no crear sistema de plugins. |
| 10-lexer | T | Reconocer números, operadores e identificadores con una gramática mínima. |
| 11-parser-descenso-recursivo | T | Precedencia, asociación y árbol sintáctico; lexer mínimo local, sin dependencias cruzadas. |
| 12-shunting-yard | T | Convertir infija a postfija utilizando una pila. |
| 13-evaluacion-ast | T | Evaluar un árbol de expresión pequeño. |
| 14-maquina-estados-comandos | R | Reconocer comandos sencillos y distinguir estados, entradas válidas e inválidas; no construir máquina virtual. |

### 17-sistemas-distribuidos

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 01-mensajeria | T | Comunicación asíncrona y orden de entrega simulado. |
| 02-relojes-logicos | T | Orden causal parcial y marcas de tiempo lógicas. |
| 03-relojes-vectoriales | T | Distinguir precedencia causal de concurrencia. |
| 04-reintentos | T | Timeout, fallos transitorios y backoff en reloj simulado. |
| 05-idempotencia | T | Deduplicar mensajes con identificadores estables. |
| 06-heartbeat | T | Sospechas de fallo bajo tiempo simulado; no certeza absoluta. |
| 07-eleccion-lider | T | Elección bajo supuestos explícitos, participantes fijos y entrega fiable. |
| 08-replicacion | T | Copia primaria/secundaria y retraso controlado. |
| 09-consistencia | T | Visibilidad de escrituras, quórums bajo supuestos; no consenso completo. |
| 10-exclusion-distribuida | T | Acceso exclusivo mediante token bajo entrega fiable y sin pérdidas. |

### 18-pruebas-depuracion-y-rendimiento

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 01-contratos | R | Validación de entradas, resultados e invariantes. |
| 02-pruebas-unitarias | R | Pruebas sin JUnit, AssertionError explícito y código de salida. |
| 03-pruebas-limites | R | Vacío, mínimos, máximos, valores inválidos. |
| 04-pruebas-propiedades | R | Generar casos con semilla fija y comprobar invariantes. |
| 05-pruebas-concurrencia | R | Coordinación determinista, límite de espera y propagación de fallos. |
| 06-trazas | R | Stack trace, causas y localización del fallo. |
| 07-depuracion-jdb | R | javac -g y jdb: breakpoints, step, next, variables y backtrace; comandos comentados. |
| 08-registro-eventos | R | java.util.logging y niveles, sin framework. |
| 09-medicion-tiempo | R | Tiempo transcurrido, calentamiento y límites de microbenchmarks. |
| 10-memoria-jvm | R | MemoryMXBean y referencias retenidas; no inferir direcciones reales. |
| 11-diagnostico-hilos | M | ThreadMXBean y modelo de detección; no dejar hilos bloqueados. |
| 12-flight-recorder | R | Grabación acotada y análisis de eventos con JDK Flight Recorder. |
| 13-herramientas-jdk | P | jcmd/jstack solo sobre una JVM hija propia; disponibilidad y permisos. |
| 14-empaquetado | R | jar, java -jar y comandos de jpackage explicados; instalador no obligatorio para practicar. |

### 19-inteligencia-artificial-fundamental

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 01-comparacion-de-patrones | R | Comparar dos matrices binarias 5x5 mediante diferencias de píxeles; devolver distancia exacta. |
| 02-reconocimiento-de-digitos | R | Clasificar una matriz pequeña por plantillas 0–9; probar coincidencia y ruido acotado; no confundir con OCR general. |
| 03-distancias | R | Calcular distancia Manhattan con bucle; Euclidiana ya en 06/05. Comparar con un caso conocido. |
| 04-clasificacion-knn | R | Medir distancias, seleccionar k vecinos y votar con desempate explícito; datos pequeños. |
| 05-perceptron | R | Aprender una frontera lineal con pesos, umbral y actualización explícita; AND/OR como datos binarios, no electrónica. |
| 06-red-neuronal-elemental | R | Propagación y ajuste de pesos con dos capas pequeñas; operaciones explícitas; verificar descenso de error y límites. |
| 07-agrupamiento-kmeans | R | Asignar puntos a centroides y recalcular medias; semilla fija y límite de iteraciones. |
| 08-busqueda-heuristica | R | A* en cuadrícula pequeña; explicar heurística admisible y comparar con coste óptimo, sin reimplementar Dijkstra como ejercicio independiente. |
| 09-algoritmos-geneticos | R | Selección, cruce y mutación sobre cadenas binarias; semilla fija, generaciones acotadas. |
| 10-regresion-lineal | R | Ajustar pendiente e intercepto con error cuadrático y bucles; distinguir predicción y clasificación. |
| 11-evaluacion-modelos | R | Separar datos de evaluación y entrenamiento; exactitud, precisión y exhaustividad, divisiones por cero. |
| 12-minimax | R | Árbol de decisiones pequeño de un juego sencillo; alternar máximo y mínimo. |

### 20-arquitectura-de-software

| Unidad | Modalidad | Objetivo y criterio conceptual |
|---|---|---|
| 01-en-capas-verduleria | R | Tienda de verduras: vender dos unidades, descontar inventario, rechazar venta sin existencias. Dependencia presentación → servicio → persistencia; dominio sencillo. |
| 02-mvc-biblioteca | R | Biblioteca: prestar y devolver un libro. Vista muestra estado, controlador traduce acciones, modelo impone disponibilidad. |
| 03-mvp-panaderia | R | Panadería: registrar pedido y mostrar total; presentador controla una vista pasiva mediante contrato. |
| 04-mvvm-heladeria | R | Heladería: elegir sabor/cantidad y observar el total. PropertyChangeSupport o listener explícito para estado observable; sin JavaFX. |
| 05-hexagonal-centro-ecuestre | R | Centro ecuestre: reservar caballo disponible y rechazar segunda reserva. Puertos de entrada/salida propios de aplicación, adaptadores implementan contratos; dominio independiente. |
| 06-clean-peluqueria-canina | R | Peluquería canina: reservar hora libre y rechazar ocupada; reglas de dependencias hacia dentro, límites de entrada/salida y presentación separada. |
| 07-onion-cabanas | R | Cabañas turísticas: reservar cabaña libre. Contrato de repositorio interior, servicio de aplicación y capas externas con dependencias hacia dominio. |
| 08-eventos-restaurante | R | Restaurante: cocina marca pedido listo; mostrador recibe evento sin dependencia directa de cocina. Publicación síncrona en memoria, no broker real. |
| 09-monolito-modular-supermercado | R | Supermercado: comprar stock y vender; un único proceso, módulos con API pública mínima. Sin dependencias cíclicas ni acceso directo a internals. |
| 10-microservicios-bicicletas | R | Alquiler de bicicletas: dos JVM hijas con HTTP loopback, puertos efímeros, reserva y disponibilidad. Estado privado por servicio y contrato HTTP explícito; detener hijos. No pretender garantías distribuidas de producción. |
| 11-comparacion-verduleria/01-en-capas | D | Misma venta de verduras en capas. Reutilizar el ejemplo 20/01 como referencia documental en lugar de copiar fuentes. |
| 11-comparacion-verduleria/02-hexagonal | R | Misma venta y salida que 20/01; cambiar dependencias hacia puertos y adaptadores. |
| 11-comparacion-verduleria/03-clean | R | Misma venta y salida que 20/01; separar límites y respetar regla de dependencia interna. |

## Eliminaciones y referencias cruzadas

| Área | Unidad | Motivo o referencia |
|---|---|---|
| 07 | 09-logica-digital | Arquitectura electrónica/ISA fuera del alcance. La pila para expresiones se estudia en 16; caché lógica y políticas de memoria se estudian en 07/24. |
| 07 | 10-sumadores | Arquitectura electrónica/ISA fuera del alcance. La pila para expresiones se estudia en 16; caché lógica y políticas de memoria se estudian en 07/24. |
| 07 | 11-registros-y-alu | Arquitectura electrónica/ISA fuera del alcance. La pila para expresiones se estudia en 16; caché lógica y políticas de memoria se estudian en 07/24. |
| 07 | 12-ciclo-de-instruccion | Arquitectura electrónica/ISA fuera del alcance. La pila para expresiones se estudia en 16; caché lógica y políticas de memoria se estudian en 07/24. |
| 07 | 13-pila-de-operandos | Arquitectura electrónica/ISA fuera del alcance. La pila para expresiones se estudia en 16; caché lógica y políticas de memoria se estudian en 07/24. |
| 07 | 14-jerarquia-memoria | Arquitectura electrónica/ISA fuera del alcance. La pila para expresiones se estudia en 16; caché lógica y políticas de memoria se estudian en 07/24. |
| 07 | 16-punteros-simulados | Punteros de C: reservar para C; Java usa referencias. Los offsets de buffers se estudian en 08/15. |
| 07 | 17-cadenas-estilo-c | Cadenas terminadas en cero: reservar para C; Java estudia codificaciones en 07/08. |
| 08 | 21-recursos | try-with-resources y excepciones suprimidas ya se enseñan en 00/14 y 01/excepciones-y-recursos. |
| 09 | 09-fork-modelo | POSIX específico: explicar diferencia con ProcessBuilder y reservar prácticas de fork/exec/señales/zombis para C; sin falsa equivalencia. |
| 09 | 10-exec-modelo | POSIX específico: explicar diferencia con ProcessBuilder y reservar prácticas de fork/exec/señales/zombis para C; sin falsa equivalencia. |
| 09 | 12-zombis-y-huerfanos | POSIX específico: explicar diferencia con ProcessBuilder y reservar prácticas de fork/exec/señales/zombis para C; sin falsa equivalencia. |
| 09 | 18-senales-unix-modelo | POSIX específico: explicar diferencia con ProcessBuilder y reservar prácticas de fork/exec/señales/zombis para C; sin falsa equivalencia. |
| 10 | 08-monitores | MonitorReentrante.java, EsperaMonitor.java y NotificarSinMonitor.java ya están en 00; aplicarlos a productor-consumidor. |
| 14 | 01-senales-digitales | Excluir hardware, registros, bus físico, ADC/PWM e interrupciones electrónicas; conservar transmisión y codificación de mensajes. |
| 14 | 02-registros-bits | Excluir hardware, registros, bus físico, ADC/PWM e interrupciones electrónicas; conservar transmisión y codificación de mensajes. |
| 14 | 07-protocolos-bus | Excluir hardware, registros, bus físico, ADC/PWM e interrupciones electrónicas; conservar transmisión y codificación de mensajes. |
| 14 | 09-muestreo-y-conversion | Excluir hardware, registros, bus físico, ADC/PWM e interrupciones electrónicas; conservar transmisión y codificación de mensajes. |
| 14 | 10-interrupciones | Excluir hardware, registros, bus físico, ADC/PWM e interrupciones electrónicas; conservar transmisión y codificación de mensajes. |
| 16 | 03-compilacion-separada | Evitar duplicar paquetes/módulos/compatibilidad binaria de 00 o introducir compilador/VM/andamiaje completo; herramientas básicas quedan en 16 y 18. |
| 16 | 04-classpath | Evitar duplicar paquetes/módulos/compatibilidad binaria de 00 o introducir compilador/VM/andamiaje completo; herramientas básicas quedan en 16 y 18. |
| 16 | 06-versiones-biblioteca | Evitar duplicar paquetes/módulos/compatibilidad binaria de 00 o introducir compilador/VM/andamiaje completo; herramientas básicas quedan en 16 y 18. |
| 16 | 08-modulos | Evitar duplicar paquetes/módulos/compatibilidad binaria de 00 o introducir compilador/VM/andamiaje completo; herramientas básicas quedan en 16 y 18. |
| 16 | 09-construccion-incremental | Evitar duplicar paquetes/módulos/compatibilidad binaria de 00 o introducir compilador/VM/andamiaje completo; herramientas básicas quedan en 16 y 18. |
| 16 | 14-interprete-pila | Evitar duplicar paquetes/módulos/compatibilidad binaria de 00 o introducir compilador/VM/andamiaje completo; herramientas básicas quedan en 16 y 18. |
| 07 | 20-memoria-dinamica | 02-estructuras-datos/arreglo-dinamico/VectorEnteros.java: no recrear arreglo dinámico. |
| 08 | 08-archivos-temporales | 01-fundamentos-en-accion/archivos-y-nio/ArchivosYNio.java: mantener solo directorio temporal. |
| 09 | 02-argumentos-consola | 00-fundamentos-especificacion-oficial/01-introduccion/01-02-programas-de-ejemplo/ArgumentosJls.java: solo opciones nuevas. |
| 10 | 31-hilos-virtuales | 01-fundamentos-en-accion/concurrencia-virtual/ConcurrenciaVirtual.java: comparar modelos, no repetir ejemplo. |
| 11 | 07-enrutamiento | 03-algoritmos/dijkstra/Dijkstra.java: prefijo más largo es distinto; no duplicar caminos mínimos. |
| 19 | 03-distancias | 06/05 DistanciaEuclidiana.java: referencia; añadir Manhattan solamente. |
| 20 | 11-comparacion-verduleria | 20/01: referencia, no duplicar árbol de capas. |

La revisión se basa en el catálogo actual y en ejemplos concretos ya inspeccionados. No equivale a una auditoría semántica de cada archivo histórico: antes de implementar cada bloque, revisar el contenido de las referencias cercanas, eliminar duplicados exactos y preservar diferencias de propósito. No borrar ni editar 00–05 para evitar redundancias.

## Validación e integración

- Manifiesto externo por punto de entrada: ruta de unidad, fuentes, main o módulo, argumentos, pasos de compilación, salida/invariante/diagnóstico esperado, modalidad, timeout y limpieza. Varios mains independientes = varios casos; no exigir salida idéntica si el mecanismo permite intercalaciones válidas.
- Compilación `javac --release 21 -encoding UTF-8 -d <temporal>` por unidad y etapa; no mezclar clases homónimas ni proyectos independientes. Las arquitecturas compilan desde la raíz de su microproyecto con paquetes correspondientes a rutas.
- Concurrencia: invariantes (exclusión, orden permitido, integridad), coordinación determinista, join/timeout, finalización; no depender de sleep para probar exclusión. Peterson y panadería son modelos de memoria explícita, no implementaciones Java correctas con campos ordinarios.
- E/S, procesos y redes: archivos temporales propios, limpieza incluso si falla el caso, hijos propios, loopback y puertos efímeros, tiempos de espera; no usar red pública, servicios persistentes ni dispositivos. Permisos POSIX/links/herramientas específicas: detectar soporte, distinguir omisión de éxito.
- Matemáticas e IA: valores de referencia, tolerancia numérica, semillas reproducibles, entradas inválidas, no usar resultados de entrenamiento como evaluación independiente. Criptografía: APIs JDK probadas, claves ficticias y datos de prueba, sin inventar cifrado propio para uso real.
- Arquitectura: dos escenarios (válido/rechazado), dominios comprensibles, verificar dependencias de paquetes y ausencia de ciclos indebidos; comparar capas/hexagonal/clean con misma salida, sin imponer una arquitectura a ejercicios normales. Microservicios requieren dos procesos, no solo dos clases.
- Catálogo offline: añadir rutas solo de archivos realmente implementados, verificar que cada ruta corresponde a recurso empaquetado; incrementar versión cuando cambie el contenido. Revisar el generador de guías y verificadores para no aplicar compilación global heredada a 06–20. Comprobar el grafo y reapertura de sesiones existentes.
- Verificar CI por bloque y al final: pruebas JDK por unidad, catálogo, pruebas app, empaquetado Windows, casos Linux/Windows; no publicar instalador hasta cierre conjunto.

## Secuencia de trabajo pendiente

1. Separar en el repositorio el alcance congelado de Java 00–05 de los bloques nuevos y registrar este plan. Evitar modificar los fuentes aprobados.
2. Preparar manifiesto y verificador aislado, excepciones de plataforma y catálogo; probar compatibilidad con las pruebas antiguas.
3. Implementar y validar 06 y 07: matemáticas, datos y memoria; revisar modelos y referencias existentes.
4. Implementar 08–11: archivos, procesos, concurrencia y redes. Prioridad a los problemas de la Unidad 6 (incluidos lectores-escritores y productor-consumidor).
5. Implementar 12–16: persistencia, seguridad, comunicación, multimedia e intérpretes mínimos.
6. Implementar 17–20: sistemas distribuidos, diagnóstico, IA y microproyectos arquitectónicos. El diagnóstico es transversal desde el paso 2.
7. Auditoría conceptual por bloque y verificación integral de Java; catálogo, compilación, resultados, grafo, empaquetado, sesiones.
8. C: biblioteca pedagógica de sistemas con las seis unidades docentes, POSIX y memoria explícita.
9. TypeScript: terminar clasificación e integración de bloques originales importados, conservar atribución y sustituir solo fundamentos antiguos.
10. Pruebas integrales y publicación de instalador definitivo; no confundir CI aprobada con release publicada.

Las rutas acordadas ya se implementaron; los ajustes posteriores deben estar justificados por validación, redundancia o revisión pedagógica.
