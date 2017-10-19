package com.btp.logstandard.serviceFanin;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.btp.common.constants.Author;
import com.btp.logstandard.tool.IPAddressGet;
import com.btp.logstandard.tool.JsonFormat;
import com.btp.logstandard.tool.MacGet;

/**
 * ��־json��ʽ�����������
 * @author baitp
 *
 */
//@Service
public class LogStandardUtil {
	private static final String separatorLog = "";
	/**
	 * �쳣��¼:�ַ�����ʽ
	 */
	@SuppressWarnings("static-access")
	public static String logExceptionJsonString(Throwable e,HttpServletRequest request) {
		try {
			String message = e.getMessage();
			Class<? extends Throwable> clazz = e.getClass();
			String localmessage = e.getLocalizedMessage();
			StackTraceElement[] stes = e.getStackTrace();
			JSONObject jo = new JSONObject();
			Date now = new Date();
			jo.fluentPut("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
			jo.fluentPut("timemillis", now.getTime());
			Thread currentThread = Thread.currentThread();
			jo.fluentPut("thread", currentThread.getName());
			jo.fluentPut("type", clazz.getName());
			jo.fluentPut("message", null == message?"":message);
			jo.fluentPut("localizedMessage",null == localmessage?"":localmessage);
			
			String ip = IPAddressGet.getIpAddr(request);
			jo.fluentPut("ip", ip);
			String mac = MacGet.getMac(ip);
			jo.fluentPut("mac", mac);

			//ֻ��¼һ���쳣ջ
			JSONArray steja = new JSONArray();
			for(int i = 1;i <= stes.length;i++) {
				if(i == 1) {
					StackTraceElement ste = stes[i-1];
					JSONObject jao = new JSONObject();
					jao.fluentPut("file", ste.getFileName());
					jao.fluentPut("class", ste.getClassName());
					
					/**
					 * ��ȡ���������ߣ���ע��@Author
					 */
					Class methodClass = Class.forName(ste.getClassName());
					Method[] methods = methodClass.getDeclaredMethods();
					for(Method m : methods) {
						if(m.getName().equals(ste.getMethodName())) {
							Author author = m.getAnnotation(Author.class);
							if(null != author) {
								jao.fluentPut("author", author.value());
								break;
							}
						}
					}
					
					jao.fluentPut("method", ste.getMethodName());
					jao.fluentPut("line", ste.getLineNumber());
					jao.fluentPut("traceSeat", i);
					steja.add(jao);
				}
			}
			jo.put("extendedStackTrace", steja);
			
			//��¼���е�cause
			/*JSONArray causeja = new JSONArray();
			Throwable cause = e;
			while(null != cause.getCause()) {
				cause = cause.getCause();
				JSONObject temp = new JSONObject();
				causeja.add(temp.parse(logExceptionJsonString(cause,request)));
			}
			jo.put("cause", causeja);*/
			//�򵥼�¼һ��cause����
			if(null != e.getCause()) {
				jo.put("cause", e.getCause().getClass().getName());
			}
			
			/*��¼url���������
			 */
			String requestURI = request.getRequestURI();
			int requestPort = request.getRemotePort();
			int servPort = request.getServerPort();
			Map<String, String[]> params = request.getParameterMap();
			
			Map<String,String[]> paramsNotEmpty = new HashMap<String,String[]>();
			
			for(Map.Entry<String, String[]> entry:params.entrySet()) {
				if(entry.getValue().length > 0 && !Arrays.asList(entry.getValue()).contains("")){
					paramsNotEmpty.put(entry.getKey(), entry.getValue());
				}
			}
			
			StringBuffer requestURL = request.getRequestURL();
			jo.fluentPut("URI", requestURI);
			jo.fluentPut("remotePort", requestPort);
			jo.fluentPut("serverPort", servPort);
			jo.fluentPut("questParams", paramsNotEmpty);
			jo.fluentPut("URL", requestURL.toString());			
			StringBuffer sb = new StringBuffer(separatorLog);
			sb.append(jo.toJSONString());
			sb.append(separatorLog);
			
			
			
			return sb.toString();
		}catch(Exception ex) {
			return logExceptionJsonString(ex,request);
		}
	}
	
	/**
	 * �쳣��¼���ַ���Json��ʽ
	 * @param e
	 * @param request
	 * @return
	 */
	public static String logExceptionJsonStringFormat(Exception e,HttpServletRequest request){
		return JsonFormat.formatJson(logExceptionJsonString(e,request));
	}
	
	/**
	 * �������ü�¼���ַ�����ʽ
	 * @param arg
	 */
	public static String logMethodInvoke() {
		return "";
	}
	
	/**
	 * �������ü�¼���ַ���Json��ʽ
	 * @param arg
	 */
	public static String logMethodInvokeFormat() {
		return JsonFormat.formatJson(logMethodInvoke());
	}
	
	@Author("����װ��")
	public static void main(String...arg) {
		RuntimeException r1 = new RuntimeException();
		NullPointerException n1 = new NullPointerException();
		n1.initCause(new ArithmeticException ());
		r1.initCause(n1);
		
		try {
			throw r1;
		}catch(RuntimeException e) {
			System.out.println(logExceptionJsonStringFormat(e,null));
		}
	}
}
