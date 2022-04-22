package com.holland.demo.util;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Parser {

    public static Document xml2Doc(final String xml) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.err.println("Try parse 'xml' error when init 'DocumentBuilder'");
            e.printStackTrace();
            return null;
        }

        final ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        final Document doc;
        try {
            doc = builder.parse(input);
        } catch (SAXException e) {
            System.err.println("Try parse 'xml' error when translate it");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.err.println("Try parse 'xml' error when assemble object");
            e.printStackTrace();
            return null;
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return doc;
    }
}
