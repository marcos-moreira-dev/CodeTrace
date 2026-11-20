// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar (PowerShell, copia del paquete): tsc --strict --target ES2022 --module commonjs --noEmit '.\04-patrones-diseno\adapter\unidades\adapter_unidades.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\04-patrones-diseno\adapter\unidades\adapter_unidades.ts'; node "$env:TEMP\ct-ts\adapter_unidades.js"

/**
 * Cliente Adapter: integrar una API Fahrenheit en dominio Celsius.
 *
 * Un adaptador satisface el contrato de negocio y transforma
 * las unidades del proveedor. Así el resto del sistema no necesita
 * conocer el lenguaje ni las particularidades de la API anterior.
 */
interface SensorCelsius { leer(): number; }
class Legado {
    fahrenheit(): number { return 77; }
}
class Adaptador implements SensorCelsius {
    constructor(private readonly antiguo: Legado) {}
    leer(): number { return (this.antiguo.fahrenheit() - 32) * 5 / 9; }
}
function mostrar(sensor: SensorCelsius): void { console.log(sensor.leer()); }
mostrar(new Adaptador(new Legado()));
