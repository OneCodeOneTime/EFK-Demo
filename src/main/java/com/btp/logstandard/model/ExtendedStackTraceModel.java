package com.btp.logstandard.model;

/**
 * 异常栈模型
 * @author baitp
 *
 */
public class ExtendedStackTraceModel {
	/**
	 * author:方法的作者
	 */
	private String author;
	
	/**
	 * file:异常发生的文件
	 */
	private String file;
	
	/**
	 * traceSeat:异常栈在整个异常栈的位置
	 */
	private String traceSeat;
	
	/**
	 * line:文件位置
	 */
	private String line;
	
	/**
	 * clazz：异常发生的类
	 */
	private String clazz;
	
	/**
	 * method:发生异常的方法
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
