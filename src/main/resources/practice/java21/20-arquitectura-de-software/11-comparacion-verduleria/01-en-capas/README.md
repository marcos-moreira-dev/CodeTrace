# Comparación — verdulería en capas

La implementación de referencia es [01-en-capas-verduleria](../../01-en-capas-verduleria/). No se duplican sus fuentes.

## Mismo escenario para las tres arquitecturas

- Inventario inicial: 5 tomates.
- Vender 2: aceptación, stock 3.
- Vender 4: rechazo, stock permanece 3.

## Salida de referencia

```text
Venta: tomate x2
Stock: 3
Venta rechazada
Stock: 3
```

## Qué comparar

En capas, el servicio importa una persistencia concreta; en hexagonal, la aplicación define puertos y la infraestructura los implementa; en Clean Architecture, el caso de uso define límites de entrada y salida y los adaptadores traducen datos. Las reglas de stock se mantienen en las entidades.

Compilar y ejecutar desde la raíz de cada microproyecto siguiendo su Main.java. La comparación no implica que un patrón sea siempre superior; en una aplicación pequeña, la separación adicional puede no compensar su costo. Todos los repositorios son de prueba en memoria.
