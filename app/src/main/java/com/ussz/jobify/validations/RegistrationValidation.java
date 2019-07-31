package com.ussz.jobify.validations;

import com.ussz.jobify.data.Graduate;

public class RegistrationValidation {

    public static String validateStep1(Graduate graduate,String password){
        String username =  graduate.getName();
        String email = graduate.getEmail();
        String phoneNumber = graduate.getPhoneNumber();
        if (!LoginValidations.validateEmailAndPassword(email,password).equals("correct")){
            return LoginValidations.validateEmailAndPassword(username,password);
        }
        else if(username.equals("") || phoneNumber.equals("")){
            return "All fields are required";
        }
        return "correct";
    }

    public static String validateStep2(String department,String classOf,String university){
        String result = "correct";
        if(department.equals("") || classOf.equals("") || university.equals("")){
             result = "All fields are required";
        }
        return result;
    }

    public static int classOfToInt(String classOf){
        int result = 0;
        try {
            result = Integer.parseInt(classOf);
        }
        catch (NumberFormatException ex){

        }
        return result;
    }

    public static String Sanitize(String input){
        return input.trim();
    }
}
