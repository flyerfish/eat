package com.eat.common;

import java.io.*;
import java.util.concurrent.TimeUnit;

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
		return obj == null;
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

	/**
	 * execute local command/shell
	 *
	 * @param cmd		command string
	 * @param workPath  cmd with work path
	 * @param timeout   wait result for timeout time, not block forever.
	 *                  time unit is second
	 * @return result of command
	 */
	public static String exec(String cmd, String workPath, long timeout)throws Exception{
		Runtime rt = Runtime.getRuntime();
		Process pr = null;
		InputStream isr = null;
		BufferedReader in = null;

		//StringBuffer is thread safe class
		StringBuffer ret = new StringBuffer();
		try{
			File fworkPath = null;
			if( notNull(workPath) )
			{
				fworkPath = new File(workPath);
				if( false == fworkPath.isDirectory() ){
					throw new Exception( workPath + " is not directory");
				}
			}

			pr = rt.exec(cmd, null, fworkPath);
			isr = pr.getInputStream();
			in = new BufferedReader( new InputStreamReader(isr));

			String line = null;
			timeout = timeout * 1000;
			while( timeout > 0 ){
				if( pr.waitFor(100L, TimeUnit.MILLISECONDS) ){
					while( (line = in.readLine()) != null ){
						ret.append(line + "\n");
					}
					break;
				}else{
					if( in.ready() ){
						line = in.readLine();
						ret.append(line + "\n");
					}
					timeout = timeout - 100;
				}
			}
			if( timeout <= 0 ){
				pr.destroy();
				throw new Exception("execute [" + cmd +"] timeout, output=\n" + ret.toString() );
			}
		}catch(Exception e){
			e.printStackTrace();
			pr.destroy();
			return "";
		}finally {
			util.close(in);
			util.close(isr);
		}
		return ret.toString();
	}

	/**
	 * execute local command/shell
	 *
	 * @param cmd
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String exec(String cmd, long timeout)throws Exception{
		return exec(cmd, null, timeout);
	}

	/**
	 *
	 * @param left
	 * @param right
	 * @return
	 */
	public static boolean equals(byte[] left, byte[]right){
		if( isNull(left) && isNull(right) ){
			return true;
		}
		if( isNull(left) || isNull(right) ){
			return false;
		}
		if( left.length != right.length ){
			return false;
		}

		for( int i = 0; i < left.length; ++i ){
			if( left[i] != right[i] ){
				return false;
			}
		}

		return true;
	}

	/**
	 *
	 * @param left
	 * @param right
	 * @return
	 */
	public static byte[] merge(byte[]left, byte[]right){
		byte[] ret = new byte[left.length + right.length];
		System.arraycopy(left, 0, ret, 0, left.length );
		System.arraycopy(right, 0, ret, left.length, right.length );
		return ret;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(util.exec("sleep 5", null, 2));
	}

}
