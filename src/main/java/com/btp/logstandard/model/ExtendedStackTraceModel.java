package com.btp.logstandard.model;

/**
 * �쳣ջģ��
 * @author baitp
 *
 */
public class ExtendedStackTraceModel {
	/**
	 * author:����������
	 */
	private String author;
	
	/**
	 * file:�쳣�������ļ�
	 */
	private String file;
	
	/**
	 * traceSeat:�쳣ջ�������쳣ջ��λ��
	 */
	private String traceSeat;
	
	/**
	 * line:�ļ�λ��
	 */
	private String line;
	
	/**
	 * clazz���쳣��������
	 */
	private String clazz;
	
	/**
	 * method:�����쳣�ķ���
	 */
	private String method;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getTraceSeat() {
		return traceSeat;
	}

	public void setTraceSeat(String traceSeat) {
		this.traceSeat = traceSeat;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "ExtendedStackTraceModel [author=" + author + ", file=" + file + ", traceSeat=" + traceSeat + ", line="
				+ line + ", clazz=" + clazz + ", method=" + method + "]";
	}
	
	
	
}
