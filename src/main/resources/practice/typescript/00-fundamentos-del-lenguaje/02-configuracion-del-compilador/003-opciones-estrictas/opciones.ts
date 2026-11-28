// Tema: Opciones estrictas adicionales
// Una propiedad opcional no admite undefined explícito sin declararlo; un índice de arreglo puede no existir.
// Desde la carpeta de este ejercicio:
// Compilar: tsc -p .
// Diagnóstico esperado:
// El compilador debe rechazar el ejemplo (TS2375, TS2322). No ejecutar.

type Opciones = { nombre?: string };
const invalida: Opciones = { nombre: undefined };
const numeros: number[] = [1];
const seguro: number = numeros[2];
