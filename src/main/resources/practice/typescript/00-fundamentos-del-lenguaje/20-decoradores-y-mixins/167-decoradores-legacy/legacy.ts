// Tema: decoradores legacy.
// El decorador legacy recibe target, nombre y descriptor. Se activa experimentalDecorators; no se mezcla con la firma estándar.
// Desde la carpeta de este ejercicio:
// Compilar: tsc -p .
// Ejecutar: node legacy.js
// Salida esperada:
// decir
// hola

function log(_:object,nombre:string,descriptor:PropertyDescriptor):void { const original=descriptor.value; descriptor.value=function(...args:unknown[]) { console.log(nombre); return original.apply(this,args); }; }
class Saludo { @log decir():void { console.log("hola"); } }
new Saludo().decir();
