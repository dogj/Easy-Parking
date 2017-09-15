package com.jiangxin.packing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Created by aimee on 11/09/17.
 */
public class TimeTest {
    @Test
    public void getTime() throws Exception {
        try {
            SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            int input = 0;
            String  output[] = new String[1];
            //String expected[];
            String packing_start = df1.format(new Date());
            String packing_end;


            df1.setTimeZone(TimeZone.getTimeZone("GMT+12"));
            Date start = df1.parse(packing_start);
            long end_time;
            end_time = start.getTime() + 60 * 60 * 1000;
            Date end = new Date(end_time);
            packing_end = df1.format(end);

            output[0] = packing_start;
            output[1] = packing_end;


            Time testGetTime = new Time();
            String expected[] = testGetTime.getTime(input);
             assertArrayEquals(expected, output);
       }
       catch( Exception e ) {
            System.out.println("Expected Exception ");
       }

    }

}