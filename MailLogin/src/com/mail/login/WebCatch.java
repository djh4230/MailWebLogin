package com.mail.login;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.htmlparser.beans.StringBean;

public class WebCatch {

 public static void main(String[] args) {
  String url ="";
  List<String> list = new ArrayList<String>() ;
  
  for(int i=1;i<=10;i++){//178ҳ
   url = "http://www.19lou.com/forum-130-thread-26070777-"+i+"-1.html" ;
   try {
    // ��ȡ����
    //String htmlCode = "asd abc123@126.com f aaa12_34@163.com wo ppp.gma12345@qq.comil.com de�ҵ���153558891@qq.com  shi anan8397800@163.com ,  �ҵ���153558891@qq.com you xiangshi lizhihe@gmail.com adf" ;
    String htmlCode = WebCatch.getHTMl(url);
    WebCatch.getEmailQQ(htmlCode,list,i); //
//    DuoDuoGetMail.getEmail163(htmlCode,list,i);//
//    DuoDuoGetMail.getEmailG(htmlCode,list,i);//
//    DuoDuoGetMail.getEmail126(htmlCode,list,i);//
   } catch (Exception e) {
    e.printStackTrace();
   }
  }
  
  //��ʾ��������
//  for (String email:list){
//   System.out.println(email.trim());
//  }
  
 }
 
 //��¼19¥��ҳ
 @SuppressWarnings("deprecation")
 public static void login19lou() throws Exception {
  HttpClient httpclient = new DefaultHttpClient();
  HttpClient httpclient2 = new DefaultHttpClient();
  try {
   HttpPost httpost = new HttpPost("http://www.19lou.com/login");
   //��¼
   List<NameValuePair> nvps = new ArrayList<NameValuePair>();
   nvps.add(new BasicNameValuePair("userName", "aaaaa"));
   nvps.add(new BasicNameValuePair("userPass", "xxxxx"));
   
   httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
   HttpResponse response = httpclient.execute(httpost);
   HttpEntity entity = response.getEntity();
   System.out.println("state----------: " + response.getStatusLine());
   //System.out.println("response----------: " + response.toString());
   
   //����Ƿ��ض���
         int statuscode = response.getStatusLine().getStatusCode();
         if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) ||(statuscode == HttpStatus.SC_MOVED_PERMANENTLY) ||
             (statuscode == HttpStatus.SC_SEE_OTHER) ||(statuscode == HttpStatus.SC_TEMPORARY_REDIRECT))
         {
              //��ȡ�µ�URL��ַ
             Header header = response.getFirstHeader("location");
             if (header != null) {
//                 String newuri = header.getValue();
//                 if ((newuri == null) || (newuri.equals(""))){
//                  newuri = "http://www.19lou.com/forum-130-thread-26070777-1-1.html";
//                 }
                 String newuri = "http://www.19lou.com/forum-130-thread-26070777-2-1.html";
                 WebCatch.getHTMl(newuri);
             } else
                 System.out.println("----------- redirect  error ---");
         }
        
   EntityUtils.consume(entity);
  } finally {
   httpclient.getConnectionManager().shutdown();
   httpclient2.getConnectionManager().shutdown();
  }
 }
 
 //��ȡҳ��HTML����
 public static String getHTMl(String url) throws Exception {
  HttpClient httpclient = new DefaultHttpClient();
  HttpGet httpget = new HttpGet(url);
  //����ҳ�治����ʵץȡ��Ҫ��װieHTTPHeaders ���߲鿴headerͷ��Ϣ�����ã�һ����Ҫ��cookie����
  httpget.setHeader("Cookie", "Hm_lpvt_5185a335802fb72073721d2bb161cd94=1346897137986; __utmc=39183075; reg_kw=; dm_sid=CgEBXE8X1/6A+0jyAwNtAg==; __utma=39183075.167980057.1326962687.1346837110.1346897115.18; __utmz=39183075.1346826823.15.6.utmccn=(organic)|utmcsr=baidu|utmctr=19¥Ũ��С˵��̫��|utmcmd=organic; Hm_lvt_5185a335802fb72073721d2bb161cd94=1346835167463,1346837111887,1346837848826,1346897046549; login_pwd_tip_disable=true; BIGipServertopic_pool=1023475978.20480.0000");//����cookie
  
  HttpResponse response = httpclient.execute(httpget);
  HttpEntity entity = response.getEntity();
  String htmlCode = "" ;
  //System.out.println("----------------rul:----"+httpget.getURI());
  //System.out.println("---------------status:"+response.getStatusLine());
  if (entity != null) {
   //System.out.println("Response content length: "+ entity.getContentLength());
   // read html page
   InputStream is = entity.getContent();
   BufferedReader in = new BufferedReader(new InputStreamReader(is));
   StringBuffer buffer = new StringBuffer();
   String line = "";
   while ((line = in.readLine()) != null) {
    buffer.append(line + "\n");
   }
   // read end
   htmlCode = buffer.toString();
   //System.out.println(htmlCode);
  }
  httpget.abort();
  httpclient.getConnectionManager().shutdown();
        return htmlCode ;
 }
 
 //HTML��ǩ��������
 public static String getContentNoHtml(String url){
  StringBean sb = new StringBean();
  sb.setLinks(false);//���ý����ȥ������
  sb.setURL(url);//��Ҫ�˵���ҳ��ǩ��ҳ�� url
  System.out.println(sb.getStrings());
  return sb.getStrings();
 }
 
 //��ȡemailQQ
 public static List<String> getEmailQQ(String htmlCode, List<String> list, int i) {
  System.out.println("--------------��"+i+"ҳQQ����");
  if (htmlCode.indexOf("@qq.com") > -1) {
   Pattern pattern = Pattern.compile("\\d{5,10}\\@qq.com"); // ������ʽƥ���ʽ
   Matcher matcher = pattern.matcher(htmlCode);
   while (matcher.find()) {
    String srcStr = matcher.group(); // ����õ���mail
    System.out.println(srcStr);
    list.add(srcStr); //
   }
  }
  return list;
 }
 //��ȡemail163
 public static List<String> getEmail163(String htmlCode,List<String> list, int i){
  System.out.println("--------------��"+i+"ҳ163����");
  if (htmlCode.indexOf("@163.com") > -1) {
   //Pattern pattern = Pattern.compile("\\s*\\w+(?:\\.{0,1}[\\w-]+)*\\@163.com"); // ������ʽƥ���ʽ
   Pattern pattern = Pattern.compile("([\\w\\d-]+)*\\@163.com"); // ƥ��Ч�ʿ�

   Matcher matcher = pattern.matcher(htmlCode);
   while (matcher.find()) {
    String srcStr = matcher.group(); //
    System.out.println(srcStr);
    list.add(srcStr); //
   }
  }
  return list;
 }
 //��ȡemail126
 public static List<String> getEmail126(String htmlCode,List<String> list, int i){
  System.out.println("--------------��"+i+"ҳ126����");
  if (htmlCode.indexOf("@126.com") > -1) {
   Pattern pattern = Pattern.compile("\\s*\\w+(?:\\.{0,1}[\\w-]+)*\\@126.com"); // ������ʽƥ���ʽ
   Matcher matcher = pattern.matcher(htmlCode);
   while (matcher.find()) {
    String srcStr = matcher.group(); //
    System.out.println(srcStr);
    list.add(srcStr); //
   }
  }
  return list;
 }
 //��ȡmailG
 public static List<String> getEmailG(String htmlCode,List<String> list, int i){
  System.out.println("--------------��"+i+"ҳgmail����"); 
  if(htmlCode.indexOf("@gmail.com")>-1){
   Pattern pattern = Pattern.compile("\\s*\\w+(?:\\.{0,1}[\\w-]+)*\\@gmail.com"); // ������ʽƥ���ʽ
   Matcher matcher = pattern.matcher(htmlCode);
   while (matcher.find()) {
    String srcStr = matcher.group(); //
    System.out.println(srcStr);
    list.add(srcStr); //
   }
  }
  return list ;
 }
 
}
