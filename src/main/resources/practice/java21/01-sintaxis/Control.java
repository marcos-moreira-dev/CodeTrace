public class Control {
    public static int sumarPares(int limite) {
        int total = 0;
        for (int i = 0; i <= limite; i++) {
            if (i % 2 == 0) {
                total += i;
            }
        }
        return total;
    }
}
