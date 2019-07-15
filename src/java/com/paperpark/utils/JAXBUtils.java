/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.utils;

import com.paperpark.models.ResultModels;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author NhanTT
 */
public class JAXBUtils {
    public static String marshall(ResultModels models) {
        try {
            JAXBContext context = JAXBContext.newInstance(ResultModels.class);
            Marshaller marshaller = context.createMarshaller();
            
//            marshaller.setProperty(CharacterEscapeHandler.class.getName(), 
//                    (CharacterEscapeHandler) (char[] chars, int i, int i1, boolean bln, Writer writer) -> {
//                writer.write(chars, i, i1);
//            });
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");

            StringWriter sw = new StringWriter();
            marshaller.marshal(models, sw);
            
            return sw.toString().replace("&amp;#", "&#");
        } catch (JAXBException ex) {
            Logger.getLogger(JAXBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
