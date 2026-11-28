// Tema: await using declaration.
// Una conexión simulada implementa AsyncDisposable; no requiere un servidor de base de datos.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 --lib ES2022,DOM,ESNext.Disposable 03.ts
// Ejecutar: node 03.js
// Salida esperada:
// Doing some work...
// Connection closed.

class DatabaseConnection implements AsyncDisposable {
    async [Symbol.asyncDispose](): Promise<void> { console.log("Connection closed."); }
}
async function doWork(): Promise<void> {
    await using connection = new DatabaseConnection();
    console.log("Doing some work...");
}
void doWork();
