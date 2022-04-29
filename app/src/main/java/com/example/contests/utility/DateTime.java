package com.example.contests.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateTime {
    public String getCodechefDate(String str) {
        String ans = str;
        try {
            Date date =
                    new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(str.substring(0,
                            str.length()-4));
            ans = new SimpleDateFormat("hh:mm - dd MMM, yyyy", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public String getReadableDate(String str) {
        LocalDateTime time = LocalDateTime.parse(str.substring(0,str.length()-2));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm - dd MMM, yyyy");
        return time.format(formatter);
    }
}
