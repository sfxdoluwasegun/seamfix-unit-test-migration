/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.tutorials.test.mockito;

/**
 *
 * @author uonuoha@seamfix.com
 */
public class CreateNumber {

    public int getThreeDigitNumber() {
        return (int) (Math.random() * 1000);
    }
}
