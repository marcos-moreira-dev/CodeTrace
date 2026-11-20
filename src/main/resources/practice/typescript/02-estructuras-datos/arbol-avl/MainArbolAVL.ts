// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainArbolAVL.js"

import { ArbolAVL } from "./ArbolAVL";
/**
 * Cliente AVL, prueba adversa de inserciones ya ordenadas.
 *
 * Un BST normal con 15 números ordenados alcanza altura 15;
 * un AVL la conserva logarítmica mediante rotaciones. Se valida
 * la propiedad local de balance y el recorrido ascendente.
 */
const avl=new ArbolAVL();
for(let i=1;i<=15;i++)avl.insertar(i);
if(avl.inorden().join(",")!==Array.from({length:15},(_,i)=>i+1).join(","))
    throw Error("Recorrido");
if(avl.validar()>5)throw Error("Altura");
console.log("Altura AVL:",avl.validar());

/*
 * Salida esperada de consola:
 *   Altura AVL:
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
