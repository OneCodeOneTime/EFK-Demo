package com.btp.logstandard.autoTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.servlet.http.HttpServlet;

import com.btp.kafka.tool.KafkaTool;
import com.btp.logstandard.model.ExceptionModel;

/**
 * 启动kafka的消费进程
 * @author baitp
 *
 */
public class KafkaComsumer extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	static ExecutorService exec = null;
	
	static {
		
	}
	
	@Override
	public void init() {
		System.out.println(">>>>>>>>> kafka服务启动.....");
		exec = Executors.newSingleThreadExecutor();
		FutureTask<ExceptionModel> ft = new FutureTask<ExceptionModel>(new Callable<ExceptionModel>() {
			@Override
			public ExceptionModel call() throws Exception {
				KafkaTool.consume();
				return null;
			}
		});
		
		exec.submit(ft);
	}
}
