package com.jiangxin.packing;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by aimee on 14/09/17.
 */
public class SundayTest {
    @Test
    public void isSun() throws Exception {

        Boolean result = false;
        Boolean expect = Sunday.isSun();

        assertEquals(result,expect);

    }

}