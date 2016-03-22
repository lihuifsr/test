package com.h.mall.myprod.util;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TreadPoolExecutor {

	private static final Log log = LogFactory.getLog(TreadPoolExecutor.class);
	private static int produceTaskSleepTime = 100;
	private static int produceTaskMaxNumber = 100;
	private static int  threadNum = 20;
	
	private static ExecutorService executor = Executors.newFixedThreadPool(threadNum);
	
	
	private TreadPoolExecutor(){};
	
	public static TreadPoolExecutor getInstance(){
		
		return new TreadPoolExecutor();
	}
	
	
	public boolean isCouldDeal (){
		return ((ThreadPoolExecutor)executor).getActiveCount()<threadNum;
	}
	
	
	
	public static void dealData(){
		executor.execute(new ThreadPoolTask());
	}
	


	/**
	 * 线程池执行的任务
	 *
	 * @author hdpan
	 */
	public static class ThreadPoolTask implements Runnable, Serializable {
		private static final long serialVersionUID = 0;

		ThreadPoolTask() {}

		public void run() {

		}
	}

}
