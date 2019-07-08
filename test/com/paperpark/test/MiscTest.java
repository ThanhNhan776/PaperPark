/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NhanTT
 */
public class MiscTest {
    public static void main(String[] args) {
        try {
            System.out.println("start");
            Thread.sleep(3000);
            System.out.println("finish");
        } catch (InterruptedException ex) {
            Logger.getLogger(MiscTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
