// Tema:
// El acceso a un campo de instancia se resuelve según el tipo estático de la expresión
// receptora. Cambiar el objeto real no cambia qué declaración de campo se selecciona.
// Compilar: javac --release 21 -encoding UTF-8 -d . Base.java Derivada.java SeleccionCampo.java
// Ejecutar: java SeleccionCampo
// Comandos desde la carpeta de la unidad.

class SeleccionCampo {
    public static void main(String[] args) {
        Derivada d = new Derivada();
        Base b = new Base();
        System.out.println(d.dato);
        System.out.println(b.dato);
        b = d;
        System.out.println(b.dato);
    }
}

// Salida esperada:
// 7
// 3
// 3
