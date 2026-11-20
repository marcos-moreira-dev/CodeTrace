; CodeTrace | Referencia: NASM x86-64 / Linux ELF64 (WSL)
; Comprobar: nasm -f elf64 ARCHIVO.asm -o ARCHIVO.o; ld ARCHIVO.o -o app
; Ejecutar: ./app

; Cliente de un bucle y un contador en registros.
;
; La invariante mantiene RCX entre 0 y diez durante el recorrido.
; En la práctica del algoritmo se observa cómo CMP y JL controlan
; saltos según FLAGS. No hay salida de consola: el programa acaba
; mediante el syscall de salida Linux.
global _start
section .text
_start:
    xor rcx, rcx
ciclo:
    inc rcx
    cmp rcx, 10
    jl ciclo
    mov eax, 60
    xor edi, edi
    syscall

; CodeTrace | EJEMPLO DE CONSOLA (practica)
; 
; Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
;   wsl nasm -f elf64 03-algoritmos/contador-binario/contador_binario.asm -o /tmp/ct-ejemplo.o
;   wsl ld /tmp/ct-ejemplo.o -o /tmp/ct-ejemplo
;   wsl sh -c '/tmp/ct-ejemplo; printf "codigo-de-salida:%s\n" "$?"'
; Salida esperada:
;   codigo-de-salida:0
; CodeTrace | FIN EJEMPLO DE CONSOLA
