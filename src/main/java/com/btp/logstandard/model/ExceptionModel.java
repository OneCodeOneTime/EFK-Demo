package com.btp.logstandard.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * �쳣��¼ģ��
 * @author baitp
 *
 */
public class ExceptionModel {
	/**
	 * type:�쳣����
	 */
	private String type;
	
	/**
	 * message:�쳣��Ϣ
	 */
	private String message;
	
	/**
	 * localizedMessage:�ֲ���Ϣ
	 */
	private String localizedMessage;
	
	/**
	 * cause:�����쳣������Դͷ�쳣
	 */
	private String cause;
	
	/**
	 * mac������mac
	 */
	private String mac;
	
	/**
	 * ip:����ip
	 */
	private String ip;
	
	/**
	 * time:ʱ�䣨��ʽ����
	 */
	private String time;
	
	/**
	 * timemillis:ʱ�䣨�����ʾ��
	 */
	private String timemillis;
	
	/**
	 * remotePort:Զ�̶˿�
	 */
	private String remotePort;
	
	/**
	 * serverPort������˿�
	 */
	private String serverPort;
	
	/**
	 * thread:�߳�
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
	 * questParams���������
	 */
	private String questParams;
	
	/**
	 * extendedStackTrace���쳣ջ
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
	 * ��һ��JSON����תΪExceptionModel
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
	 * ��ExceptionModel����ʱ�����з���
	 * @param list
	 * @return List
	 */
	public static List<ExceptionModel> sortByTime(List<ExceptionModel> list){
		//��ʱ������
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
