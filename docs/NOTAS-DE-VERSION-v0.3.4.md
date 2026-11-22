# CodeTrace v0.3.4 — coherencia de reglas y progreso

Actualización de mantenimiento para Windows, conservando MIT, los recursos
gráficos oficiales y la autoría de Marcos Moreira.

## Cambios incluidos

- **Progreso coherente:** el total evaluable descuenta correctamente caracteres
  no exigidos por «Practicar comentarios», «Omitir dependencias de código» y
  omisiones deliberadas (como Turbo). Un archivo terminado puede mostrar
  100 % sin que los caracteres saltados se contabilicen como incorrectos.
- El cuadro «Archivo terminado» muestra aciertos sobre caracteres **exigidos**,
  mientras informa por separado los caracteres omitidos deliberadamente.
- **Omitir dependencias de código:** ahora es un toggle visible en «Reglas de
  práctica», sincronizado con el menú «Práctica». Es reversible y conserva
  caracteres ya escritos. Se recuerda como preferencia.
- **Contenido evaluado:** se elimina el checkbox redundante «Todos». Se
  mantienen por separado «Código fuente» y «Markdown».
- **Autocrear archivos y carpetas:** al desactivar, se cancelan las creaciones
  en curso y se revierten los nodos vacíos creados por la autocreación **en esta
  ejecución**, sin borrar archivos escritos, nodos creados manualmente ni
  estructuras restauradas de sesiones anteriores. Se mantiene la posibilidad
  de volver a activar la reconstrucción.
- Las pruebas automatizadas cubren los denominadores, el 100 %, los toggles
  reversibles, la preservación de sesiones y la limpieza segura del árbol.

## Instalación y seguridad

Windows 11: EXE instalable y ZIP portable con Java incluido. Se publican
huellas SHA-256. El instalador carece de firma digital y Windows puede presentar
un aviso de SmartScreen. La consola integrada ejecuta comandos sobre archivos
reales del proyecto de referencia, no sobre el espacio virtual.

Código y documentación originales: MIT © 2026 Marcos Moreira. Las licencias de
dependencias externas están documentadas en THIRD-PARTY-NOTICES.md.
