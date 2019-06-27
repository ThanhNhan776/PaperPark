/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.crawler.papercraftmuseum;

import com.paperpark.contants.URLConstants;
import com.paperpark.crawler.BaseCrawler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author NhanTT
 */
public class MuseumCategoryCrawler extends BaseCrawler {
    
    private final String URL = URLConstants.PAPERCRAFTMUSEUM;
    
    public MuseumCategoryCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, String> getCategories(String url) {
        try (BufferedReader reader = getBufferedReaderForUrl(url)) {
            String line = "";
            String document = "<categoryDocument>";
            boolean isStart = false;
            
            while ((line = reader.readLine()) != null) {
                if (isStart && line.contains("<div class=\"lrgboxbtm\"></div>")) {
                    break;
                }
                if (line.contains("<div class=\"post\">")) {
                    isStart = true;
                }
                if (isStart) {
                    document += line.trim();
                }
            }
            document += "</categoryDocument>";
            
            return stAXParserForCategories(document);
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(MuseumCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Map<String, String> stAXParserForCategories(String document) 
            throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<String, String> categories = new HashMap<>();
        
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (isCategoryElement(startElement)) {
                    event = (XMLEvent) eventReader.next();
                    String categoryName = event.asCharacters().getData().trim();
                    
                    eventReader.next();
                    eventReader.next();
                    event = (XMLEvent) eventReader.next();
                    startElement = event.asStartElement();
                    
                    String categoryLink = URL + getHref(startElement);
                    
                    categories.put(categoryLink, categoryName);
                }
            }
        }
        
        return categories;
    }
    
    private boolean isCategoryElement(StartElement element) {
        String tagName = element.getName().getLocalPart();
        if (!"div".equals(tagName)) {
            return false;
        }
        
        Attribute classAttr = element.getAttributeByName(new QName("class"));
        if (classAttr == null) {
            return false;
        }
        
        String className = classAttr.getValue();
        
        return "parentcat".equals(className);
    }
    
    private String getHref(StartElement element) {
        Attribute href = element.getAttributeByName(new QName("href"));
        return href == null ? null : href.getValue();
    }
}
