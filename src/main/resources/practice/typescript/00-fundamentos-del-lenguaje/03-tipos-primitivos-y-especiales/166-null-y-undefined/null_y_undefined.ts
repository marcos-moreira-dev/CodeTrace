// Tema: Ausencia de valor
// null representa ausencia explícita y undefined una ausencia sin inicializar. ?? aplica un valor alternativo solo a estos dos casos.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 null_y_undefined.ts
// Ejecutar: node null_y_undefined.js
// Salida esperada:
// sin dato
// sin dato
// Ana

function mostrar(valor: string | null | undefined): void {
    console.log(valor ?? 'sin dato');
}
mostrar(null);
mostrar(undefined);
mostrar('Ana');
