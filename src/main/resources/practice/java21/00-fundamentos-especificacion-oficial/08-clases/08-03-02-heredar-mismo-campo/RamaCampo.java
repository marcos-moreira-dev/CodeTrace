// Tema:
// Heredar un mismo campo por varias rutas no crea varias declaraciones distintas. El nombre
// sigue siendo inequívoco si todas las rutas llegan al mismo campo.
// Compilar: javac --release 21 -encoding UTF-8 -d . RaizCampo.java RamaCampo.java BaseCampo.java ReherenciaCampo.java
// Ejecutar: java ReherenciaCampo
// Comandos desde la carpeta de la unidad.

interface RamaCampo extends RaizCampo {}

// Salida esperada:
// 9
