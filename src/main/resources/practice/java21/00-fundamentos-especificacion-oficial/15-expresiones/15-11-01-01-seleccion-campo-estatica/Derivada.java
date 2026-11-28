// Tema:
// El acceso a un campo de instancia se resuelve según el tipo estático de la expresión
// receptora. Cambiar el objeto real no cambia qué declaración de campo se selecciona.
// Compilar: javac --release 21 -encoding UTF-8 -d . Base.java Derivada.java SeleccionCampo.java
// Ejecutar: java SeleccionCampo
// Comandos desde la carpeta de la unidad.

class Derivada extends Base { int dato = 7; }

// Salida esperada:
// 7
// 3
// 3
