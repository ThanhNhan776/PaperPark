/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.listener;

import com.paperpark.categories_mapping.CategoryMappings;
import com.paperpark.dao.utils.DBUtils;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
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
public class GuitarParkContextListener implements ServletContextListener {

    private static final String CATEGORY_MAPPING_FILE
            = "WEB-INF\\configs\\category\\categories-mapping.xml";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        loadCategoryMappings(sce);
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
                sce.getServletContext().setAttribute("CATEGORY_MAPPINGS", mappings.getCategoryMapping());
            }
        } catch (JAXBException ex) {
            Logger.getLogger(GuitarParkContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
