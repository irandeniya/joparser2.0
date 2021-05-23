/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpjoc;

import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import jop.Parser;

/**
 *
 * @author Ishan
 */
public final class Connection {

    private static Connection connection;

    private Connection() {
    }

    /**
     * Create a HTTPConnection to Web.
     *
     * @return Connection which contains multiple methods for easily communicate
     * over the internet.
     */
    public static Connection openConnection() {
        if (connection == null) {
            connection = new Connection();
        }
        return connection;
    }

    public void close() {
        connection = null;
    }

    /**
     *
     * @param url Targeted path to resource on the web or network.
     * @param data data to send. If data is an non of String type it will
     * convert to Parser XML format automatically. Otherwise if data is an
     * HttpRequest type Parser will send as PHP formated parameter.
     * @param method HTTP method type
     * @param returnType
     * @return
     * @throws java.lang.Exception
     */
    public Object execute(String url, Object data, Http method, Http returnType) throws Exception {

        String para = null;
        if (data instanceof String) {
            para = (String) data;
        } else if (data instanceof HttpRequest) {
            para = ((HttpRequest) data).getRequest();
        } else if (data != null) {
            para = Parser.getParser().createXMLForObject(data);
        }

        if (method != Http.POST && para != null) {
            url += "?" + para;
        }
        URL urlC = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlC.openConnection();
        con.setDoInput(true);
        if (method == Http.POST) {
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            if (para != null) {
                OutputStream out = con.getOutputStream();
                out.write(para.getBytes());
                out.flush();
                out.close();
            }
        }
        InputStream ins = con.getInputStream();
        int read = -1;
        StringBuilder builder = new StringBuilder();
        while ((read = ins.read()) != -1) {
            builder.append((char) read);
        }
        ins.close();
        if (returnType == Http.Text) {
            return builder.toString();
        } else if (returnType == Http.Object) {
            return Parser.getParser().createObjectForXML(builder.toString());
        } else if (returnType == Http.HashMap) {
            return new HttpResponse0(builder.toString()).getHashMap();
        } else if (returnType == Http.JOPObject) {
            return new HttpResponse1(builder.toString()).getJOPObject();
        }
        throw new InvalidClassException("Invalid return type submited. " + returnType);
    }

}
