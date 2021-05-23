/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpjoc;

import java.util.HashMap;
import jod.JOPObject;

/**
 *
 * @author Ishan
 */
public final class HttpResponse {

    String response;

    /**
     *
     * @param response which need to convert into JOPObject
     */
    public HttpResponse(String response) {
        this.response = response;
    }

    /**
     *
     * @return Created JOPObject
     */
    public JOPObject getJOPObject() {
        return new HttpResponse1(response).getJOPObject();
    }

    /**
     *
     * @return Created HashMap
     */
    public HashMap getHashMap() {
        return new HttpResponse0(response).getHashMap();
    }
}
