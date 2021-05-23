/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jop;

/**
 *
 * @author Ishan
 */
public abstract class Reader {

    protected String getVariableName(String methName) {
        if (methName.startsWith("get")) {
            return methName.substring(3, 4).toLowerCase() + methName.substring(4);
        } else {
            return methName.substring(2, 3).toLowerCase() + methName.substring(3);
        }
    }

    protected abstract String readObject(String name, Object object) throws Exception;

    protected abstract String readCollectionAndArrays(String name, Object object) throws Exception;

    protected abstract String readMaps(String name, Object object) throws Exception;
}
