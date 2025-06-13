package ejercicios;

import java.util.Scanner;

// Determinar palindromo
public class Ejer1 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresa la palabra:");
        String palabra = scanner.next();
        determinarPalindromo(palabra);
    }

    public static void determinarPalindromo(String palabra){
        for (int i = 0; i < palabra.length()/2; i++){
            if (palabra.charAt(i) != palabra.charAt(palabra.length() - i - 1)){
                System.out.println("No es palindromo");
            }
        }
        System.out.println("Si es palindromo");
    }
}
