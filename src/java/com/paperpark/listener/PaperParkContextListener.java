/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.listener;

import com.guitarpark.config.CrawlerConfig;
import com.paperpark.categories_mapping.CategoryMappings;
import com.paperpark.contants.ConfigConstants;
import com.paperpark.crawler.papercraftmuseum.MuseumThread;
import com.paperpark.crawler.kit168.Kit168Thread;
import com.paperpark.utils.DBUtils;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Web application lifecycle listener.
 *
 * @author NhanTT
 */
public class PaperParkContextListener implements ServletContextListener {

    private static final String CATEGORY_MAPPING_FILE
            = "WEB-INF\\configs\\category\\categories-mapping.xml";
   
    private static Kit168Thread kit168Thread;
    private static MuseumThread museumThread;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        loadCategoryMappings(sce);
        
        String realPath = sce.getServletContext().getRealPath("/");
        
        CrawlerConfig crawlerConfig = CrawlerConfig.getCrawlerConfig(realPath);
        if (!crawlerConfig.isEnableCrawler()) {
            System.out.println("INFO Crawler has been disabled.");
            return;
        }
        
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
    
    private void loadCategoryMappings(ServletContextEvent sce) {
        try {
            JAXBContext context = JAXBContext.newInstance(CategoryMappings.class);
            Unmarshaller un = context.createUnmarshaller();

            String realPath = sce.getServletContext().getRealPath("/");
            String filePath = realPath + CATEGORY_MAPPING_FILE;

            File file = new File(filePath);

            CategoryMappings mappings = (CategoryMappings) un.unmarshal(file);
            if (mappings != null) {
                sce.getServletContext().setAttribute("CATEGORY_MAPPINGS", mappings);
            }
        } catch (JAXBException ex) {
            Logger.getLogger(PaperParkContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
