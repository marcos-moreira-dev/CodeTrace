// Tema:
// Los parámetros de tipo suelen usar letras breves que indican su papel, como E para elemento,
// K para clave, V para valor y T para tipo.
// Compilar: javac --release 21 -encoding UTF-8 -d . Contenedor.java Par.java Calculo.java NombresGenericos.java
// Ejecutar: java NombresGenericos
// Comandos desde la carpeta de la unidad.

class NombresGenericos {
    public static void main(String[] args) throws Exception {
        Contenedor<String> c = new Contenedor<>();
        c.elemento = "texto";
        Par<String,Integer> p = new Par<>("clave", 7);
        Calculo<Integer,Exception> f = () -> p.valor();
        System.out.println(c.elemento);
        System.out.println(p.clave() + " " + f.evaluar());
    }
}

// Salida esperada:
// texto
// clave 7
