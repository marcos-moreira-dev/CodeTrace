// Tema:
// La referencia del componente List no se reasigna automáticamente, pero el objeto List
// puede ser mutable. Un record no ofrece inmutabilidad profunda por sí solo.
// Compilar: javac --release 21 -encoding UTF-8 -d . Grupo.java RecordSuperficial.java
// Ejecutar: java RecordSuperficial
// Comandos desde la carpeta de la unidad.

record Grupo(java.util.List<String> nombres) {}

// Salida esperada:
// [Ana, Luis]
