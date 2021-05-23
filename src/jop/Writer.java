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
public abstract class Writer {

    protected String getMethodName(String variableName) {
        return "set" + variableName.substring(0, 1).toUpperCase() + variableName.substring(1);
    }

    protected abstract Object writeObject(String elem) throws Exception;

    protected abstract Object writeMaps(String elem) throws Exception;

    protected abstract Object writeCollectionAndArrays(String elem) throws Exception;
}
