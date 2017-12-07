package com.eat.autotest;

/**
 *
 * @author  sylar
 * @version 0.0.1
 * @create  2017-12-08
 * @modify
 */
public class Base {
    protected String content = null;
    protected String FLAG = null;
    protected String name = null;

    Base(){

    }

    /**
     *
     *
     * @param value
     * @return
     */
    public boolean parse(String value){
        return false;
    }

    /**
     *
     *
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     *
     *
     * @return
     */
    public String getContent(){
        return this.content;
    }
}
