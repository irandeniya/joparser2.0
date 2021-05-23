/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpjoc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import jop.Reader;

/**
 *
 * @author Ishan
 */
public final class HttpRequest extends Reader {

    String request;

    /**
     *
     * @param object Which need to convert into HttpRequest
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public HttpRequest(Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        request = createHttpRequest("para", object);
    }

    /**
     *
     * @param name Root parameter name
     * @param object which need to convert into HttpRequest
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public HttpRequest(String name, Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        request = createHttpRequest(name, object);
    }

    public String getRequest() {
        return request;
    }

    String createHttpRequest(String name, Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String rep = readObject(name, object);
        if (rep != null && rep.startsWith("&")) {
            rep = rep.substring(1);
        }
        return rep;
    }

    @Override
    protected String readObject(String name, Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String s = "";
        if (object == null) {
            return null;
        }
        Class<?> clazz = object.getClass();
//        System.out.println(object.getClass().getName());
        Method[] method = clazz.getDeclaredMethods();
        if (method != null) {
            if (object instanceof String || object instanceof Number || object instanceof Boolean
                    || object instanceof Character || object instanceof StringBuilder || object instanceof StringBuffer) {
                s += (name != null ? ("&" + name + "=") : "") + URLEncoder.encode(object.toString());
            } else if (object instanceof Date) {
                Date dat = (Date) object;
                s += (name != null ? ("&" + name + "=") : "") + dat.getTime();
            } else if (object instanceof Collection || clazz.isArray()) {
                return readCollectionAndArrays(name, object);
            } else if (object instanceof Map) {
                return readMaps(name, object);
            } else {
                if (!Skip(object)) {
                    return "";
                }
                for (Method meth : method) {
                    if ((meth.getName().startsWith("get") || meth.getName().startsWith("is")) && !meth.getName().equals("getClass")) {
                        Object val = meth.invoke(object);
                        if (val != null) {
                            String vr = getVariableName(meth.getName());
                            s += readObject((name != null ? (name + "[" + vr + "]") : vr), val);
                        }
                    }
                }
            }
        }
        return s;
    }

    private boolean Skip(Object object) {
        String name = object.getClass().getName();
        return !(name.equals("java.sql.Timestamp") || name.equals("org.hibernate.collection.internal.PersistentSet")
                || name.equals("org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer"));
    }

    @Override
    protected String readCollectionAndArrays(String name, Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String s = "";
        if (object != null) {
            if (object instanceof Collection) {
                if (Skip(object)) {
                    Collection collection = (Collection) object;
                    int co = 0;
                    for (Object obj : collection) {
                        s += readObject(name + "[" + co++ + "]", obj);
                    }
                }
            }
            if (object.getClass().isArray()) {
                if (object.getClass().getCanonicalName().equals("int[]")) {
                    int objts[] = (int[]) object;
                    int co = 0;
                    for (Object obj : objts) {
                        s += readObject(name + "[" + co++ + "]", obj);
                    }
                } else if (object.getClass().getCanonicalName().equals("double[]")) {
                    double objts[] = (double[]) object;
                    int co = 0;
                    for (Object obj : objts) {
                        s += readObject(name + "[" + co++ + "]", obj);
                    }
                } else if (object.getClass().getCanonicalName().equals("short[]")) {
                    short objts[] = (short[]) object;
                    int co = 0;
                    for (Object obj : objts) {
                        s += readObject(name + "[" + co++ + "]", obj);
                    }
                } else if (object.getClass().getCanonicalName().equals("byte[]")) {
                    byte objts[] = (byte[]) object;
                    int co = 0;
                    for (Object obj : objts) {
                        s += readObject(name + "[" + co++ + "]", obj);
                    }
                } else if (object.getClass().getCanonicalName().equals("long[]")) {
                    long objts[] = (long[]) object;
                    int co = 0;
                    for (Object obj : objts) {
                        s += readObject(name + "[" + co++ + "]", obj);
                    }
                } else if (object.getClass().getCanonicalName().equals("float[]")) {
                    float objts[] = (float[]) object;
                    int co = 0;
                    for (Object obj : objts) {
                        s += readObject(name + "[" + co++ + "]", obj);
                    }
                } else if (object.getClass().getCanonicalName().equals("boolean[]")) {
                    boolean objts[] = (boolean[]) object;
                    int co = 0;
                    for (Object obj : objts) {
                        s += readObject(name + "[" + co++ + "]", obj);
                    }
                } else if (object.getClass().getCanonicalName().equals("char[]")) {
                    char objts[] = (char[]) object;
                    int co = 0;
                    for (Object obj : objts) {
                        s += readObject(name + "[" + co++ + "]", obj);
                    }
                } else {
                    Object objts[] = (Object[]) object;
                    int co = 0;
                    for (Object obj : objts) {
                        s += readObject(name + "[" + co++ + "]", obj);
                    }
                }
            }
        }
        return s;
    }

    @Override
    protected String readMaps(String name, Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String s = "";
        if (object != null) {
            if (Skip(object)) {
                Map map = (Map) object;
                for (Object obj : map.keySet()) {
                    String vr = readObject(null, obj);
                    s += readObject((name != null ? (name + "[" + vr + "]") : vr), map.get(obj));
                }
            }
        }
        return s;
    }

}
