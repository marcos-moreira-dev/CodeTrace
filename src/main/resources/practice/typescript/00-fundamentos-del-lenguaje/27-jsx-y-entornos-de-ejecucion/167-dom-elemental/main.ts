// Tema: dom elemental.
// DOM es una API del navegador, no de TypeScript. Se comprueba el elemento antes de usarlo; abrir el HTML y pulsar el botón.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 main.ts
// Para emitir: tsc --strict --target ES2022 main.ts
// Abrir index.html y pulsar el botón; mostrará Listo.
// Salida esperada: comprobación de tipos; ejecutar solo en el entorno indicado, no en Node por defecto.

const boton=document.querySelector<HTMLButtonElement>("#boton");
if(boton){boton.addEventListener("click",()=>{boton.textContent="Listo";});}
