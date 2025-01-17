/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.crawler.kit168;

import com.paperpark.contants.ConfigConstants;
import com.paperpark.crawler.BaseCrawler;
import com.paperpark.crawler.BaseThread;
import com.paperpark.dao.model.ModelDAO;
import com.paperpark.entity.Category;
import com.paperpark.entity.Model;
import com.paperpark.utils.ElementChecker;
import com.paperpark.utils.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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
public class Kit168ModelListCrawler extends BaseCrawler implements Runnable {

    private String pageUrl;
    private Category category;

    public Kit168ModelListCrawler(ServletContext context, String pageUrl,
            Category category) {
        super(context);
        this.pageUrl = pageUrl;
        this.category = category;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForUrl(pageUrl);
            String document = getModelListDocument(reader);

            document = TextUtils.refineHtml(document);

            if (ConfigConstants.DEBUG && ConfigConstants.DEBUG_PRINT_DOC) {
                System.out.println("DEBUG ModelList document: " + document);
            }

            List<String> modelLinks = getModelLinks(document);

            for (String modelLink : modelLinks) {
                Kit168ModelCrawler modelCrawler
                        = new Kit168ModelCrawler(getContext(), modelLink, category);

                Model model = modelCrawler.getModel();
                if (model == null) {
                    continue;
                }
                ModelDAO.getInstance().saveModelWhileCrawling(getContext(), model);

                if (ConfigConstants.DEBUG) {
                    System.out.println("DEBUG saved model " + model.getLink());
                }
                
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            }
        } catch (IOException | XMLStreamException | InterruptedException ex) {
            Logger.getLogger(Kit168ModelListCrawler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private String getModelListDocument(BufferedReader reader) throws IOException {
        String line = "";
        String document = "<models>";
        boolean isStart = false;
        while ((line = reader.readLine()) != null) {
            if (isStart && line.contains("<nav class=\"page-navigation\">")) {
                break;
            }
            if (isStart) {
                document += line.trim();
            }
            if (!isStart && line.contains("<div id=\"main\"")) {
                isStart = true;
            }
        }
        document += "</models>";
        return document;
    }

    private List<String> getModelLinks(String document)
            throws UnsupportedEncodingException, XMLStreamException {
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        XMLEvent event = null;
        List<String> links = new ArrayList<>();
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "a", "class", "title")) {
                    String link = getHref(startElement);
                    links.add(link);
                }
            }
        }
        return links;
    }

    private String getHref(StartElement element) {
        Attribute href = element.getAttributeByName(new QName("href"));
        return href == null ? "" : href.getValue();
    }
}
