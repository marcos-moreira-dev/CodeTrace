// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainListaDobleCircular.js"

import { ListaDobleCircular } from "./ListaDobleCircular";
/**
 * Cliente Linked List doble circular: combina navegación bidireccional
 * con un anillo. No es un BST, ni un deque concurrido.
 *
 * Los recorridos se limitan a la cantidad de nodos para evitar
 * bucles infinitos, pues ningún extremo apunta a null.
 */
const l=new ListaDobleCircular<number>();
[2,3,5].forEach(n=>l.agregar(n));
if(l.valores().join(",")!=="2,3,5"||l.valores(true).join(",")!=="5,3,2")
    throw Error("Enlaces");
console.log(l.valores());

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
