// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check main.js
// Ejecutar: node main.js (desde esta carpeta)

const sorts=require("./Ordenadores");
/**
 * Cliente: cinco ordenamientos sobre copias de una misma entrada.
 * Comprobar salida correcta no prueba Big-O. La estabilidad se
 * razona por tratamiento de iguales, y los costos por invariantes.
 */
function main(){
    const metodos=[
        ["Insercion",sorts.insercion],["Burbuja",sorts.burbuja],
        ["Mezcla",sorts.mezcla],["HeapSort",sorts.heap],
        ["QuickSort",sorts.rapido]
    ];
    for(const [nombre,fn] of metodos){
        const datos=[9,3,8,1,7,2,5];
        fn(datos);
        if(datos.join(",")!=="1,2,3,5,7,8,9")throw Error(nombre);
        console.log(nombre+": ["+datos.join(", ")+"]");
        const vacio=[],repetidos=[3,1,3,1];
        fn(vacio);fn(repetidos);
        if(vacio.length!==0||repetidos.join(",")!=="1,1,3,3")
            throw Error("Limites");
    }
}
if(require.main===module)main();
/*
 * Salida esperada:
 * Insercion: [1, 2, 3, 5, 7, 8, 9]
 * Burbuja: [1, 2, 3, 5, 7, 8, 9]
 * Mezcla: [1, 2, 3, 5, 7, 8, 9]
 * HeapSort: [1, 2, 3, 5, 7, 8, 9]
 * QuickSort: [1, 2, 3, 5, 7, 8, 9]
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\03-algoritmos\ordenamientos\comparativa-cinco\main.js
 * Salida esperada:
 *   Insercion: [1, 2, 3, 5, 7, 8, 9]
 *   Burbuja: [1, 2, 3, 5, 7, 8, 9]
 *   Mezcla: [1, 2, 3, 5, 7, 8, 9]
 *   HeapSort: [1, 2, 3, 5, 7, 8, 9]
 *   QuickSort: [1, 2, 3, 5, 7, 8, 9]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
