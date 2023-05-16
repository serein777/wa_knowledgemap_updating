package edu.hhu.wa_knowledgemap_updating.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static edu.hhu.wa_knowledgemap_updating.common.Common.DATE_FORMAT;

public class DateFormatUtil {
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    public  static String formatDate(Date date){
        return  simpleDateFormat.format(date);
    }
    public  static String formatDate(Date date,String pattern){
        String res = new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
        return  res;
    }
}
