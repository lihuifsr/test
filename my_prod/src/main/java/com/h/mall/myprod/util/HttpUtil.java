package com.h.mall.myprod.util;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import com.h.mall.myprod.model.HttpRes;

public class HttpUtil {
	
//	private static CloseableHttpClient httpclient = null;
	
	private static CloseableHttpClient httpclient = null;
	
	private static BasicCookieStore cookieStore =null;
	
	private static CloseableHttpClient getHttpclient(SSLConnectionSocketFactory sslsf) {
		if(httpclient == null){
			
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();  
//			// Increase max total connection to 200  
			cm.setMaxTotal(200);  
//			// Increase default max connection per route to 20  
			cm.setDefaultMaxPerRoute(20);  
			// Increase max connections for localhost:80 to 50  
			HttpHost localhost = new HttpHost("127.0.0.1", 8087);  
			cm.setMaxPerRoute(new HttpRoute(localhost), 50); 
			
			httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).setSSLSocketFactory(sslsf).setConnectionManager(cm)
					.build();

			
			
		}
		return httpclient;
	}
	
	public static SSLConnectionSocketFactory initSsl() 
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException{
		
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		
//		FileInputStream instream = new FileInputStream(new File("D://cre//jassecacerts"));
//		
//		trustStore.load(instream, "123456".toCharArray());
		
		SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore).build();
		
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		
		return sslsf;
	}
	
	public static HttpPost getHttpPost(URI uri,List<String> keys,List<String> values,Map<String,String> cookieMap,String cookies,boolean ispoxy){
		
		HttpPost httpPost = new HttpPost(uri);
		
//		RequestConfig requestConfig = RequestConfig.custom().
//				setSocketTimeout(2000).
//				setConnectTimeout(2000).build();
		
		Builder b = RequestConfig.custom().
				setSocketTimeout(2000).
				setConnectTimeout(2000);
		//HttpHost proxy = new HttpHost("127.0.0.1:8087"); 
		if(ispoxy){			
			HttpHost proxy = new HttpHost("127.0.0.1", 8087, "http");
			b.setProxy(proxy);
		}
		
		RequestConfig requestConfig = b.build();
		
		
		httpPost.setConfig(requestConfig);
		
		
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
		for(int i = 0 ; i < keys.size() ; i++){
			nvps.add(new BasicNameValuePair(keys.get(i),values.get(i)));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps,Consts.UTF_8));
		
		
		if(cookieMap!=null){
			
			StringBuffer cookie = new StringBuffer();
			for (Entry<String, String> entry:cookieMap.entrySet()) {
				cookie.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
			}
			
			if(cookie.length() > 0){
				httpPost.setHeader("Cookie", cookie.substring(0, cookie.length()-1));
			}
		}
		
		
//		httpPost.setHeader("BIGipServerotn", "2580807946.64545.0000");
		httpPost.setHeader("Cookie", cookies);
//		httpPost.setHeader("Cookie", "Hm_lvt_3d143f0a07b6487f65609d8411e5464f=1390464892; Hm_lpvt_3d143f0a07b6487f65609d8411e5464f=1390465488");

//		System.out.println("--------------登录请求头信息开始------------");
//		for (Header header:httpPost.getAllHeaders()) {
//			System.out.println("["+header.getName()+":"+header.getValue()+"]");
//		}
//		System.out.println("--------------登录请求头信息结束------------");
		
		
		return httpPost;
	}
	
	private static HttpGet getHttpGet(URI uri){
		
		HttpGet httpGet = new HttpGet(uri);
		RequestConfig requestConfig = RequestConfig.custom().
				setSocketTimeout(20000).
				setConnectTimeout(20000).build();
		

		httpGet.setConfig(requestConfig);
		
		
		
//		httpGet.setHeader("JSESSIONID", "A3A9B5904A7FA816199B1ADA2F77894E");
//		httpGet.setHeader("BIGipServerotn", "2580807946.64545.0000");
//		httpGet.setHeader("Cookie", "JSESSIONID=5BF6F411858C6816A4121FB3B065FC5E; BIGipServerotn=1524171018.38945.0000");
//		httpPost.setHeader("Cookie", "a=123");

		
//		System.out.println("--------------获取验证码请求头信息开始------------");
//		for (Header header:httpGet.getAllHeaders()) {
//			System.out.println("["+header.getName()+":"+header.getValue()+"]");
//		}
//		System.out.println("--------------获取验证码请求头信息结束------------");
		
		return httpGet;
		
		
	}
	
	public static HttpRes postSsl(URI uri,List<String> keys,List<String> values,Map<String,String> cookieMap,String cookies,boolean ispoxy) 
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, 
					CertificateException, IOException{
		
		CloseableHttpClient closeableHttpClient = getHttpclient(initSsl());
		
		HttpPost httpPost = getHttpPost(uri,keys,values,cookieMap,cookies,ispoxy);
		//System.out.println("URI:"+httpPost.getRequestLine());
		CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
		
		
//		System.out.println("--------------登录响应头信息开始------------");
//		for (Header header:response.getAllHeaders()) {
//			System.out.println("["+header.getName()+":"+header.getValue()+"]");
//		}
//		System.out.println("--------------登录响应头信息结束------------");
		
		return new HttpRes(response,response.getEntity(),response.getAllHeaders());
	}
	
	public static HttpRes getSsl(URI uri) 
				throws KeyManagementException, KeyStoreException, 
						NoSuchAlgorithmException, CertificateException, IOException{
		
		CloseableHttpClient closeableHttpClient = getHttpclient(initSsl());
		
		HttpGet httpPost = getHttpGet(uri);
		
		CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
		
//		System.out.println("--------------获取验证码响应头信息开始------------");
//		for (Header header:response.getAllHeaders()) {
//			System.out.println("["+header.getName()+":"+header.getValue()+"]");
//		}
//		System.out.println("--------------获取验证码响应头信息结束------------");
		
		return new HttpRes(response,response.getEntity(),response.getAllHeaders());
	}
	

}
