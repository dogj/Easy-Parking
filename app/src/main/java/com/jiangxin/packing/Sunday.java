package com.jiangxin.packing;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/13.
 */

public class Sunday {

    public static boolean isSun(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        if(dayOfTheWeek.contains("日")||dayOfTheWeek.contains("Sun")){
            return true;
        }else {
            return false;
        }
    }
}
