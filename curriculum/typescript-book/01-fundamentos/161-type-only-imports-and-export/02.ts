// Tema:
// import type y export type expresan dependencias exclusivamente de tipos que se eliminan de
// la salida JavaScript.

import type T from './mod';
import type { A, B } from './mod';
import type * as Types from './mod';
export type { T };
export type { T } from './mod';
