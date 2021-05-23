/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpjoc;

import java.net.URLDecoder;
import java.util.HashMap;

/**
 *
 * @author Ishan
 */
final class HttpResponse0 {

    private HashMap jopa;
    /**
     *
     * @param response which need to convert into JOPObject
     */
    HttpResponse0(String response) {
        jopa = get(response);
    }

    /**
     *
     * @return Created HashMap
     */
    HashMap getHashMap() {
        return jopa;
    }
    
    /**
     *
     * @param response Parser generated HttpRequest.
     * @return HashMap created from response.
     */
    private HashMap get(String response) {
        String params[] = response.split("&");
        HashMap map = new HashMap();

        for (String para : params) {
            int sta = para.indexOf("[");
            int cl = para.indexOf("]");
            int lst = para.lastIndexOf("]");
            int eq = para.indexOf("=");
            String name = para.substring(0, eq);
            if (cl == lst) {
                map.put(name.substring(sta + 1, cl), URLDecoder.decode(para.substring(eq + 1)));
            } else if (sta != -1 && cl != -1) {
                readSubPart(name.substring(sta), para.substring(eq + 1), map);
            }
        }
        return map;
    }

    private void readSubPart(String params, String value, HashMap map) {
        int sta = params.indexOf("[");
        int cl = params.indexOf("]");
        int lst = params.lastIndexOf("]");
        String first = params.substring(sta + 1, cl);
        if (cl == lst) {
            map.put(first, URLDecoder.decode(value));
        } else {
            HashMap map2 = (HashMap) map.get(first);
            if (map2 == null) {
                map2 = new HashMap();
                map.put(first, map2);
            }
            readSubPart(params.substring(cl + 1, lst + 1), value, map2);
        }
    }
}
