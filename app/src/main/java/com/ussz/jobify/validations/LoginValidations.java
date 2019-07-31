package com.ussz.jobify.validations;

public class LoginValidations {

    public static String validateEmailAndPassword(String email, String password){
        String result = "correct";
        if(email.equals("") || password.equals("")){
            result = "All fields are required";
        }
        else if(password.length()<6){
            result = "Weak Password";
        }
        return result;
    }
}
