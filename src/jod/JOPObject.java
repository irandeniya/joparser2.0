/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jod;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Ishan
 */
public final class JOPObject implements Map {

    Object name;
    Object value;

    public JOPObject(Object name, Object value) {
        this.name = name;
        this.value = value;
    }

    public JOPObject() {
    }

    public JOPObject(Object name) {
        this.name = name;
    }

    @Override
    public int size() {
        if (this.value instanceof HashMap) {
            return ((HashMap) this.value).size();
        } else {
            return (this.value == null ? 0 : 1);
        }
    }

    @Override
    public boolean isEmpty() {
        if (this.value instanceof HashMap) {
            return ((HashMap) this.value).isEmpty();
        } else {
            return this.value == null;
        }
    }

    @Override
    public boolean containsKey(Object key) {
        if (this.value instanceof HashMap) {
            return ((HashMap) this.value).containsKey(key);
        } else {
            return ((JOPObject) value).name == key;
        }
    }

    @Override
    public boolean containsValue(Object value) {
        if (this.value instanceof HashMap) {
            return ((HashMap) this.value).containsValue(value);
        } else {
            return ((JOPObject) value).value == value;
        }
    }

    @Override
    public JOPObject get(Object key) {
        if (this.value instanceof JOPObject) {
            JOPObject jop = (JOPObject) value;
            if ((jop.name + "").equals(key + "")) {
                return (JOPObject) this.value;
            }
            return null;
        } else if (this.value instanceof HashMap) {
            HashMap map = (HashMap) value;
            if (map == null) {
                return null;
            }
            return (JOPObject) map.get(key+"");
        }
        return null;
    }

    public Object getValue() {
        if (this.value instanceof HashMap) {
            return this;
        } //        else if (this.value instanceof JOPObject) {
        //            return ((JOPObject) this.value).getValue();
        //        }
        else {
            return this.value;
        }
    }

    public int getInt() {
        if (value instanceof String) {
            return Integer.parseInt(value + "");
        }
        throw new NumberFormatException("Invalid try with data object " + value);
    }

    public double getDouble() {
        if (value instanceof String) {
            return Double.parseDouble(value + "");
        }
        throw new NumberFormatException("Invalid try with data object " + value);
    }

    public float getFloat() {
        if (value instanceof String) {
            return Float.parseFloat(value + "");
        }
        throw new NumberFormatException("Invalid try with data object " + value);
    }

    public byte getByte() {
        if (value instanceof String) {
            return Byte.parseByte(value + "");
        }
        throw new NumberFormatException("Invalid try with data object " + value);
    }

    public short getShort() {
        if (value instanceof String) {
            return Short.parseShort(value + "");
        }
        throw new NumberFormatException("Invalid try with data object " + value);
    }

    public long getLong() {
        if (value instanceof String) {
            return Long.parseLong(value + "");
        }
        throw new NumberFormatException("Invalid try with data object " + value);
    }

    public char getChar() {
        if (value instanceof String) {
            return (value + "").charAt(0);
        }
        throw new NumberFormatException("Invalid try with data object " + value);
    }

    public Date getDate() {
        if (value instanceof String) {
            return new Date(getLong());
        }
        throw new NumberFormatException("Invalid try with data object " + value);
    }

    public String getString() {
        if (value instanceof String) {
            return (String) value;
        }
        throw new NumberFormatException("Invalid try with data object " + value);
    }

    public Object getName() {
        return this.name;
    }

    public Object setName(Object object) {
        return this.name = object;
    }

    public JOPObject put(Object key, JOPObject value) {
//        System.out.println("adding="+name+" "+key);
        HashMap map = null;
        if (this.value == null) {
            this.value = value;
            return value;
        } else if (this.value instanceof JOPObject) {
            map = new HashMap();
            JOPObject jop = (JOPObject) this.value;
            map.put(jop.name, jop);
            this.value = map;
        } //        else if (this.value instanceof String) {
        //            map = new HashMap();
        //            JOPObject jop = new JOPObject(name, this.value);
        //            map.put(jop.name, jop);
        //            this.value = map;
        //        }
        else {
            map = (HashMap) this.value;
        }
        return (JOPObject) map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        if (this.value instanceof HashMap) {
            return ((HashMap) this.value).remove(key);
        } else if (name == key) {
            Object obj = this.value;
            this.value = null;
            return obj;
        }
        return null;
    }

    @Override
    public void putAll(Map m) {
        HashMap map = null;
        if (this.value instanceof JOPObject) {
            map = new HashMap();
            JOPObject jop = (JOPObject) this.value;
            map.put(jop.name, jop);
            this.value = map;
        } else {
            map = (HashMap) this.value;
        }
        map.putAll(m);
    }

    @Override
    public void clear() {
        name = null;
        value = null;
    }

    @Override
    public Set keySet() {
        if (this.value instanceof HashMap) {
            return ((HashMap) this.value).keySet();
        }
        return null;
    }

    @Override
    public Collection values() {
        if (this.value instanceof HashMap) {
            return ((HashMap) this.value).values();
        }
        return null;
    }

    @Override
    public Set entrySet() {
        if (this.value instanceof HashMap) {
            return ((HashMap) this.value).entrySet();
        }
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
//        if (this.value instanceof HashMap) {
//            HashMap map = (HashMap) this.value;
//            map.put(key, new JOPObject(key, value));
//        } else {
//            this.name = key;
//            this.value = value;
//        }
//        
        HashMap map = null;
        if (this.value == null) {
            this.name = key;
            this.value = value;
            return value;
        } else if (this.value instanceof JOPObject) {
            map = new HashMap();
            JOPObject jop = (JOPObject) this.value;
            map.put(jop.name, jop);
            this.value = map;
        } //        else if (this.value instanceof String) {
        //            map = new HashMap();
        //            JOPObject jop = new JOPObject(name, this.value);
        //            map.put(jop.name, jop);
        //            this.value = map;
        //        }
        else if (this.value instanceof HashMap) {
            map = (HashMap) this.value;
        } else {
            map = new HashMap();
            JOPObject jop = new JOPObject(name, this.value);
            map.put(jop.name, jop);
            this.value = map;
        }
        JOPObject jop = new JOPObject(key, value);
        map.put(key, jop);
        return this.value;
    }

}
