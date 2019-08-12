package com.ussz.jobify.utilities;

import java.util.Random;

public class Helper {

    private static String[] colors = new String[]{"#C10000FF","#800000","#228B22","#9932CC","#696969"};


    public static String getRandomColorString(char letter){
        return colors[((int)letter)%colors.length];
    }

}
