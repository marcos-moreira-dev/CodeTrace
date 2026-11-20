; CodeTrace | Referencia: NASM x86-64 / Linux ELF64 (WSL)
; Comprobar: nasm -f elf64 ARCHIVO.asm -o ARCHIVO.o; ld ARCHIVO.o -o app
; Ejecutar: ./app

; Cliente de búsqueda lineal sobre enteros de 64 bits.
;
; Se recorren posiciones contiguas de memoria con índices explícitos.
; En el peor caso se comprueban n valores: O(n) tiempo y O(1) memoria.
; El resultado es el índice encontrado en RDI, o -1 si no aparece.
; Para simplificar la ejecución se usa como código de salida Linux
; cero si se encontró el elemento, uno si no.
global _start
section .data
    numeros dq 2, 5, 8, 12, 21
    longitud equ 5
    objetivo dq 12
section .text
_start:
    xor rcx, rcx
buscar:
    cmp rcx, longitud
    jae no_encontrado
    mov rax, [numeros + rcx * 8]
    cmp rax, [objetivo]
    je encontrado
    inc rcx
    jmp buscar
encontrado:
    mov eax, 60
    xor edi, edi
    syscall
no_encontrado:
    mov eax, 60
    mov edi, 1
    syscall
