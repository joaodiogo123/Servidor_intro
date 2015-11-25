package com.joaopires.servidor_intro;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SaxXmlClientListHandler extends MyXmlListHandler<Integer> {
    private String tempValue;

    public SaxXmlClientListHandler() {
        osElementos = new ArrayList<Integer>();
    }

    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {
        tempValue = "";
        if (qName.equalsIgnoreCase("customer"))
            ;
    }

    @Override
    public void characters(char[] ch, int start, int end) {
        tempValue = new String(ch, start, end);
    }

    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        if (qName.equalsIgnoreCase("customer"))
            osElementos.add(new Integer(tempValue));
    }
}