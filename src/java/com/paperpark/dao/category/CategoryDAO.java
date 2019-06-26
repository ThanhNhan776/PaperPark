/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.dao.category;

import com.paperpark.dao.BaseDAO;
import com.paperpark.entity.Category;

/**
 *
 * @author NhanTT
 */
public class CategoryDAO extends BaseDAO<Category, String> {

    private CategoryDAO() {

    }
    
    private static CategoryDAO instance;
    private static final Object LOCK = new Object();
    
    public static CategoryDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new CategoryDAO();
            }
        }
        return instance;
    }
}
