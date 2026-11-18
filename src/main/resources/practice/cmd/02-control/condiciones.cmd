@echo off
setlocal
set /a numero=8
if %numero% GTR 5 (
    echo Mayor que cinco
) else (
    echo Cinco o menos
)
endlocal
