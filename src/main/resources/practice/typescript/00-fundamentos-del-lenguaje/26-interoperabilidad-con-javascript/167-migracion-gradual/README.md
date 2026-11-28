# Migración gradual

Comienza con allowJs y checkJs en módulos JavaScript independientes. Agrega JSDoc, incorpora declaraciones de dependencias reales, renombra a .ts cuando el contrato esté claro y activa strict. Compila cada paso con `tsc -p .`; evita cambiar simultáneamente el comportamiento y el tipado.
