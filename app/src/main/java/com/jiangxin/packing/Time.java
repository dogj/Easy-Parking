package com.jiangxin.packing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/8/30.
 */

public class Time {

    public  String[] getTime(int i){
        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        df1.setTimeZone(TimeZone.getTimeZone("GMT+12"));
        String packing_start = df1.format(new Date());
        String packing_end = null;
        String[] packing_time = new String[2];
        if(i==0) {


            try {
                Date start = df1.parse(packing_start);
                long end_time;
                end_time = start.getTime() + 60 * 60 * 1000;
                Date end = new Date(end_time);
                packing_end = df1.format(end);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {

            try {
                Date start = df1.parse(packing_start);
                long end_time;
                end_time = start.getTime() + 2*60 * 60 * 1000;
                Date end = new Date(end_time);
                packing_end = df1.format(end);

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        packing_time[0]=packing_start;
        packing_time[1]=packing_end;
        return packing_time;
    }


}
