package com.h.mall.myprod.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

import com.h.mall.myprod.model.HttpRes;
import com.h.mall.myprod.util.HttpUtil;

public class LogInService {

	/**
	 *  
	 * "Url:https://kyfw.12306.cn/otn/login/loginAysnSuggest"
	 * "Form Data"
	 * 
	 */
	public HttpRes LongIn(List<String> keys,List<String> values,Map<String,String> cookieMap)
			throws URISyntaxException, KeyManagementException, 
					KeyStoreException, NoSuchAlgorithmException, 
					CertificateException, IOException{
		
		URI uri = new URIBuilder().setScheme("https")
				.setHost("kyfw.12306.cn")
				.setPath("/otn/login/loginAysnSuggest")
				.build();
		
		return HttpUtil.postSsl(uri, keys, values,cookieMap,"",false);
	}
	
	public static void main(String[] args) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException, URISyntaxException, IOException {
		 
		new LogInService().getRandomCode();
	}
	
	/**
	 * 
	 * "Url:https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew"
	 * "Form Data"
	 */
	public HttpRes getRandomCode() 
			throws URISyntaxException, KeyManagementException, 
					KeyStoreException, NoSuchAlgorithmException, 
					CertificateException, IOException{
		
		URI uri = new URIBuilder().setScheme("https")
				.setHost("kyfw.12306.cn")
				.setPath("/otn/passcodeNew/getPassCodeNew")
				.setParameter("module", "login")
				.setParameter("rand", "sjrand")
				.build();
		
		List<String> keys = new ArrayList<String>();
		keys.add("emailStatus");
		keys.add("channelType");
		keys.add("inviter");
		keys.add("backurl");

		
		List<String> values = new ArrayList<String>();
		
		values.add("0");
		values.add("");
		values.add("");
		values.add("");
		
		
		HttpRes httpRes = HttpUtil.getSsl(uri);
		
		HttpEntity entity = httpRes.getHttpEntity();
		File file = new File("D:\\image\\randCode.png");
		OutputStream outStream = new FileOutputStream(file);
		if (entity != null) {
			 entity.writeTo(outStream); 
		}
		outStream.close();
		EntityUtils.consume(entity);
		
		
		return httpRes;
	}
	
	
	/**
	 * 
	 * "Url:https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew"
	 * "Form Data"
	 */
	public HttpRes getRandomCode(String i) 
			throws URISyntaxException, KeyManagementException, 
					KeyStoreException, NoSuchAlgorithmException, 
					CertificateException, IOException{
		
		URI uri = new URIBuilder().setScheme("https")
				.setHost("kyfw.12306.cn")
				.setPath("/otn/passcodeNew/getPassCodeNew")
				.setParameter("module", "login")
				.setParameter("rand", "sjrand")
				.build();
		
		
		HttpRes httpRes = HttpUtil.getSsl(uri);
		
		HttpEntity entity = httpRes.getHttpEntity();
		File file = new File("D:\\image\\randCode"+i+".png");
		OutputStream outStream = new FileOutputStream(file);
		if (entity != null) {
			 entity.writeTo(outStream); 
		}
		outStream.close();
		EntityUtils.consume(entity);
		
		
		return httpRes;
	}
	
	/**
	 * 	
	 * "Url" https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsy
	 * "Form Data" randCode:kanx 
	 * 			   rand:sjrand 
	 */
	public HttpRes checkRandCode(List<String> keys,List<String> values,Map<String,String> cookieMap) 
			throws URISyntaxException, KeyManagementException, 
					KeyStoreException, NoSuchAlgorithmException, 
					CertificateException, IOException{
		
		URI uri = new URIBuilder().setScheme("https")
				.setHost("kyfw.12306.cn")
				.setPath("/otn/passcodeNew/checkRandCodeAnsy")
				.build();
		
		HttpRes httpRes = HttpUtil.postSsl(uri, keys, values, cookieMap,"",false);
		return httpRes;
	}
	
	/**
	 * 
	 * "Url" https://kyfw.12306.cn/otn/login/existUser
	 * "Form Data" _json_att:
	 */
	public HttpRes existUser(List<String> keys,List<String> values,Map<String,String> cookieMap)
			throws URISyntaxException, KeyManagementException, 
			KeyStoreException, NoSuchAlgorithmException, 
			CertificateException, IOException{
		
		URI uri = new URIBuilder().setScheme("https")
				.setHost("kyfw.12306.cn")
				.setPath("/otn/index/init")
				.build();
		
		HttpRes httpRes = HttpUtil.postSsl(uri, keys, values, cookieMap,"",false);
		return httpRes;
	}
	 
	

}
