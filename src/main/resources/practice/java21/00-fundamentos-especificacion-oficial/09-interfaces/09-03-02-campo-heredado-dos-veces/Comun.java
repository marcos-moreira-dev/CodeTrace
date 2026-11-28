// Tema:
// Si varias superinterfaces heredan el mismo campo original, solo existe una declaración. El
// nombre no es ambiguo por tener varias rutas de herencia.
// Compilar: javac --release 21 -encoding UTF-8 -d . Comun.java PrimeraRuta.java SegundaRuta.java DobleRuta.java CampoCompartido.java
// Ejecutar: java CampoCompartido
// Comandos desde la carpeta de la unidad.

interface Comun { int VALOR = 12; }

// Salida esperada:
// 12
