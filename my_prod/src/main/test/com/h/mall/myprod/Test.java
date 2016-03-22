package com.h.mall.myprod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class Test {
	
//
//	keytool -import -alias "my server cert" -file server.cer -keystore my.truststore 
//
//	keytool -genkey -v -alias "my client key" -validity 365 -keystore my.keystore 
	
	@org.junit.Test
	public void testLogin() throws Exception{
		
		URI uri = new URIBuilder().setScheme("https").setHost("kyfw.12306.cn/otn").setPath("/login/loginAysnSuggest").build();
		
		KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
		
		FileInputStream instream = new FileInputStream(new File("D://cre//my.truststore"));
		
		trustStore.load(instream, "1234567".toCharArray());
		
		SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore).build();
		
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	    
		System.out.println(sslsf);
		
		BasicCookieStore cookieStore = new BasicCookieStore();
		
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).setSSLSocketFactory(sslsf).build();
		
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		
		HttpPost httpost = new HttpPost(uri);
		
		httpost.setConfig(requestConfig);
		
		httpost.setConfig(requestConfig);
		
		List keys = new ArrayList();
		List values = new ArrayList();
		keys.add("loginUserDTO.user_name");
		keys.add("userDTO.password");
		keys.add("randCode");
		values.add("lihuifsr@126.com");
		values.add("19891013");
		values.add("1234");
		
		if(keys.size() != 0){
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			for(int i = 0 ; i < keys.size() ; i++){
				nvps.add(new BasicNameValuePair(keys.get(i).toString(),values.get(i).toString() ));
			}
			httpost.setEntity(new UrlEncodedFormEntity(nvps,Consts.UTF_8));
		}
		
		CloseableHttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();
		
		System.out.println("--------------------------------------");
		
		Header[] heads = response.getAllHeaders();
		for (int i = 0; i < heads.length; i++) {
			System.out.println(i+"====="+heads[i].getName()+":"+heads[i].getValue()+"|");
		}
		
		
		System.out.println("--------------------------------------");
		
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
}
