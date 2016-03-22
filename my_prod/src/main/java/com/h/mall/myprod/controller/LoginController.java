package com.h.mall.myprod.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.h.mall.myprod.model.HttpRes;
import com.h.mall.myprod.service.LogInService;

@Controller
@RequestMapping(value = "/") 
public class LoginController {
	
	@RequestMapping(value="rc")
	public void getRandomCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HttpRes httpRes = new LogInService().getRandomCode();
		InputStream in =httpRes.getResponse().getEntity().getContent();
		byte data[] = readInputStream(in);
		in.close();
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        os.write(data);
        os.flush();    
        os.close();
	}

	
	private static byte[] readInputStream(InputStream inStream) throws Exception{    
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();    
        byte[] buffer = new byte[2048];    
        int len = 0;    
        while( (len=inStream.read(buffer)) != -1 ){    
            outStream.write(buffer, 0, len);    
        }
        outStream.flush();
        outStream.close();
        return outStream.toByteArray();    
	}
	
	@RequestMapping(value="tologin")
	public String toLogin(){
		return "login";	
	}

	@RequestMapping(value="login")
	public @ResponseBody String login(String randCode) 
			throws KeyManagementException, KeyStoreException, 
					NoSuchAlgorithmException, CertificateException, 
					URISyntaxException, IOException{
		
		List<String> keys = new ArrayList<String>();
		keys.add("loginUserDTO.user_name");
		keys.add("userDTO.password");
		keys.add("randCode");
		
		List<String> values = new ArrayList<String>();
		
		values.add("lihuifsr@126.com");
		values.add("19891013");
		values.add(randCode);
		
		HttpRes httpRes = new LogInService().LongIn(keys, values,new HashMap<String, String>());
		
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
			return jsonData;
		}
		return "";
	}
	
	@RequestMapping("existUser")
	public @ResponseBody String userLogin() 
			throws KeyManagementException, KeyStoreException, 
					NoSuchAlgorithmException, CertificateException, 
					URISyntaxException, IOException{
		
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
			return jsonData;
		}
		return "";
	}
	
	public static void main(String[] args) {
		
		
		for (int i = 0; i < 1000; i++) {
			if(i%3==1&&i%5==1&i%6==1){
				System.out.println(i);
			}
		}
		
	}

}
