/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Ishan
 */
final class ReadObjects extends Reader {

    private String readClassType(Object object) {
        String name = object.getClass().getName();
        if (name.equals("java.sql.Timestamp")) {
            return "java.util.Date";
        } else if (name.equals("org.hibernate.collection.internal.PersistentSet")) {
            return "java.util.HashSet";
        }
        return name;
    }

    @Override
    protected String readObject(String name, Object object) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        String s = "";
        if (object == null) {
            return "<object class=\"null\"></object>";
        }
        Class<?> clazz = object.getClass();
        Method[] method = clazz.getDeclaredMethods();
        if (method != null) {
            s = "<object class=\"" + readClassType(object) + "\">";
            if (object instanceof String || object instanceof Number || object instanceof Boolean
                    || object instanceof Character || object instanceof StringBuilder || object instanceof StringBuffer) {
                s += object.toString();
            } else if (object instanceof Date) {
                Date dat = (Date) object;
                s += dat.getTime();
            } else if (object instanceof Collection || clazz.isArray()) {
                return readCollectionAndArrays(name, object);
            } else if (object instanceof Map) {
                return readMaps(name, object);
            } else {
                for (Method meth : method) {
                    if ((meth.getName().startsWith("get") || meth.getName().startsWith("is")) && !meth.getName().equals("getClass")) {
                        Object val = meth.invoke(object);
                        if (val != null) {
                            s += "<param>";
                            s += "<name>" + getVariableName(meth.getName()) + "</name>";
                            s += readObject(name, val);
                            s += "</param>";
                        }
                    }
                }
            }
            s += "</object>";
        }
        return s;
    }

    private boolean Skip(Object object) {
        String name = object.getClass().getName();
        if (name.equals("java.sql.Timestamp") || name.equals("org.hibernate.collection.internal.PersistentSet")) {
            return false;
        }
        return true;
    }

    @Override
    protected String readCollectionAndArrays(String name, Object object) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        String s = "";
        if (object != null) {
            if (object instanceof Collection) {
                if (Skip(object)) {
                    Collection collection = (Collection) object;
                    s += "<collection class=\"" + readClassType(object) + "\">";
                    for (Object obj : collection) {
                        s += readObject(name, obj);
                    }
                    s += "</collection>";
                }
            }
            if (object.getClass().isArray()) {
                if (object.getClass().getCanonicalName().equals("int[]")) {
                    int objts[] = (int[]) object;
                    s += "<array class=\"" + readClassType(object) + "\">";
                    for (Object obj : objts) {
                        s += readObject(name, obj);
                    }
                } else if (object.getClass().getCanonicalName().equals("double[]")) {
                    double objts[] = (double[]) object;
                    s += "<array class=\"" + readClassType(object) + "\">";
                    for (Object obj : objts) {
                        s += readObject(name, obj);
                    }
                } else if (object.getClass().getCanonicalName().equals("short[]")) {
                    short objts[] = (short[]) object;
                    s += "<array class=\"" + readClassType(object) + "\">";
                    for (Object obj : objts) {
                        s += readObject(name, obj);
                    }
                } else if (object.getClass().getCanonicalName().equals("byte[]")) {
                    byte objts[] = (byte[]) object;
                    s += "<array class=\"" + readClassType(object) + "\">";
                    for (Object obj : objts) {
                        s += readObject(name, obj);
                    }
                } else if (object.getClass().getCanonicalName().equals("long[]")) {
                    long objts[] = (long[]) object;
                    s += "<array class=\"" + readClassType(object) + "\">";
                    for (Object obj : objts) {
                        s += readObject(name, obj);
                    }
                } else if (object.getClass().getCanonicalName().equals("float[]")) {
                    float objts[] = (float[]) object;
                    s += "<array class=\"" + readClassType(object) + "\">";
                    for (Object obj : objts) {
                        s += readObject(name, obj);
                    }
                } else if (object.getClass().getCanonicalName().equals("boolean[]")) {
                    boolean objts[] = (boolean[]) object;
                    s += "<array class=\"" + readClassType(object) + "\">";
                    for (Object obj : objts) {
                        s += readObject(name, obj);
                    }
                } else if (object.getClass().getCanonicalName().equals("char[]")) {
                    char objts[] = (char[]) object;
                    s += "<array class=\"" + readClassType(object) + "\">";
                    for (Object obj : objts) {
                        s += readObject(name, obj);
                    }
                } else {
                    Object objts[] = (Object[]) object;
                    s += "<array class=\"" + readClassType(object) + "\">";
                    for (Object obj : objts) {
                        s += readObject(name, obj);
                    }
                }
                s += "</array>";

            }
        }
        return s;
    }

    @Override
    protected String readMaps(String name, Object object) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        String s = "";
        if (object != null) {
            if (Skip(object)) {
                Map map = (Map) object;
                s += "<map class=\"" + readClassType(object) + "\">";
                for (Object obj : map.keySet()) {
                    s += "<para>" + readObject(name, obj);
                    s += readObject(name, map.get(obj)) + "</para>";
                }
                s += "</map>";
            }
        }
        return s;
    }

}
