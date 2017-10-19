package com.btp.flume.tool;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.event.EventBuilder;

import com.btp.common.constants.Author;
import com.btp.logstandard.serviceFanin.LogStandardUtil;

/**
 * 和flume交互，直接写数据到flume
 * @author baitp
 *
 */
public class FlumeTool {
	/**
	 * 写日志到flume
	 */
	public static void logToFlume(Throwable e,HttpServletRequest request) {
		 MyRpcClientFacade client = new MyRpcClientFacade();
		 client.init("localhost", 44444); //flume所在的机器ip，端口号
		 client.sendDataToFlume(LogStandardUtil.logExceptionJsonString(e, request));
		 client.cleanUp();
		 
		 //日志消费到elasticsearch
		 //KafkaTool.consume();
	}
	 
	 static class MyRpcClientFacade {
		 private RpcClient client;
		 private String hostname;
		 private int port;
		 public void init(String hostname, int port) {
			 this.hostname = hostname;
			 this.port = port;
			 this.client = RpcClientFactory.getDefaultInstance(hostname, port);
		 }
		 public void sendDataToFlume(String data) {
			 Event event = EventBuilder.withBody(data, Charset.forName("UTF-8"));
			 try {
				 client.append(event);
			 } catch (EventDeliveryException e) {
				 client.close();
				 client = null;
				 client = RpcClientFactory.getDefaultInstance(hostname, port);
			 }
		 }
		 public void cleanUp() {
			 client.close();
		 }
	}
	 
	 @Author("拜糖平")
	public static void main(String[] args) {/*
		  try{
		   //创建连接
		   URL url = new URL("http://127.0.0.1:5140");
		   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		   connection.setDoOutput(true);
		   connection.setDoInput(true);
		   connection.setRequestMethod("POST");
		   connection.setUseCaches(false);
		   connection.setInstanceFollowRedirects(true);
		   connection.setRequestProperty("Content-Type",
		   "application/x-www-form-urlencoded");
		   connection.connect();
		   //POST请求
		   DataOutputStream out = new DataOutputStream(
		     connection.getOutputStream());
		  
		   JSONEvent jse = new JSONEvent();
		   Map<String,String> ipt = new HashMap<String,String>();
		   ipt.put("type", "g3");
		   ipt.put("brand", "six god");
		   jse.setBody("cc33  test".getBytes());
		   jse.setHeaders(ipt);
		   Gson gson = new Gson();
		   List<JSONEvent> events1 = new ArrayList<JSONEvent>();
		   events1.add(jse);
		   out.writeBytes(gson.toJson(events1));
		   out.flush();
		   out.close();
		   //读取响应
		   BufferedReader reader = new BufferedReader(new InputStreamReader(
		     connection.getInputStream()));
		   String lines;
		   StringBuffer sb = new StringBuffer("");
		   while ((lines = reader.readLine()) != null) {
		    lines = new String(lines.getBytes(), "utf-8");
		    sb.append(lines);
		   }
		   System.out.println(sb);
		   reader.close();
		   // 断开连接
		   connection.disconnect();
		  } catch (MalformedURLException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  } catch (UnsupportedEncodingException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		 */
		 RuntimeException r1 = new RuntimeException();
		 NullPointerException n1 = new NullPointerException();
		 n1.initCause(new ArithmeticException ());
		 r1.initCause(n1);
		 
		 try {
		 	throw r1;
		 }catch(RuntimeException e) {
			 logToFlume(e, null);
		 }
	}
}
