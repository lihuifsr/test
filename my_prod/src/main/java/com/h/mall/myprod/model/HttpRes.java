package com.h.mall.myprod.model;

import java.util.Arrays;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

public class HttpRes {
	
	public HttpRes() {}
	
	public HttpRes(CloseableHttpResponse response,HttpEntity httpEntity,Header[] heads) {
		
		this.response = response;
		this.httpEntity = httpEntity;
		this.heads = heads;
	}
	
	private CloseableHttpResponse response;
	
	private HttpEntity httpEntity;
	
	private Header[] heads;

	public HttpEntity getHttpEntity() {
		return httpEntity;
	}

	public void setHttpEntity(HttpEntity httpEntity) {
		this.httpEntity = httpEntity;
	}

	public Header[] getHeads() {
		return heads;
	}

	public void setHeads(Header[] heads) {
		this.heads = heads;
	}
	
	public CloseableHttpResponse getResponse() {
		return response;
	}

	public void setResponse(CloseableHttpResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "HttpRes [response=" + response + ", httpEntity=" + httpEntity
				+ ", heads=" + Arrays.toString(heads) + "]";
	}
	
}
