package com.eat.common;

import java.io.Closeable;
import java.io.IOException;

public class util {

	public static boolean isNull(Object obj){
		if( obj != null){
			return false;
		}
		return true;
	}

	public static boolean isNull(String val) {
		if (val == null || "".equals(val)) {
			return true;
		}
		return false;
	}
	
	public static boolean notNull(Object obj){
		return !isNull(obj);
	}

	public static boolean notNull(String val){
		return !isNull(val);
	}

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
