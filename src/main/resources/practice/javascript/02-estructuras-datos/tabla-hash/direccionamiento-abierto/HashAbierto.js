// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check HashAbierto.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

/**
 * Hash por sondeo lineal de capacidad fija.
 * No admite borrar: requeriría tombstones. Esperado O(1)
 * con carga baja, peor O(n) por colisiones consecutivas.
 */
class HashAbierto{
    constructor(n){
        if(!Number.isInteger(n)||n<=0)throw RangeError("Capacidad");
        this.a=Array(n);this.cantidad=0;
    }
    indice(clave){
        let h=0;
        for(let i=0;i<clave.length;i++)
            h=(Math.imul(h,31)+clave.charCodeAt(i))|0;
        return (h>>>0)%this.a.length;
    }
    poner(clave,valor){
        const ini=this.indice(clave);
        for(let paso=0;paso<this.a.length;paso++){
            const i=(ini+paso)%this.a.length;
            const previo=this.a[i];
            if(!previo){this.a[i]={clave,valor};this.cantidad++;return;}
            if(previo.clave===clave){this.a[i]={clave,valor};return;}
        }
        throw Error("Tabla llena");
    }
    buscar(clave){
        const ini=this.indice(clave);
        for(let paso=0;paso<this.a.length;paso++){
            const previo=this.a[(ini+paso)%this.a.length];
            if(!previo)return undefined;
            if(previo.clave===clave)return previo.valor;
        }
        return undefined;
    }
}
module.exports={HashAbierto};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\tabla-hash\direccionamiento-abierto\main.js
 * Nota: HashAbierto.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   Sondeo verificado
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
