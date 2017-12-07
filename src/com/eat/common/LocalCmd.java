package com.eat.common;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author  sylar
 * @version 0.0.1
 * @date    2017-12-07
 */
public class LocalCmd {
    private Process proc = null;
    private BufferedReader input  = null;
    private BufferedWriter output = null;

    public String execute(String cmd, long timeout){
        Runtime rt = Runtime.getRuntime();
        Process pr = null;
        InputStream isr = null;
        BufferedReader in = null;

        StringBuffer ret = new StringBuffer();
        try{
            pr = rt.exec(cmd);

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
                System.out.println("execute " + cmd + " timeout");
                pr.destroy();
                System.out.println(ret.toString());
                return "";
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

    public static void main(String[] args) throws IOException, InterruptedException {
        LocalCmd cmd = new LocalCmd();

        String ret = cmd.execute("ping 127.0.0.1", 10L);
        System.out.println(ret);
        ret = cmd.execute("ls -l", 3L);
        System.out.println(ret);
    }
}
