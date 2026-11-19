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
