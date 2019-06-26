/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.crawler.kit168;

import com.paperpark.crawler.BaseCrawler;
import com.paperpark.dao.category.CategoryDAO;
import com.paperpark.entity.Category;
import com.paperpark.utils.CategoryHelper;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author NhanTT
 */
public class Kit168CategoryPageCrawler extends BaseCrawler implements Runnable {

    private String pageUrl;
    private String categoryName;

    public Kit168CategoryPageCrawler(ServletContext context, String pageUrl, 
            String categoryName) {
        super(context);
        this.pageUrl = pageUrl;
        this.categoryName = categoryName;
    }

    @Override
    public void run() {
        CategoryDAO categoryDAO = CategoryDAO.getInstance();
        Category category = createCategory(categoryName);
        if (category == null) {
            Logger.getLogger(Kit168CategoryPageCrawler.class.getName())
                    .log(Level.SEVERE, null, new Exception("Error: category null"));
            return;
        }
        
    }

    private static final Object LOCK = new Object();
    
    protected Category createCategory(String name) {
        synchronized (LOCK) {
            Category category = null;
            String realName = getRealCategoryName(name);
            if (realName != null) {
                CategoryDAO dao = CategoryDAO.getInstance();
                category = dao.getFirstCategory(realName);
                if (category == null) {
                    category = new Category(CategoryHelper.generateUUID(), realName);
                    dao.create(category);
                }
            }
            return category;
        }
    }
    
    private String getRealCategoryName(String altName) {
        CategoryHelper helper = new CategoryHelper(getContext());
        return helper.getRealCategoryName(altName);
    }
}
