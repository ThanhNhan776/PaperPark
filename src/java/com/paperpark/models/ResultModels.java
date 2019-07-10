/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.models;

import com.paperpark.entity.Model;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author NhanTT
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultModels", propOrder = {
    "resultModel"
})
@XmlRootElement(name = "result-models")
public class ResultModels {
    
    @XmlElement(name = "model")
    private List<Model> resultModel;

    /**
     * @return the resultModel
     */
    public List<Model> getResultModel() {
        if (resultModel == null) {
            resultModel = new ArrayList<>();
        }
        return resultModel;
    }

    /**
     * @param resultModel the resultModel to set
     */
    public void setResultModel(List<Model> resultModel) {
        this.resultModel = resultModel;
    }
}
