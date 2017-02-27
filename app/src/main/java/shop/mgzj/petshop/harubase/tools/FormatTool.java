package shop.mgzj.petshop.harubase.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 星野悠 on 2017/1/19.
 */

public class FormatTool {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    public static String formatDate(Date date){
        return formatDate("yyyy-MM-dd", date) ;
    }

    public static String formatDate(String format, Date date){
        if(date == null){
            return "" ;
        }else {
            simpleDateFormat.applyPattern(format);
            return simpleDateFormat.format(date) ;
        }
    }

    public static Date stringToDate(String source){
        return stringToDate("yyyy-MM-dd", source) ;
    }
    public static Date stringToDate(String format, String source){
        simpleDateFormat.applyPattern(format);
        try {
            return simpleDateFormat.parse(source) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String formatObject(Object object){
        if(object == null){
            return "" ;
        }else{
            return object.toString() ;
        }
    }

    public static String formatFloat(Float f){
        return formatFloat("%.2f", f) ;
    }

    public static String formatFloat(String format, Float f){
        if(f == null){
            return "" ;
        }
        return String.format(format, f) ;
    }

    public static String formatDouble(Double d) {
        return formatDouble("%.2f", d) ;
    }

    public static String formatDouble(String format, Double d){
        if(d == null){
            return "" ;
        }else{
            return String.format(format, d) ;
        }
    }
}
