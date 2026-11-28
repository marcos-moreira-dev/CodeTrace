// Tema: modificadores de acceso.
// private y protected controlan el acceso en el compilador; #secreto también se protege durante la ejecución.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 acceso.ts
// Diagnóstico esperado: TS2341; no ejecutar.

class Cuenta { public nombre="Ana"; protected saldo=0; private codigo=1; #secreto=2; leer():number { return this.#secreto; } }
class Ahorro extends Cuenta { consultar():number { return this.saldo; } }
const c=new Ahorro();
c.codigo;
