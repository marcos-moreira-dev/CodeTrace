; CodeTrace | Referencia: NASM x86-64, ABI System V (Linux)
; Compilar: nasm -f elf64 arreglo.asm -o arreglo.o
; rdi = puntero de qwords; rsi = longitud; salida rax = suma
bits 64
section .text
global sumar_arreglo

sumar_arreglo:
    xor rax, rax
    xor rcx, rcx
.bucle:
    cmp rcx, rsi
    jge .fin
    add rax, [rdi + rcx * 8]
    inc rcx
    jmp .bucle
.fin:
    ret

; CodeTrace | EJEMPLO DE CONSOLA (practica)
; 
; Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
;   wsl nasm -f elf64 02-estructuras-datos/arreglo/arreglo.asm -o /tmp/ct-ejemplo.o
; Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.
; Salida esperada:
;   (sin salida de consola; la comprobación termina correctamente)
; CodeTrace | FIN EJEMPLO DE CONSOLA
