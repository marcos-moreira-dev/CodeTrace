// Tema:
// Las comparaciones de igualdad permiten reducir las alternativas compatibles de una unión.

const checkStatus = (status: 'success' | 'error') => {
    switch (status) {
        case 'success':
            return true;
        case 'error':
            return null;
    }
};
