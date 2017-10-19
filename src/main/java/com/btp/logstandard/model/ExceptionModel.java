package com.btp.logstandard.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 异常记录模型
 * @author baitp
 *
 */
public class ExceptionModel {
	/**
	 * type:异常类型
	 */
	private String type;
	
	/**
	 * message:异常信息
	 */
	private String message;
	
	/**
	 * localizedMessage:局部信息
	 */
	private String localizedMessage;
	
	/**
	 * cause:导致异常发生的源头异常
	 */
	private String cause;
	
	/**
	 * mac：请求mac
	 */
	private String mac;
	
	/**
	 * ip:请求ip
	 */
	private String ip;
	
	/**
	 * time:时间（格式化）
	 */
	private String time;
	
	/**
	 * timemillis:时间（毫秒表示）
	 */
	private String timemillis;
	
	/**
	 * remotePort:远程端口
	 */
	private String remotePort;
	
	/**
	 * serverPort：服务端口
	 */
	private String serverPort;
	
	/**
	 * thread:线程
	 */
	private String thread;
	
	/**
	 * URI
	 */
	private String URI;
	
	/**
	 * URL
	 */
	private String URL;
	
	/**
	 * questParams：请求参数
	 */
	private String questParams;
	
	/**
	 * extendedStackTrace：异常栈
	 */
	private ExtendedStackTraceModel extendedStackTrace;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLocalizedMessage() {
		return localizedMessage;
	}

	public void setLocalizedMessage(String localizedMessage) {
		this.localizedMessage = localizedMessage;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTimemillis() {
		return timemillis;
	}

	public void setTimemillis(String timemillis) {
		this.timemillis = timemillis;
	}

	public String getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(String remotePort) {
		this.remotePort = remotePort;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getQuestParams() {
		return questParams;
	}

	public void setQuestParams(String questParams) {
		this.questParams = questParams;
	}

	public ExtendedStackTraceModel getExtendedStackTrace() {
		return extendedStackTrace;
	}

	public void setExtendedStackTrace(ExtendedStackTraceModel extendedStackTrace) {
		this.extendedStackTrace = extendedStackTrace;
	}
	
	
	
	

	@Override
	public String toString() {
		return "ExceptionModel [type=" + type + ", message=" + message + ", localizedMessage=" + localizedMessage
				+ ", cause=" + cause + ", mac=" + mac + ", ip=" + ip + ", time=" + time + ", timemillis=" + timemillis
				+ ", remotePort=" + remotePort + ", serverPort=" + serverPort + ", thread=" + thread + ", URI=" + URI
				+ ", URL=" + URL + ", questParams=" + questParams + ", extendedStackTrace=" + extendedStackTrace + "]";
	}

	/**
	 * 将一个JSON对象转为ExceptionModel
	 * @param jsons
	 * @return List<ExceptionModel>
	 */
	public static List<ExceptionModel> jsonToExceptionModel(List<JSONObject> jsons) {
		List<ExceptionModel> list = new ArrayList<ExceptionModel>();
		ExceptionModel exception;
		for(JSONObject jsonOb : jsons) {
			if(!"exception".equals(jsonOb.getString("_type"))) {
				continue;
			}
			JSONObject single = jsonOb.getJSONObject("_source");
			exception = new ExceptionModel();
			exception.setLocalizedMessage(null == single.getString("localizedMessage")?"":single.getString("localizedMessage"));
			exception.setCause(null == single.getString("cause")?"":single.getString("cause"));
			exception.setMac(null == single.getString("mac")?"":single.getString("mac"));
			exception.setType(null == single.getString("type")?"":single.getString("type"));
			exception.setIp(null == single.getString("ip")?"":single.getString("ip"));
			exception.setMessage(null == single.getString("message")?"":single.getString("message"));
			exception.setTime(null == single.getString("time")?"":single.getString("time"));
			exception.setServerPort(null == single.getString("serverPort")?"":single.getString("serverPort"));
			exception.setThread(null == single.getString("thread")?"":single.getString("thread"));
			exception.setRemotePort(null == single.getString("remotePort")?"":single.getString("remotePort"));
			exception.setTimemillis(null == single.getString("timemillis")?"":single.getString("timemillis"));
			exception.setURI(null == single.getString("URI")?"":single.getString("URI"));
			exception.setURL(null == single.getString("URL")?"":single.getString("URL"));
			
			try {
				JSONObject ja = (JSONObject) single.getJSONArray("extendedStackTrace").get(0);
				ExtendedStackTraceModel estm = new ExtendedStackTraceModel();
				estm.setAuthor(null == ja.getString("author")?"":ja.getString("author"));
				estm.setClazz(null == ja.getString("class")?"":ja.getString("class"));
				estm.setTraceSeat(null == ja.getString("traceSeat")?"":ja.getString("traceSeat"));
				estm.setFile(null == ja.getString("file")?"":ja.getString("file"));
				estm.setLine(null == ja.getString("line")?"":ja.getString("line"));
				estm.setMethod(null == ja.getString("method")?"":ja.getString("method"));
				exception.setExtendedStackTrace(estm);
			}catch(Exception e) {
				exception.setExtendedStackTrace(null);
			}
			Object questParamsOb = single.get("questParams");
			if(null != questParamsOb) {
				exception.setQuestParams(questParamsOb.toString());
			}
			
			list.add(exception);
		}
		return list;
	}
	
	/**
	 * 将ExceptionModel按照时间排列返回
	 * @param list
	 * @return List
	 */
	public static List<ExceptionModel> sortByTime(List<ExceptionModel> list){
		//按时间排序
		Collections.sort(list, new Comparator<ExceptionModel>() {

			@Override
			public int compare(ExceptionModel o1, ExceptionModel o2) {
				
				
				BigInteger b1 = new BigInteger(o1.getTimemillis());
				BigInteger b2 = new BigInteger(o2.getTimemillis());
				
				if(b1.compareTo(b2) > 0) {
					return 1;
				}else if(b1.compareTo(b2) < 0) {
					return -1;
				}
				
				return 0;
			}
			
		});
		return list;
	}
}
