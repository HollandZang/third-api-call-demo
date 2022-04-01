package com.holland.zdl.util;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class Parser {

    public static Document xml2Doc(String xml) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            log.error("Try parse 'xml' error when init 'DocumentBuilder'", e);
            return null;
        }

        ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        Document doc = null;
        try {
            doc = builder.parse(input);
        } catch (SAXException e) {
            log.error("Try parse 'xml' error when translate it", e);
        } catch (IOException e) {
            log.error("Try parse 'xml' error when assemble object", e);
        }

        return doc;
    }
}
