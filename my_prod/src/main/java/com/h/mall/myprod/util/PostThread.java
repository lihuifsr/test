package com.h.mall.myprod.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

public class PostThread extends Thread{
	
	private final CloseableHttpClient httpClient;
    private final HttpContext context;
    private final HttpPost httppost;

    public PostThread(CloseableHttpClient httpClient, HttpPost httpPost) {
        this.httpClient = httpClient;
        this.context = HttpClientContext.create();
        this.httppost = httpPost;
    }

    @Override
    public void run() {
        try {
            CloseableHttpResponse response = httpClient.execute(
            		httppost);
            try {
                HttpEntity entity = response.getEntity();
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
                
            } finally {
                response.close();
            }
        } catch (ClientProtocolException ex) {
            // Handle protocol errors
        	
        } catch (IOException ex) {
            // Handle I/O errors
        }
    }

}
