# Estado de verificación

334 rutas implementadas en 27 categorías; 296 casos: 162 de comprobación estática, 90 ejecutables, 39 negativos y 5 de plataforma. 38 archivos de apoyo y documentación.

- Primera entrega de 33 casos: verificación en GitHub Actions con TypeScript 7.0.2, Linux y Windows, ejecución 46149257895, commit 54905362765d72a17523a18c4685e0929ccb3856.
- Árbol completo: revisión local con TypeScript 5.8.3 mediante su API de compilación, con skipLibCheck, y Node.js 22.16.0: 295/296. Los casos de plataforma se comprueban estáticamente, no en un navegador real.
- import defer requiere TypeScript 7; el compilador local no reconoce esa sintaxis. Está pendiente de verificación con la versión objetivo.
- La publicación en el catálogo de CodeTracer queda pendiente de las pruebas completas con TypeScript 7.0.2 y de la revisión de integración.

Los comandos de cada ejercicio son directos, desde la carpeta correspondiente. Los scripts de verificación solo son herramientas internas.
