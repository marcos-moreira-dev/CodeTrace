// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.8; ejemplo 9.8-3
// Compilar: javac --release 21 -encoding UTF-8 -d . Base.java FuncionA.java FuncionB.java Funcion.java FuncionParametrizada.java
// Ejecutar: java FuncionParametrizada
// Tema JLS: Se implementa explícitamente la firma con Class raw. La clase anónima devuelve String y satisface los tres contratos; no se presupone que una lambda sea aceptada con estas firmas.
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
