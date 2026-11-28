// Tema: thistype t.
// ThisType ayuda a determinar el tipo contextual de this en métodos de objetos.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Diagnóstico esperado: TS2339; no ejecutar.

type Logger = {
    log: (error: string) => void;
};

let helperFunctions: { [name: string]: Function } & ThisType<Logger> = {
    hello: function () {
        this.log('some error'); // Válido as "log" is a part of "this".
        this.update(); // Inválido
    },
};
