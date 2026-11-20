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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\abstract-factory\tema-consola\main.js
 * Nota: Familias.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   [ OK ] / Ventana clara < OK > / Ventana oscura
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
