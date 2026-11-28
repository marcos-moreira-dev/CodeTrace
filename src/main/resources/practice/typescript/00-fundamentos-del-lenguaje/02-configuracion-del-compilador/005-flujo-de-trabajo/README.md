# Flujo de trabajo

Desde una carpeta de práctica, con TypeScript instalado:

```text
tsc --version
tsc --init
tsc -p .
tsc --watch
```

Para revisar la configuración efectiva: `tsc --showConfig`. `--noEmit` comprueba los tipos sin generar JS. TypeScript 7 usa una implementación nativa y retira opciones antiguas como target ES5 y moduleResolution classic/node10; usa NodeNext o bundler según tu entorno. El editor consume un servicio de lenguaje para autocompletar y diagnosticar; no es el ejecutor de JavaScript. Las opciones de proyecto no se mezclan con rutas de archivos en la misma invocación.
