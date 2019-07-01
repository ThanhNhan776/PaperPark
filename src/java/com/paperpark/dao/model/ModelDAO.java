/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.dao.model;

import com.paperpark.dao.BaseDAO;
import com.paperpark.entity.Model;
import com.paperpark.utils.DBUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
    
    public Model getModelByLink(String link) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            
            List<Model> models = em.createNamedQuery("Model.findByLink")
                    .setParameter("link", link)
                    .getResultList();
            
            transaction.commit();
            
            if (models != null && !models.isEmpty()) {
                return models.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(ModelDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
    
    public synchronized Model saveModelWhileCrawling(Model model) {
        Model existedModel = getModelByLink(model.getLink());
        if (existedModel == null) {
            return create(model);
        } 
        return update(model);
    }
}
