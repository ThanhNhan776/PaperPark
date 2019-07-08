/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.listener;

import com.paperpark.config.crawler.CrawlerConfig;
import com.paperpark.categories_mapping.CategoryMappings;
import com.paperpark.config.model.ModelEstimation;
import com.paperpark.contants.ConfigConstants;
import com.paperpark.crawler.papercraftmuseum.MuseumThread;
import com.paperpark.crawler.kit168.Kit168Thread;
import com.paperpark.utils.DBUtils;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author NhanTT
 */
public class PaperParkContextListener implements ServletContextListener {
    
    private static Kit168Thread kit168Thread;
    private static MuseumThread museumThread;
    private static String realPath;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        realPath = sce.getServletContext().getRealPath("/");
        
        CategoryMappings categoryMappings = getCategoryMappings(realPath);
        sce.getServletContext().setAttribute("CATEGORY_MAPPINGS", categoryMappings);
        
        CrawlerConfig crawlerConfig = CrawlerConfig.getCrawlerConfig(realPath);
        if (!crawlerConfig.isEnableCrawler()) {
            System.out.println("INFO Crawler has been disabled.");
            return;
        }
        
        ModelEstimation modelEstimation = getModelEstimationConfig(realPath);
        sce.getServletContext().setAttribute("MODEL_ESTIMATION", modelEstimation);
        
        final ServletContext context = sce.getServletContext();
        kit168Thread = new Kit168Thread(context);
        kit168Thread.start();
        if (ConfigConstants.DEBUG) {
            System.out.println("DEBUG Kit168 Thread start with Id = " + kit168Thread.getId());
        }
        
        museumThread = new MuseumThread(context);
        museumThread.start();
        if (ConfigConstants.DEBUG) {
            System.out.println("DEBUG Museum Thread start with Id = " + museumThread.getId());
        }
        
        context.setAttribute("KIT168_THREAD", kit168Thread);
        context.setAttribute("MUSEUM_THREAD", museumThread);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManager em = DBUtils.getEntityManager();
        if (em != null) {
            em.close();
        }
    }
    
    private CategoryMappings getCategoryMappings(String realPath) {
        return CategoryMappings.getCategoryMappings(realPath);
    }
    
    private ModelEstimation getModelEstimationConfig(String realPath) {
        return ModelEstimation.getModelEstimation(realPath);
    }

    public static String getRealPath() {
        return realPath;
    }
}
