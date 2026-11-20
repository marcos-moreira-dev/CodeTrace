// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check Elementos.js
// Ejecutar: node main.js desde esta carpeta, sin librerías externas

/** Composite: objetos hoja y grupo comparten bytes(). */
class Archivo{
    constructor(nombre,tamanio){
        if(tamanio<0)throw Error("Tamaño");
        this.nombre=nombre;this.tamanio=tamanio;
    }
    bytes(){return this.tamanio;}
}
class Carpeta{
    constructor(nombre){this.nombre=nombre;this.hijos=[];}
    agregar(elemento){
        if(elemento===this||typeof elemento?.bytes!=="function")
            throw Error("Contrato/ciclo directo");
        this.hijos.push(elemento);
    }
    bytes(){return this.hijos.reduce((total,x)=>total+x.bytes(),0);}
}
module.exports={Archivo,Carpeta};
