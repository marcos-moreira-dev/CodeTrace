// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainListaCircular.js"

import { ListaCircular } from "./ListaCircular";
/**
 * Cliente lista circular: rotación de turnos sin fin de cadena.
 *
 * En una lista circular, buscar un null para detenerse provoca
 * un bucle infinito. El recorrido limita el número de visitas.
 * Girar el último puntero cuesta O(1), recorrer O(n).
 */
const turnos=new ListaCircular<string>();
if(turnos.valores().length)throw Error("Vacía");
["Ana","Luis","Mia"].forEach(x=>turnos.agregar(x));
turnos.rotar();
if(turnos.valores().join(",")!=="Luis,Mia,Ana")throw Error("Circular");
console.log(turnos.valores());

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
