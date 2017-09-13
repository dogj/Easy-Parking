package com.jiangxin.packing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2017/9/13.
 */
public class SaturdayTest {
    @Test
    public void isSat() throws Exception {
            boolean expected = false; // or maybe add a method to verify if it is saturday?
            boolean actual = Saturday.isSat();
            assertEquals(expected, actual);
    }

}