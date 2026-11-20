// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check Familias.js
// Ejecutar: node main.js desde esta carpeta, sin librerías externas

/** Abstract Factory: fábrica genera DOS productos relacionados. */
class Texto{
    constructor(valor){this.valor=valor;}
    pintar(){return this.valor;}
}
class FabricaClara{
    boton(){return new Texto("[ OK ]");}
    dialogo(){return new Texto("Ventana clara");}
}
class FabricaOscura{
    boton(){return new Texto("< OK >");}
    dialogo(){return new Texto("Ventana oscura");}
}
module.exports={FabricaClara,FabricaOscura};
