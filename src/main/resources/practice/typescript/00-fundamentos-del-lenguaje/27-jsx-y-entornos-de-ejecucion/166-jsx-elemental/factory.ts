// Tema: Fábrica JSX propia; retorna una representación textual.
// Compilar: tsc -p .
// Ejecutar: node jsx.js
export function h(tag:string,props:unknown,...children:unknown[]):string{return `<${tag}>${children.join("")}</${tag}>`;}
declare global { namespace JSX { interface IntrinsicElements { saludo:{ children?:unknown } } type Element=string; } }
