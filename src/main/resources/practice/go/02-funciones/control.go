package main

import "fmt"

func sumaPares(limite int) int {
    total := 0
    for i := 0; i <= limite; i++ {
        if i%2 == 0 { total += i }
    }
    return total
}

func main() {
    fmt.Println(sumaPares(10))
}
