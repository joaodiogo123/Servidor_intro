package com.joaopires.servidor_intro;
//Como queremos apanhar listas de coisas vamos criar uma função que permita apanhar as listas e coisas

import java.util.List;

import org.xml.sax.helpers.DefaultHandler;

public class MyXmlListHandler<E> extends DefaultHandler { //Escolhemos os E que queremos

    protected List<E> osElementos;

    public List<E> obterElementos() {
        return osElementos;
    }
}