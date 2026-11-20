; CodeTrace | Referencia: NASM x86-64, contador hasta n (System V Linux)
; Compilar: nasm -f elf64 contador.asm -o contador.o
; rdi = n; devuelve 0 si n=0, o la suma de impares < n
bits 64
section .text
global sumar_impares

sumar_impares:
    xor rax, rax
    mov rcx, 1
.bucle:
    cmp rcx, rdi
    jge .fin
    add rax, rcx
    add rcx, 2
    jmp .bucle
.fin:
    ret

; CodeTrace | EJEMPLO DE CONSOLA (practica)
; 
; Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
;   wsl nasm -f elf64 03-algoritmos/contador/contador.asm -o /tmp/ct-ejemplo.o
; Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.
; Salida esperada:
;   (sin salida de consola; la comprobación termina correctamente)
; CodeTrace | FIN EJEMPLO DE CONSOLA
