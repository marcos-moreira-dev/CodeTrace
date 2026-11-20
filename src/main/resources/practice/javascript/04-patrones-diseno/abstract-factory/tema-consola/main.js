// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check main.js
// Ejecutar: node main.js desde esta carpeta, sin librerías externas

const {FabricaClara,FabricaOscura}=require("./Familias");
/**
 * Cliente Abstract Factory: el tema visual se cambia como familia.
 *
 * Es distinto de Factory Method, que especializa la construcción de
 * UN producto. Los objetos JS son compatibles si tienen los métodos
 * requeridos, sin la interfaz nominal de Java o C#.
 */
function pantalla(f){return f.boton().pintar()+" / "+f.dialogo().pintar();}
function main(){
    const a=pantalla(new FabricaClara()),b=pantalla(new FabricaOscura());
    if(a===b||!a.includes("clara")||!b.includes("oscura"))
        throw Error("Abstract Factory");
    console.log(a,b);
}
if(require.main===module)main();

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
