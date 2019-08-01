package com.ussz.jobify.validations;

public class EditValidation {

    public static boolean simpleEditDataValidation(String previousData,String currentData){
        if(currentData.equals("")){
            return false;
        }
        else if(previousData.equals(currentData)){
            return false;
        }
        return true;
    }
}
