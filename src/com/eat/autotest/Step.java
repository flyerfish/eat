package com.eat.autotest;

import com.eat.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author  sylar
 * @version 0.0.1
 * @create  2017-12-08
 * @modify
 */
public class Step extends Base {
    private Map<String,String> params = new HashMap<String,String>();

    Step(){
        super();
        this.FLAG = "*";
    }

    /**
     * parse test step
     * step format : name {json object}
     * $val input  var
     * &val output var
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
            return false;//step name is null
        }
        this.name = value;
        return true;
    }

    /**
     *
     *
     * @param value
     * @return
     */
    private boolean parseParams(String value){
        return false;
    }
}
