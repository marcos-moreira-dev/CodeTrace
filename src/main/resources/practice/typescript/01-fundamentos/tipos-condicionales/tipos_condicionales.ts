// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar (PowerShell, copia del paquete): tsc --strict --target ES2022 --module commonjs --noEmit '.\01-fundamentos\tipos-condicionales\tipos_condicionales.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\01-fundamentos\tipos-condicionales\tipos_condicionales.ts'; node "$env:TEMP\ct-ts\tipos_condicionales.js"

/**
 * Cliente de mapped types, tipos condicionales y utilidades estándar.
 *
 * Partial<T> permite actualizaciones parciales; Pick<T,K> selecciona
 * propiedades. Un tipo condicional expresa reglas de transformación.
 * Ninguna restricción se ejecuta por sí sola en JavaScript: el sistema
 * de tipos existe durante la compilación de TypeScript.
 */
interface Producto { codigo: string; precio: number; disponible: boolean; }
type Publico = Pick<Producto, "codigo" | "precio">;
type Parcial = Partial<Producto>;
type Respuesta<T> = T extends string ? { texto: T } : { valor: T };
function actualizar(p: Producto, cambios: Parcial): Producto {
    return { ...p, ...cambios };
}
const producto: Producto = { codigo: "A", precio: 12, disponible: true };
const publico: Publico = producto;
const respuesta: Respuesta<number> = { valor: 42 };
console.log(actualizar(producto, { disponible: false }), publico, respuesta);
