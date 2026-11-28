// Tema:
// Un tipo declarado en el paquete puede tener prioridad sobre un nombre ofrecido por una
// importación bajo demanda. El nombre calificado permite desambiguar.
// Compilar: javac --release 21 -encoding UTF-8 -d . Date.java TipoPreferido.java
// Ejecutar: java TipoPreferido
// Comandos desde la carpeta de la unidad.

class Date { int dia = 12; }

// Salida esperada:
// 12
