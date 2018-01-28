/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.tutorials.test.mockito;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author uonuoha@seamfix.com
 */
public class CreateNumberTest {

    private CreateNumber createNumber = null;

    @BeforeClass
    public void init() {
        createNumber = Mockito.mock(CreateNumber.class);
    }

    @Test
    public void testWith_TestNG() {

        int expected = 100;
        Mockito.when(createNumber.getThreeDigitNumber()).thenReturn(expected);

        int actual = createNumber.getThreeDigitNumber();
        Assert.assertEquals(actual, expected);
    }
}
