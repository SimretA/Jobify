package com.ussz.jobify.utilities;

import java.util.Random;

public class Helper {

    private static Random random = new Random();
    private static String[] colors = new String[]{"#343434","#E64A19","#9FE554","#558B2F","#0D5D98"};


    public static String getRandomColorString(){
        return colors[random.nextInt(colors.length)];
    }

}
