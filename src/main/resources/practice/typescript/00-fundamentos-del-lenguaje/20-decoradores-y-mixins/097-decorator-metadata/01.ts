// Tema: decorator metadata.
// El contexto de un decorador puede ofrecer un objeto de metadatos. Su disponibilidad en ejecución depende del runtime; esta prueba es estática.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 --lib ES2022,DOM,ESNext.Decorators 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

function marca(_: undefined, context: ClassFieldDecoratorContext): void {
    if (context.metadata) context.metadata["campo"] = String(context.name);
}
class Persona { @marca nombre = "Ana"; }
const p = new Persona();
