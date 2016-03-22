package com.h.mall.myprod;

import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpDemoTest {

	public static void main(String[] args) throws Exception{
		URI uri = new URIBuilder()
	    .setScheme("http")
	    .setHost("www.google.com")
	    .setPath("/search")
	    .setParameter("q", "httpclient")
	    .setParameter("btnG", "Google Search")
	    .setParameter("aq", "f")
	    .setParameter("oq", "")
	    .build();
	    HttpGet httpget = new HttpGet(uri);
	    System.out.println(httpget.getURI());
	}

	@Test
	public void testRequestHead(){
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
	    response.addHeader("Set-Cookie", "c1=a; path=/; domain=yeetrack.com");
	    response.addHeader("Set-Cookie", "c2=b; path=\"/\", c3=c; domain=\"yeetrack.com\"");
	    Header h1 = response.getFirstHeader("Set-Cookie");
	    System.out.println(h1);
	    Header h2 = response.getLastHeader("Set-Cookie");
	    System.out.println(h2);
	    Header[] hs = response.getHeaders("Set-Cookie");
	    System.out.println(hs.length);
	}


	@Test
	public void test() throws Exception{
		StringEntity myEntity = new StringEntity("important message", ContentType.create("text/plain", "UTF-8"));
	    System.out.println(myEntity.getContentType());
	    System.out.println(myEntity.getContentLength());
	    System.out.println(EntityUtils.toString(myEntity));
	    System.out.println(EntityUtils.toByteArray(myEntity).length);
	}
}
