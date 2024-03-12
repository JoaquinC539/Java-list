package com.project.utils;

public class PasswordValidator {
    public static boolean isValidPassword(String password) {

        // Verifica la longitud
        // if (password.length() < 8) {
        //     return false;
        // }

        // Verifica la complejidad
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=]).*$")) {
            return false;
        }

        return true;
    }
}
