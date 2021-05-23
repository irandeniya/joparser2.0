/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Ishan
 */
final class WriteObject extends Writer {

    private String readCls(String q) {
        int eq = q.indexOf("=\"");
        int en = q.indexOf("\">");
        if (eq < en) {
            return q.substring(eq + 2, en);
        }
        return null;
    }

    private String readValue(String q) {
        int eq = q.indexOf("\">");
        int en = q.lastIndexOf("<");
        if (eq < en) {
            return q.substring(eq + 2, en);
        }
        return null;
    }

    @Override
    protected Object writeObject(String elem) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        String att = readCls(elem);
        if (att == null || att.equals("null")) {
            return null;
        } else if (att.equals("java.lang.Integer")) {
            return Integer.parseInt(readValue(elem));
        } else if (att.equals("java.lang.Double")) {
            return Double.parseDouble(readValue(elem));
        } else if (att.equals("java.lang.Float")) {
            return Float.parseFloat(readValue(elem));
        } else if (att.equals("java.lang.Long")) {
            return Long.parseLong(readValue(elem));
        } else if (att.equals("java.lang.Short")) {
            return Short.parseShort(readValue(elem));
        } else if (att.equals("java.lang.Byte")) {
            return Byte.parseByte(readValue(elem));
        } else if (att.equals("java.lang.Boolean")) {
            return Boolean.parseBoolean(readValue(elem));
        } else if (att.equals("java.lang.Character")) {
            return readValue(elem).charAt(0);
        } else if (att.equals("java.util.Date")) {
            return new Date(Long.parseLong(readValue(elem)));
        } else if (att.equals("java.lang.String")) {
            return readValue(elem);
        } else if (att.equals("java.lang.StringBuffer")) {
            return new StringBuffer(readValue(elem));
        } else if (att.equals("java.lang.StringBuilder")) {
            return new StringBuilder(readValue(elem));
        } else if (att.startsWith("[Ljava.lang")) {
            return writeCollectionAndArrays(elem);
        } else if (att.startsWith("[")) {
            return writeCollectionAndArrays(elem);
        }

        Class<?> clazz = Class.forName(att);
        Object object = clazz.newInstance();

        if (clazz != null) {
            if (object instanceof Collection || clazz.isArray()) {
                return writeCollectionAndArrays(elem);
            } else if (object instanceof Map) {
                return writeMaps(elem);
            } else {
                ArrayList<String> nList = red2(red2(elem).get(0));
                for (int t = 0; t < nList.size(); t++) {
                    String node = nList.get(t);
                    if (node.startsWith("<name>")) {
                        ArrayList<String> chn = red2(node);
                        if (chn.size() >= 2 && chn.get(0) != null) {
                            String methName = getMethodName(chn.get(0));
                            Method[] meth = clazz.getMethods();
                            for (Method me : meth) {
                                if (me.getName().equals(methName)) {
                                    Object cont = writeObject(node.substring(node.indexOf("</name>") + 7));
                                    me.invoke(object, cont);
                                    break;
                                }
                            }

                        }
                    }
                }
            }
        }

        return object;
    }

    @Override
    protected Object writeMaps(String elem) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        String att = readCls(elem);
        Class<?> clazz = Class.forName(att);
        Object object = clazz.newInstance();
        if (object instanceof Map) {
            Map map = (Map) object;
            ArrayList<String> nList = red3(red2(elem).get(0));
            for (int t = 0; t < nList.size(); t++) {
                String node = nList.get(t);
                if (node != null) {
                    ArrayList<String> vlvs = red3(red2(node).get(0));
                    map.put(writeObject(vlvs.get(0)), writeObject(vlvs.get(1)));
                }
            }
        }
        return object;
    }

    @Override
    protected Object writeCollectionAndArrays(String elem) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String att = readCls(elem);
        Class<?> clazz = Class.forName(att);
        Object object = null;
        if (!att.startsWith("[")) {
            object = clazz.newInstance();
        }
        if (object instanceof Collection) {
            Collection collection = (Collection) object;
            ArrayList<String> nList = red3(red2(elem).get(0));
            for (int t = 0; t < nList.size(); t++) {
                String node = nList.get(t);
                if (node != null) {
                    collection.add(writeObject(node));
                }
            }
        }
        if (att.startsWith("[Ljava.lang") || att.startsWith("[")) {
            Vector v = new Vector();
            ArrayList<String> nList = red3(red2(elem).get(0));
            for (int t = 0; t < nList.size(); t++) {
                String node = nList.get(t);
                if (node != null) {
                    v.add(writeObject(node));
                }
            }

            Object ary[] = v.toArray();
            if (att.contains("[Ljava.lang.String;")) {
                object = Arrays.copyOf(ary, ary.length, String[].class);
            } else if (att.contains("[Ljava.lang.StringBuffer;")) {
                object = Arrays.copyOf(ary, ary.length, StringBuffer[].class);
            } else if (att.contains("[Ljava.lang.StringBuilder;")) {
                object = Arrays.copyOf(ary, ary.length, StringBuilder[].class);
            } else if (att.contains("Integer") || att.contains("[I")) {
                Integer inta[] = Arrays.copyOf(ary, ary.length, Integer[].class);
                object = inta;
                if (att.contains("[I")) {
                    int ara[] = new int[inta.length];
                    int ind = 0;
                    for (int c : inta) {
                        ara[ind] = inta[ind++];
                    }
                    object = ara;
                }
            } else if (att.contains("Double") || att.contains("[D")) {
                Double inta[] = Arrays.copyOf(ary, ary.length, Double[].class);
                object = inta;
                if (att.contains("[D")) {
                    double ara[] = new double[inta.length];
                    int ind = 0;
                    for (double c : inta) {
                        ara[ind] = inta[ind++];
                    }
                    object = ara;
                }
            } else if (att.contains("Float") || att.contains("[F")) {
                Float inta[] = Arrays.copyOf(ary, ary.length, Float[].class);
                object = inta;
                if (att.contains("[F")) {
                    float ara[] = new float[inta.length];
                    int ind = 0;
                    for (float c : inta) {
                        ara[ind] = inta[ind++];
                    }
                    object = ara;
                }
            } else if (att.contains("Long") || att.contains("[L")) {
                Long inta[] = Arrays.copyOf(ary, ary.length, Long[].class);
                object = inta;
                if (att.contains("[L")) {
                    long ara[] = new long[inta.length];
                    int ind = 0;
                    for (long c : inta) {
                        ara[ind] = inta[ind++];
                    }
                    object = ara;
                }
            } else if (att.contains("Short") || att.contains("[S")) {
                Short inta[] = Arrays.copyOf(ary, ary.length, Short[].class);
                object = inta;
                if (att.contains("[S")) {
                    short ara[] = new short[inta.length];
                    int ind = 0;
                    for (short c : inta) {
                        ara[ind] = inta[ind++];
                    }
                    object = ara;
                }
            } else if (att.contains("Byte") || att.contains("[B")) {
                Byte inta[] = Arrays.copyOf(ary, ary.length, Byte[].class);
                object = inta;
                if (att.contains("[B")) {
                    byte ara[] = new byte[inta.length];
                    int ind = 0;
                    for (byte c : inta) {
                        ara[ind] = inta[ind++];
                    }
                    object = ara;
                }
            } else if (att.contains("Boolean") || att.contains("[Z")) {
                Boolean inta[] = Arrays.copyOf(ary, ary.length, Boolean[].class);
                object = inta;
                if (att.contains("[Z")) {
                    boolean ara[] = new boolean[inta.length];
                    int ind = 0;
                    for (boolean c : inta) {
                        ara[ind] = inta[ind++];
                    }
                    object = ara;
                }
            } else if (att.contains("Character") || att.contains("[C")) {
                Character inta[] = Arrays.copyOf(ary, ary.length, Character[].class);
                object = inta;
                if (att.contains("[C")) {
                    char ara[] = new char[inta.length];
                    int ind = 0;
                    for (char c : inta) {
                        ara[ind] = inta[ind++];
                    }
                    object = ara;
                }
            } else if (att.contains("Date")) {
                object = Arrays.copyOf(ary, ary.length, Date[].class);
            } else {
                object = v.toArray();
            }

        }
        return object;
    }

    private ArrayList<String> red3(String a) {

        int length = a.length();
        LinkedList<String> list = new LinkedList();
        StringBuilder build = new StringBuilder(a);
        ArrayList<String> param = new ArrayList();

        int start = -1;
        for (int in = 0; in < length - 1; in++) {
            char as = build.charAt(0);
            char ab = build.charAt(1);
            if (as == '<') {
                int space = build.indexOf(" ");
                int cl = build.indexOf(">");
                if (space > cl || space == -1) {
                    space = cl;
                }
                if (ab == '/') {
                    list.removeLast();
                    if (start != -1 && list.size() == 0) {
                        if (list.size() == 0) {
                            String vlv = a.substring(start, in + build.indexOf(">") + 1);
                            param.add(vlv);
                            start = -1;
                        }
                    }
                } else {
                    String var = build.substring(1, space);
                    if (list.size() == 0) {
                        start = in;
                    }
                    list.add(var);
                }
            }
            build.delete(0, 1);
        }
        return param;
    }

    private ArrayList<String> red2(String a) {

        int length = a.length();
        LinkedList<String> list = new LinkedList();
        StringBuilder build = new StringBuilder(a);
        ArrayList<String> param = new ArrayList();

        int start = -1;
        for (int in = 0; in < length - 1; in++) {
            char as = build.charAt(0);
            char ab = build.charAt(1);
            if (as == '<') {
                int space = build.indexOf(" ");
                int cl = build.indexOf(">");
                if (space > cl || space == -1) {
                    space = cl;
                }
                if (ab == '/') {
                    list.removeLast();
                    if (start != -1 && list.size() == 0) {
                        String vlv = a.substring(start, in);
                        param.add(vlv);
                        start = -1;
                    }
                } else {
                    String var = build.substring(1, space);
                    if (list.size() == 0) {
                        start = in + build.indexOf(">") + 1;
                    }
                    list.add(var);
                }
            }
            build.delete(0, 1);
        }
        return param;
    }
}
