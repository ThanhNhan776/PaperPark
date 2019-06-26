/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.dao.tag;

import com.paperpark.dao.BaseDAO;
import com.paperpark.entity.Tag;

/**
 *
 * @author NhanTT
 */
public class TagDAO extends BaseDAO<Tag, String> {

    private TagDAO() {
        
    }
    
    private static TagDAO instance;
    private static final Object LOCK = new Object();
    
    public static TagDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new TagDAO();
            }
        }
        return instance;
    }
}
