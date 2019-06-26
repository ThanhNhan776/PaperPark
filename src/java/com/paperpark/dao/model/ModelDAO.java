/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.dao.model;

import com.paperpark.dao.BaseDAO;
import com.paperpark.entity.Model;

/**
 *
 * @author NhanTT
 */
public class ModelDAO extends BaseDAO<Model, Integer> {
    
    private ModelDAO() {
        
    }
    
    private static ModelDAO instance;
    private static final Object LOCK = new Object();
    
    public static ModelDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new ModelDAO();
            }
        }
        return instance;
    }
}
