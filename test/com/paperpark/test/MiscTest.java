/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.test;

import com.paperpark.categories_mapping.CategoryMapping;
import com.paperpark.categories_mapping.CategoryMappings;

/**
 *
 * @author NhanTT
 */
public class MiscTest {
    
    private static final String realPath = "E:\\Java\\Summer_2019\\PaperPark\\web\\";

    public static void main(String[] args) {
        CategoryMappings mappings = CategoryMappings.getCategoryMappings(realPath);
        mappings.getCategoryMapping().forEach((mapping) -> {
            System.out.println(mapping.getName());
        });
    }
}
