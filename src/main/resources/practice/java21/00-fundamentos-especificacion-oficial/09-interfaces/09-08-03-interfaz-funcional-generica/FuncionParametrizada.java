// Tema:
// Una interfaz genérica puede tener parametrizaciones con retornos compatibles y otras que no
// lo son. Aquí String satisface ambos contratos; la implementación explícita utiliza la firma
// con Class raw.
// Compilar: javac --release 21 -encoding UTF-8 -d . Base.java FuncionA.java FuncionB.java Funcion.java FuncionParametrizada.java
// Ejecutar: java FuncionParametrizada
// Comandos desde la carpeta de la unidad.

class FuncionParametrizada {
    public static void main(String[] args) {
        Funcion<String,String> f = new Funcion<String,String>() {
            @Override public String aplicar(Class c) {
                return c.getSimpleName();
            }
        };
        System.out.println(f.aplicar(Integer.class));
        // Funcion<String,Integer> no admite un único retorno compatible.
    }
}

// Salida esperada:
// Integer
