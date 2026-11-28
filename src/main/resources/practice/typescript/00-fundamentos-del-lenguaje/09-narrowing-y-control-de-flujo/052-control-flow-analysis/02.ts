// Tema: control flow analysis.
// El compilador sigue ramas, asignaciones y salidas anticipadas para deducir el tipo posible en cada punto.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Diagnóstico esperado: TS18046, TS2339; no ejecutar.

const f1 = (x: unknown) => {
    let isString = typeof x === 'string';
    if (isString) {
        x.length; // Error, no narrowing because isString it is not const
    }
};

const f6 = (
    obj: { kind: 'foo'; foo: string } | { kind: 'bar'; bar: number }
) => {
    const isFoo = obj.kind === 'foo';
    obj = obj;
    if (isFoo) {
        obj.foo; // Error, no narrowing because obj is assigned in function body
    }
};
