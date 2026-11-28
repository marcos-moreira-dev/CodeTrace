// Tema:
// unknown acepta valores de cualquier tipo, pero exige una comprobación o reducción antes de
// utilizarlos de forma específica.

let value: unknown;

let value1: unknown = value; // Valid
let value2: any = value; // Valid
let value3: boolean = value; // Invalid
let value4: number = value; // Invalid
