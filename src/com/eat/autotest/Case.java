package com.eat.autotest;

import com.eat.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author  sylar
 * @version 0.0.1
 * @create  2017-12-08
 * @modify
 */
public class Case extends Base {

    private List<Step>initStep  = new ArrayList<Step>();
    private List<Step>clearStep = new ArrayList<Step>();
    private List<Step>mainStep  = new ArrayList<Step>();

    Case(){
        super();
        this.FLAG = "##";
    }

    /**
     *
     *
     * @param value
     * @return
     */
    public boolean parse(String value) {
        this.content = value;
        if( util.isNull(value) || util.isNull( value.trim() ) ){
            return false;
        }

        value = value.trim();
        if( value.indexOf(this.FLAG) == -1 ){
            return false;
        }

        value = value.substring(FLAG.length()).trim();
        if( util.isNull(value)){
            return false;//case name is null
        }

        this.name = value;
        return true;
    }
}
