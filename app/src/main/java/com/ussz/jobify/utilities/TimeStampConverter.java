package com.ussz.jobify.utilities;

import com.google.firebase.Timestamp;

import java.util.Date;

public class TimeStampConverter {

    public static String toReadableDate(Timestamp timestamp){
        String date = "";
        Date temp = timestamp.toDate();
        date += temp.toString();
        return date;
    }
    public static String timeLeft(Date timestamp){
        Date now = new Date();
        if(timestamp.compareTo(now) == 1){
            return String.valueOf(timestamp.getDate() - now.getDate());

        }
        return "0";

    }
}
