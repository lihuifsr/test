package com.h.mall.myprod.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

import com.h.mall.myprod.model.HttpRes;
import com.h.mall.myprod.util.HttpUtil;

public class FmxService {
	
	public HttpRes getRandomCode(String id) 
			throws URISyntaxException, KeyManagementException, 
					KeyStoreException, NoSuchAlgorithmException, 
					CertificateException, IOException{
		
		URI uri = new URIBuilder().setScheme("http")
				.setHost("dm.uletm.com")
				.setPath("/dmv2/ha!saveTest.do")
				.build();
		
		
		
		List<String> keys = new ArrayList<String>();
		keys.add("dmCaseId");
		keys.add("testStatus");
		keys.add("failureReason");
		keys.add("intro");

		
		List<String> values = new ArrayList<String>();
		
		values.add(id);
		values.add("27");
		values.add("47");
		values.add("项目有Bug不通过");
		
		String cookie="dmRoles=\"96,98,99,103,104,105,107,108,109,118,119,122,127,128,133,137,141,144,146,149,173,174,176,180,181,182,189,192,199,200,212,213,214,215,216,217,221,233,238,243,270,272,277,278,280,281,282,289,291,007004,007005,007008,007009\"; dmUserLoginName=shangsonglin; dmUserLoginNameCn=%E5%95%86%E6%9D%BE%E6%9E%97; dmPlatformType=platformHa; JSESSIONID=8F2A5DD85E49B9B163B769731BCDEFDA";
		HttpRes httpRes = HttpUtil.postSsl(uri, keys, values,null,cookie,false);
		
		HttpEntity entity = httpRes.getHttpEntity();
	
		if(entity != null){
			InputStream is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = "";
			String jsonData = "";
			while ((line = br.readLine()) != null) {
				jsonData += line;
			}
		}
		
		return httpRes;
	}

	
	public static void main(String[] args) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException, URISyntaxException, IOException, InterruptedException {
		
		String ids = "4639";
		
		System.out.println(ids.split("\\,").length);
		String[] idarr = ids.split("\\,");
		for (String id:idarr) {			
			FmxService f = new FmxService();
			f.getRandomCode(id);
			Thread.sleep(1000);
			System.out.println("执行完毕:"+id);
		}
		
		
	}
}
