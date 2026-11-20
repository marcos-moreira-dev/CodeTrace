; CodeTrace | Referencia: NASM x86-64, convenciones System V (Linux)
; Compilar: nasm -f elf64 pila.asm -o pila.o
; Demuestra el marco de pila y registros preservados
bits 64
section .text
global multiplicar_y_sumar

; rdi = a, rsi = b, rdx = c; devuelve a*b+c
multiplicar_y_sumar:
    push rbp
    mov rbp, rsp
    mov rax, rdi
    imul rax, rsi
    add rax, rdx
    pop rbp
    ret

; CodeTrace | EJEMPLO DE CONSOLA (practica)
; 
; Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
;   wsl nasm -f elf64 01-fundamentos/subrutinas/pila.asm -o /tmp/ct-ejemplo.o
; Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.
; Salida esperada:
;   (sin salida de consola; la comprobación termina correctamente)
; CodeTrace | FIN EJEMPLO DE CONSOLA
