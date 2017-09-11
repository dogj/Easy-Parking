package com.jiangxin.packing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Created by aimee on 11/09/17.
 */
public class TimeTest {
    @Test
    public void getTime() throws Exception {
        float input = 0;
        String output[] ;
        String expected[];
        float delta = 0;

        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        df1.setTimeZone(TimeZone.getTimeZone("GMT+12"));
        String output[0] = input.g


        Time testGetTime = new Time();
        expected = testGetTime.getTime(input);
        assertEquals(expected, output, delta);

    }

    @Before


}