package com.btp.logstandard.tool;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class MacGet {
	public static String getMac(String ip){  
		String mac = "";
        try {  
        	Process p = Runtime.getRuntime().exec("arp -n");  
        	InputStreamReader ir = new InputStreamReader(p.getInputStream());  
            LineNumberReader input = new LineNumberReader(ir);  
            p.waitFor();  
            boolean flag = true;
            String ipStr = "(" + ip + ")";  
            while(flag) {  
                String str = input.readLine();  
                if (str != null) {  
                    if (str.indexOf(ipStr) > 1) {  
                        int temp = str.indexOf("at");  
                        mac = str.substring(  
                        temp + 3, temp + 20);  
                        break;  
                    }  
                } else  
                flag = false;  
            }  
        } catch (Exception e) {  
            e.printStackTrace(System.out);  
        }  
        return mac;  
    }  
}
