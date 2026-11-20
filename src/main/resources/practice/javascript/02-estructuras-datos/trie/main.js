// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check main.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

const {Trie}=require("./Trie");
/**
 * Cliente Trie: prefijo y palabra completa son estados diferentes.
 *
 * for...of sobre un String JS itera puntos de código Unicode en
 * vez de simples índices UTF-16, un comportamiento idiomático
 * distinto del Char de Java o Kotlin.
 */
function main(){
    const t=new Trie();
    t.insertar("casa");t.insertar("casco");
    if(!t.prefijo("cas")||t.contiene("cas")||
       !t.contiene("casa")||t.contiene("cama"))throw Error("Trie");
    console.log("Trie OK");
}
if(require.main===module)main();

/*
 * Salida esperada de consola:
 *   Trie OK
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
