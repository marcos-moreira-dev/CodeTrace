# Interoperabilidad nativa — Java 21

## Objetivo
Distinguir las API portables del JDK de la comunicación con dispositivos dependiente del sistema operativo.

- Java SE 21 no ofrece una API estándar general para configurar puertos seriales, GPIO o controladores de dispositivos. Leer un archivo con java.io o NIO no configura automáticamente un puerto ni sustituye un driver.
- JNI permite invocar funciones nativas de una biblioteca compilada para el sistema y arquitectura. Introduce responsabilidades de memoria, compatibilidad y seguridad fuera de la JVM.
- Foreign Function & Memory (FFM) en Java 21 es una API preview (JEP 442). Requiere habilitar preview y puede depender de permisos de acceso nativo. No se exige en CodeTrace.
- Un adaptador real debe definir un contrato de bytes, configurar el dispositivo mediante una biblioteca apropiada, validar datos, gestionar timeouts y cerrar recursos.

## Comprobación conceptual
Explicar qué parte del canal simulado se sustituiría al integrar un adaptador real y por qué el decodificador puede probarse sin hardware.

Esta unidad es documental, sin compilación ni instalación de drivers.
