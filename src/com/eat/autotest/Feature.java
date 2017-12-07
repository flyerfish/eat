package com.eat.autotest;

import com.eat.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  sylar
 * @version 0.0.1
 * @create  2017-12-08
 * @modify
 */
public class Feature extends Base {
    private List<Step>initStep  = new ArrayList<Step>();
    private List<Step>clearStep = new ArrayList<Step>();
    private List<Case>testcases = new ArrayList<Case>();

    Feature(){
        super();
        this.FLAG = "#";//don't modify FLAG
    }

    /**
     * parse feature line
     *
     * @param value
     * @return
     */
    public boolean parse(String value){
        this.content = value;
        if( util.isNull(value) || util.isNull( value.trim() ) ){
            return false;
        }

        value = value.trim();
        if( value.indexOf(FLAG) == -1 ){
            return false;
        }

        value = value.substring(FLAG.length()).trim();
        if( util.isNull(value)){
            return false;//feature name is null
        }
        this.name = value;
        return true;
    }

    /**
     * test it
     *
     * @param args
     */
    public static void main(String[] args) {
        Feature f = new Feature();
        System.out.println( f.parse("# feature name") );
    }
}
