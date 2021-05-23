/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpjoc;

import java.net.URLDecoder;
import jod.JOPObject;

/**
 *
 * @author Ishan
 */
final class HttpResponse1 {

    private JOPObject jopa;

    /**
     *
     * @param response which need to convert into JOPObject
     */
    HttpResponse1(String response) {
        jopa = get(response);
    }

    /**
     *
     * @return Created JOPObject
     */
    JOPObject getJOPObject() {
        return jopa;
    }

    private JOPObject get(String response) {
//        System.out.println(response);
        String params[] = response.split("&");
        JOPObject jop = new JOPObject();

        for (String para : params) {
//            System.out.println(para);
            int sta = para.indexOf("[");
            int cl = para.indexOf("]");
            int lst = para.lastIndexOf("]");
            int eq = para.indexOf("=");
            String name = para.substring(0, eq);
            jop.setName(para.substring(0, (sta < eq && sta != -1 ? sta : eq)));
            if (cl == lst) {
//                System.out.println(name.substring(sta + 1, (cl != -1 ? cl : eq)));
                JOPObject jop1 = new JOPObject(name.substring(sta + 1, (cl != -1 ? cl : eq)), URLDecoder.decode(para.substring(eq + 1)));
                jop.put(name.substring(sta + 1, (cl != -1 ? cl : eq)), jop1);
            } else if (sta != -1 && cl != -1) {
                readSubPart(name.substring(sta), para.substring(eq + 1), jop);
            }
        }
        return jop;
    }

    private void readSubPart(String params, String value, JOPObject jop) {
//        System.out.println(params + " " + value + " " + jop.getName());
        int sta = params.indexOf("[");
        int cl = params.indexOf("]");
        int lst = params.lastIndexOf("]");
        String first = params.substring(sta + 1, cl);
        if (cl == lst) {
            JOPObject jop1 = new JOPObject(first, URLDecoder.decode(value));
            jop.put(first, jop1);
        } else {
            JOPObject jop2 = (JOPObject) jop.get(first);
            if (jop2 == null) {
                jop2 = new JOPObject(first);
                jop.put(first, jop2);
            }
            readSubPart(params.substring(cl + 1, lst + 1), value, jop2);
        }
    }
}
