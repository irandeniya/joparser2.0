/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jop;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Ishan
 */
public final class Parser {

    private static Parser parser;
    private ReadObjects readObjects;
    private WriteObject writeObject;

    /**
     * Parser is library which help to communicate over multiple platforms using
     * java object as XML format. Parser will help to make well formatted XML
     * file for Java OBjects and as same as reverse. Parser XML can use to save
     * Java Objects as XML file and as same as Communication purposes. You can
     * pass Parser XML as same as SOAP Object over the Internet. Java SE, J2EE,
     * J2ME, Android as same as PHP.
     *
     * @return
     */
    public static Parser getParser() {
        if (parser == null) {
            parser = new Parser();
            parser.writeObject = new WriteObject();
            parser.readObjects = new ReadObjects();
        }
        return parser;
    }

    public void close() {
        parser = null;
    }

    /**
     *
     * @param text value which contains well formatted XML.
     * @return Object for given XML.
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     */
    public Object createObjectForXML(String text) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return writeObject.writeObject(text);
    }

    /**
     *
     * @param object which need to covert to XML.
     * @return XML.
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public String createXMLForObject(Object object) throws IllegalArgumentException, InvocationTargetException,
            IllegalAccessException {
        return readObjects.readObject(null, object);
    }

}
