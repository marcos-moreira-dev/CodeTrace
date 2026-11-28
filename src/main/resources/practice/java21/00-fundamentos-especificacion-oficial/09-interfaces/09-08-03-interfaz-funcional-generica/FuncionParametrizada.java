// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.8; ejemplo 9.8-3
// Compilar: javac --release 21 -encoding UTF-8 -d . Base.java FuncionA.java FuncionB.java Funcion.java FuncionParametrizada.java
// Ejecutar: java FuncionParametrizada
// Comandos desde la carpeta de la unidad.

class FuncionParametrizada {
    public static void main(String[] args) {
        Funcion<String,String> f = c -> c.getSimpleName();
        System.out.println(f.aplicar(Integer.class));
        // Funcion<String,Integer> no admite un único retorno compatible.
    }
}

// Salida esperada:
// Integer
