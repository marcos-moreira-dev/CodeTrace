// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde la raíz java21 (PowerShell):
// javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: usar desde la clase cliente Main... del mismo ejemplo

/** Modelo mínimo de inventario: unidades nunca negativas. */
public final class Inventario {
    private final String sku;
    private int unidades;

    public Inventario(String sku, int unidades) {
        if (sku == null || sku.isBlank() || unidades < 0)
            throw new IllegalArgumentException("Datos de inventario");
        this.sku = sku;
        this.unidades = unidades;
    }

    public void despachar(int solicitadas) {
        if (solicitadas <= 0) throw new IllegalArgumentException("Cantidad positiva");
        if (solicitadas > unidades)
            throw new StockInsuficienteException(sku, solicitadas, unidades);
        unidades -= solicitadas;
    }
    public int disponibles() { return unidades; }
}
