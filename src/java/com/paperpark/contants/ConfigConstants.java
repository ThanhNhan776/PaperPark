/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.contants;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author NhanTT
 */
public class ConfigConstants {
    private static final int CRAWLING_DAY_INTERVAL = 1; 
    
    /**
     * time interval between re-crawl refresh data
     */
    public static final long CRAWLING_INTERVAL = TimeUnit.DAYS.toMillis(CRAWLING_DAY_INTERVAL);
    
    public static final boolean DEBUG = true;
    public static final boolean DEBUG_PRINT_DOC = false;
    
    /**
     * reduce 1/CRAWL_THREAD_REDUCE number of crawling threads
     */
    public static final int CRAWL_THREAD_REDUCE = 2;
}
