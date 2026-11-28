# Tipos de paquetes

Un paquete puede incluir declaraciones .d.ts o depender de tipos publicados bajo @types. Instala el paquete real y sus declaraciones si las necesita. `tsc --noEmit` comprueba que las importaciones y contratos se resuelvan. Una declaración nunca instala la implementación del paquete.
