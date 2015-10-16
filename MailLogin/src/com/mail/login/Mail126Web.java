package com.mail.login;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class Mail126Web {

	private String USERNAME = "djh4230";

	private String PASSWORD = "XXXXXXXXXXXX";

	private static String LOGINURL = "http://reg.163.com/login.jsp?type=1&product=mail126&url=http://entry.mail.126.com/cgi/ntesdoor?hid%3D10010102%26lightweight%3D1%26language%3D0%26style%3D-1";
    //private static String LOGINURL="http://mail.126.com";
	private FileOutputStream fileoutput = null;

	private BufferedInputStream bufferinput = null;

	private BufferedOutputStream bufferoutput = null;

	public static void main(String[] args) {
		Mail126Web webclient = new Mail126Web();
		
		/*try { webclient.login(); } catch (HttpException e) {
			e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }*/
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", "djh4230");
		map.put("password", "");
		//map.put("username", "djh4230@126.com");
		map.put("url2", "http://mail.126.com/errorpage/error126.htm");
		map.put("savelogin", "0");
		try {
			String c = doPost(LOGINURL, map);
			System.out.println(c);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void login() throws HttpException, IOException {

		HttpClient httpClient = new DefaultHttpClient();

		// PostMethod authpost = new PostMethod("http://www.126.com");

		HttpPost authpost = new HttpPost(LOGINURL);

		// 注意上面的地址！

		authpost.addHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");

		List<NameValuePair> nvlps = new ArrayList<NameValuePair>();
		NameValuePair user = new BasicNameValuePair("username", USERNAME);
		nvlps.add(user);
		NameValuePair pwd = new BasicNameValuePair("password", PASSWORD);
		nvlps.add(pwd);
		nvlps.add(new BasicNameValuePair("url2", "http://mail.126.com/errorpage/error126.htm"));
		nvlps.add(new BasicNameValuePair("savelogin", "0"));
		//NameValuePair username = new BasicNameValuePair("username", USERNAME+ "@126.com");
		//nvlps.add(username);

		authpost.setEntity(new UrlEncodedFormEntity(nvlps, HTTP.UTF_8));
		// authpost.setRequestBody(new NameValuePair[] { user, pwd, username });

		HttpResponse response = httpClient.execute(authpost);

		// System.out.println(authpost.getResponseBodyAsString());

		// System.out.println(status);

		try {

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				System.out.println("登录成功!");

			}
			String content = EntityUtils.toString(response.getEntity(),HTTP.UTF_8);
			/*int index1 = content.indexOf("window.location.replace(\"");
			int index2 = content.indexOf("\");//remain for popo");
			String url2 = content.substring(index1 + 25, index2);*/
			System.out.println(content);
			/*for (Header header : response.getAllHeaders()) {
				System.out.println(header.getName() + ":" + header.getValue());
			}
			HttpPost post2 = new HttpPost(url2);
			for (Header header : response.getAllHeaders()) {
				if (header.getName().equals("Transfer-Encoding"))
					continue;
				// post2.addHeader(header);
			}
			HttpResponse response2 = httpClient.execute(post2);

			System.out
					.println("------------------response2---------------------------");
			System.out.println(" ");

			if (response2.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				System.out.println("登录成功!");

			}
			System.out.println(EntityUtils.toString(response2.getEntity()));
			System.out.println(" ");
			for (Header header : response.getAllHeaders()) {

				System.out.println(header.getName() + ":" + header.getValue());
			}*/

		} finally {

		}
	}

	public static String doPost(String url, HashMap<String, String> hashMap)
			throws IOException {
		String content = "";
		BasicCookieStore cookieStore = new BasicCookieStore();
		CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
			public CookieSpec create(HttpContext context) {

				return new BrowserCompatSpec() {
					@Override
					public void validate(Cookie cookie, CookieOrigin origin)
							throws MalformedCookieException {
						// Oh, I am easy
					}
				};
			}

		};
		Registry<CookieSpecProvider> r = RegistryBuilder
				.<CookieSpecProvider> create()
				.register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
				.register(CookieSpecs.BROWSER_COMPATIBILITY,
						new BrowserCompatSpecFactory())
				.register("easy", easySpecProvider).build();

		RequestConfig requestConfig = RequestConfig.custom()
				.setCookieSpec("easy").setSocketTimeout(10000)
				.setConnectTimeout(10000).build();

		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieSpecRegistry(r)
				.setDefaultRequestConfig(requestConfig)
				.setDefaultCookieStore(cookieStore).build();
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (hashMap != null) {
			Iterator<String> it = hashMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = hashMap.get(key);
				nvps.add(new BasicNameValuePair(key, value));
			}
		}
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate");
		httpPost.setHeader("Accept-Language", "en-US,en;q=0.5");
		httpPost.setHeader("Cache-Control", "max-age=0");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		httpPost.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:28.0) Gecko/20100101 Firefox/28.0");
		// 如果参数是中文，需要进行转码
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);

			HttpEntity entity = response.getEntity();
			for (Header s : response.getAllHeaders()) {
				System.out.println("post header====" + s);
			}
			InputStream is = entity.getContent();
			BufferedReader in = new BufferedReader(new InputStreamReader(is,
					Consts.UTF_8));
			String line = "";
			while ((line = in.readLine()) != null) {
				content += line;
			}
			String sid=null;
			List<Cookie> cookies = cookieStore.getCookies();
			if (cookies.isEmpty()) {
				System.out.println("None");
			} else {
				// 读取Cookie
				for (int i = 0; i < cookies.size(); i++) {
					if(cookies.get(i).getName().equals("SID")){
						sid=cookies.get(i).getValue();
					}
					System.out.println(cookies.get(i).getName()+":"+cookies.get(i).getValue());
				}
			}
			//System.out.println(content);
			
			HttpGet get = new HttpGet("http://mail.126.com/js6/main.jsp?sid="+sid+"&df=mail126_letter");
			HttpResponse response2=httpclient.execute(get);
			int code=response2.getStatusLine().getStatusCode();
			System.out.println(EntityUtils.toString(response2.getEntity(),HTTP.UTF_8));
			/*int index1 = content.indexOf("window.location.replace(\"");
			int index2 = content.indexOf("\");//remain for popo");
			String url2 = content.substring(index1 + 25, index2);
			System.out.println(content);
			for (Header header : response.getAllHeaders()) {
				System.out.println(header.getName() + ":" + header.getValue());
			}
			HttpGet post2 = new HttpGet(url2);
			for (Header header : response.getAllHeaders()) {
				if (header.getName().equals("Transfer-Encoding"))
					continue;
				// post2.addHeader(header);
			}
			HttpResponse response2 = httpclient.execute(post2);

			System.out
					.println("------------------response2---------------------------");
			System.out.println(" ");

			if (response2.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				System.out.println("登录成功!");

			}
			System.out.println(EntityUtils.toString(response2.getEntity()));
			System.out.println(" ");
			for (Header header : response.getAllHeaders()) {

				System.out.println(header.getName() + ":" + header.getValue());
			}*/
		} finally {
			if (response != null)
				response.close();
		}
		return content;
	}

}
