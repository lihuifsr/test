package com.h.mall.myprod.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.junit.Test;

import com.h.mall.myprod.model.HttpRes;
import com.h.mall.myprod.util.CQZDMDLL;

public class LogInServiceTest {

	
	@Test
	public void randomCodeTest() 
			throws KeyManagementException, KeyStoreException, 
					NoSuchAlgorithmException, CertificateException, 
					URISyntaxException, IOException, InterruptedException{
//		for (int i = 0; i < 100; i++) {
//			new LogInService().getRandomCode(i+"");
//			
//		}
		new LogInService().getRandomCode();
		
	}

	@Test
	public void loginTest()
			throws KeyManagementException, KeyStoreException, 
					NoSuchAlgorithmException, CertificateException, 
					URISyntaxException, IOException{
		
		Map<String,String> cookieMap = new HashMap<String, String>();
		System.out.println(login(cookieMap,"1234"));	
	
	}
	
	@Test
	public void userLoginTest() throws Exception{
		
		LogInService service = new LogInService();
		
		long time1=System.currentTimeMillis();
		HttpRes httpRes = service.getRandomCode();
		long time2=System.currentTimeMillis();
		System.out.println("--------------获取验证码结束[耗时:"+(time2-time1)+"ms]------------");
		
		long time3=System.currentTimeMillis();
		String code = CQZDMDLL.getCode();
		long time4=System.currentTimeMillis();
		System.out.println("--------------识别验证码结束[randCode:"+code+"][耗时:"+(time4-time3)+"ms]------------");
		
		HeaderIterator it = httpRes.getResponse().headerIterator("Set-Cookie");
		
		Map<String,String> cookieMap = new HashMap<String, String>();
		
		while (it.hasNext()) {
			String cookies = it.next().toString();
			if(cookies.length() > 0){
				String[] cookieArry = cookies.split("\\;");
				for (String cookie : cookieArry) {
					cookieMap.put(cookie.split("\\=")[0], cookie.split("\\=")[1]);
				}
			}	
		}
		String jsonData = login(cookieMap,code);
		System.out.println(jsonData);	
		System.out.println("--------------登录成功[耗时:"+(time2-time1)+"ms]------------");
	}
	
	
	@Test
	public void existUser() throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException, URISyntaxException, IOException{
		HttpRes httpRes =new LogInService().existUser(new ArrayList<String>(), new ArrayList<String>(), new HashMap<String, String>());
		HttpEntity entity = httpRes.getHttpEntity();
		if(entity != null){
			InputStream is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = "";
			String jsonData = "";
			while ((line = br.readLine()) != null) {
				jsonData += line;
			}
			System.out.println(jsonData);
		}
	}
	
	
	
	private String login(Map<String,String> cookieMap,String randCode)
			throws KeyManagementException, KeyStoreException, 
			NoSuchAlgorithmException, CertificateException, 
			URISyntaxException, IOException{
		
		List<String> keys = new ArrayList<String>(){
			private static final long serialVersionUID = 1L;
			{
				add("loginUserDTO.user_name");
				add("userDTO.password");
				add("randCode");
			}
		};
		
		List<String> values = new ArrayList<String>(){
			private static final long serialVersionUID = 1L;
			{
				add("lihuifsr@126.com");
				add("19891013");
			}
		};
		
		values.add(randCode);
		
		HttpRes httpRes = new LogInService().LongIn(keys,values,cookieMap);
		
		HttpEntity entity = httpRes.getHttpEntity();
		if(entity != null){
			InputStream is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = "";
			String jsonData = "";
			while ((line = br.readLine()) != null) {
				jsonData += line;
			}
			return jsonData;
		}
		return "";
	}
	

}
