// Tema:
// Los atributos de importación comunican al entorno de ejecución cómo interpretar un módulo,
// por ejemplo como JSON.

const config = import('./config.json', { with: { type: 'json' } });
