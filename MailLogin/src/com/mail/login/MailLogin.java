package com.mail.login;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;


import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.sun.mail.imap.IMAPMessage;

public class MailLogin {
	private static String USERNAME = "djh4230";
	private static String PASSWORD = "djh423016djh";
	private String LOGINURL = "http://reg.163.com/login.jsp?type=1&product=mail126&url=http://entry.mail.126.com/cgi/ntesdoor?hid%3D10010102%26lightweight%3D1%26language%3D0%26style%3D-1";
	private InputStream response = null;
	private FileOutputStream fileoutput = null;
	private BufferedInputStream bufferinput = null;
	private BufferedOutputStream bufferoutput = null;
	
	private static String pop3Server = "pop.qq.com";
	//private static String pop3Server = "pop3.126.com";
	//private static String pop3Server = "imap.126.com";
	//private static String pop3Server = "pop3.163.com";
	private static String protocol = "pop3";
	//private static String protocol = "pop3";
	//private static String protocol="imap";
	//private static String protocol = "pop3";
	private static String user = "915924289";
	private static String pwd = "djh423016";
	

	public static void main(String[] args) throws MessagingException,
			IOException {
		Store store;
		MailLogin mailLogin=new MailLogin();
		mailLogin.loginQQ("", "", user,pwd);
		/*
		 * try { login(); } catch (HttpException e) { e.printStackTrace(); }
		 */
		/*String key = "月度账单";
		
		store=mailLogin.login126(pop3Server, protocol, user, pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);

		// 获得邮件夹Folder内的所有邮件Message对象
		Message[] messages = folder.getMessages();
		

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(new File(
						"C:\\djh\\juno\\MailLogin\\src\\mail.txt"))));

		int i = 0;
		String subject;
		for (Message message : messages) {
			i++;
			try {
				subject = message.getSubject();
			} catch (Exception e) {
				System.out.println(i);
				e.printStackTrace();
				continue;
			}

			if (!subject.contains(key)) {
				continue;
			}

			String from = (message.getFrom()[0]).toString();
			from = MimeUtility.decodeText(from);
			System.out.println("第 " + (i + 1) + "封邮件的主题：" + subject);
			System.out.println("第 " + (i + 1) + "封邮件的发件人地址：" + from);

			InputStreamReader isr = new InputStreamReader(
					message.getInputStream(), "utf-8");
			BufferedReader br = new BufferedReader(isr);

			String str;
			while ((str = br.readLine()) != null) {
				bw.write(str);
				bw.write("\n");
			}
		}
		folder.close(false);
		store.close();*/
		
	}
	public void loginQQ(String protocolServer, String protocol,
			String user, String pwd) throws MessagingException {
		// 创建一个有具体连接信息的Properties对象
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		/*Properties props = new Properties();
		props.setProperty("mail.store.protocol", protocol);
		props.setProperty("mail."+protocol+".host", protocolServer);
		if(protocol.equals("imap")){
			props.setProperty("mail.imap.port", "143"); 
		}*/
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		
		Properties props = System.getProperties();  
		  props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);  
		//  props.setProperty("mail.imap.socketFactory.fallback", "false");  
//		     props.setProperty("mail.imap.port", port);  
		   //  props.setProperty("mail.imap.socketFactory.port", "143");  
		     
		    
		   props.setProperty("mail.store.protocol","imap");    
		   props.setProperty("mail.imap.host", "imap.qq.com");    
		   props.setProperty("mail.imap.port", "993");    
		   props.setProperty("mail.imap.auth.login.disable", "true");  
		   Session session = Session.getDefaultInstance(props,null);  
		  session.setDebug(true);  
		  Store store = session.getStore("imap");     
		  store.connect("imap.qq.com", user, pwd);
		  Folder inbox = null;  
		  try {  
		     
		  inbox = store.getFolder("Inbox");  
		  inbox.open(Folder.READ_ONLY);  
		  FetchProfile profile = new FetchProfile();  
		  profile.add(FetchProfile.Item.ENVELOPE);  
		  Message[] messages = inbox.getMessages();  
		  inbox.fetch(messages, profile);  
		  System.out.println("收件箱的邮件数：" + messages.length);  
		    
		  IMAPMessage msg;  
		  for (int i = 0; i < messages.length; i++) {  
		   msg = (IMAPMessage)messages[i];  
		   String from = null;
		try {
			from = MimeUtility.decodeText(msg.getFrom()[0].toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
		   InternetAddress ia = new InternetAddress(from);  
		   System.out.println("FROM:" + ia.getPersonal()+'('+ia.getAddress()+')');  
		   System.out.println("TITLE:" + msg.getSubject());  
		   System.out.println("SIZE:" + msg.getSize());  
		   System.out.println("DATE:" + msg.getSentDate());  
		    Enumeration headers = msg.getAllHeaders();     
		             System.out.println("----------------------allHeaders-----------------------------");     
		          while (headers.hasMoreElements()) {     
		                 Header header = (Header)headers.nextElement();    
		                 System.out.println(header.getName()+" ======= "+header.getValue());    
		             }   
		    
		  }
		  } finally {  
		   try {  
		    inbox.close(false);  
		   } catch (Exception e) {  
		   }  
		   try {  
		    store.close();  
		   } catch (Exception e) {  
		   }  
		  }  
		 }

	public Store login126(String protocolServer, String protocol,
			String user, String pwd) throws MessagingException {
		// 创建一个有具体连接信息的Properties对象
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", protocol);
		props.setProperty("mail."+protocol+".host", protocolServer);
		if(protocol.equals("imap")){
			props.setProperty("mail.imap.port", "143"); 
		}
		// 使用Properties对象获得Session对象
		Session session = Session.getInstance(props);
		session.setDebug(false);

		// 利用Session对象获得Store对象，并连接pop3服务器
		Store store = session.getStore();
		store.connect(protocolServer, user, pwd);

		// 获得邮箱内的邮件夹Folder对象，以"只读"打开

		return store;
	}

	public static void login() throws HttpException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost authpost = new HttpPost("http://www.126.com");
		// PostMethod authpost = new PostMethod(LOGINURL);
		// 注意上面的地址！
		authpost.setHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// authpost.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		NameValuePair user = new BasicNameValuePair("user", USERNAME);
		NameValuePair pwd = new BasicNameValuePair("password", PASSWORD);
		NameValuePair username = new BasicNameValuePair("username", USERNAME
				+ "@126.com");
		nvps.add(user);
		nvps.add(pwd);
		authpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		// authpost.setRequestBody(new NameValuePair[] { user, pwd, username });
		HttpResponse response = httpClient.execute(authpost);
		// System.out.println(authpost.getResponseBodyAsString());
		// System.out.println(status);
		int status = response.getStatusLine().getStatusCode();
		try {
			if (status == HttpStatus.SC_OK) {

				System.out.println("登录成功!");
			}
			// 缓冲输入输出.
			/*
			 * response = authpost.getResponseBodyAsStream(); bufferinput = new
			 * BufferedInputStream(response); fileoutput = new
			 * FileOutputStream(new
			 * File("C:\\djh\\juno\\MailLogin\\src\\mail1.txt"));
			 * bufferoutput=new BufferedOutputStream(fileoutput); byte[] temp =
			 * new byte[1024]; int len=0; while ((len = bufferinput.read(temp))
			 * > 0) { bufferoutput.write(temp, 0, len); }
			 */
		} finally {
			// 关闭输入输出流.
			/*
			 * if (response != null) response.close(); if (fileoutput != null)
			 * fileoutput.close();
			 */
		}

	}
}
