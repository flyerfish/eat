package com.eat.common;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 * @author  sylar
 * @version 0.0.1
 * @date    2017-12-07
 */
public class util {

	/**
	 * check obj is null
	 *
	 * @param obj Object
	 * @return
	 */
	public static boolean isNull(Object obj){
		if( obj != null){
			return false;
		}
		return true;
	}

	/**
	 * check string is null or empty
	 *
	 * @param val
	 * @return
	 */
	public static boolean isNull(String val) {
		if (val == null || "".equals(val)) {
			return true;
		}
		return false;
	}

	/**
	 * check object is not null
	 *
	 * @param obj
	 * @return
	 */
	public static boolean notNull(Object obj){
		return !isNull(obj);
	}

	/**
	 * check string is not null
	 *
	 * @param val
	 * @return
	 */
	public static boolean notNull(String val){
		return !isNull(val);
	}

	/**
	 * call close function of Closeable object, there is no exception
	 *
	 * @param cl
	 */
	public static void close( Closeable cl){
		if( notNull(cl) ){
			try {
				cl.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
