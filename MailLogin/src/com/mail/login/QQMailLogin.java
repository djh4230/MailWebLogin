package com.mail.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class QQMailLogin {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			login();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public static void login() throws ClientProtocolException, IOException{
		HttpClient httpClient=new  DefaultHttpClient();
		
		HttpPost post=new HttpPost("https://mail.qq.com/cgi-bin/loginpage");
		List<NameValuePair> nvps=new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("user", "915924289"));
		nvps.add(new BasicNameValuePair("password", ""));
		post.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));
		HttpResponse response=httpClient.execute(post);
		int code=response.getStatusLine().getStatusCode();
		System.out.println(code);
		HttpEntity entity=response.getEntity();
		System.out.println(EntityUtils.toString(entity));
		//System.out.println(response.getAllHeaders()[0].getName());
		
		
	}

}
