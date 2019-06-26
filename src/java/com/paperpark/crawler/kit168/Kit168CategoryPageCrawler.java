/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.crawler.kit168;

import com.paperpark.crawler.BaseCrawler;
import javax.servlet.ServletContext;

/**
 *
 * @author NhanTT
 */
public class Kit168CategoryPageCrawler extends BaseCrawler implements Runnable {

    private String pageUrl;
    private String categoryName;
    
    public Kit168CategoryPageCrawler(ServletContext context) {
        super(context);
    }

    @Override
    public void run() {
        
    }

}
