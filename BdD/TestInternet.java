package BdD;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;



public class TestInternet {

   public static void main(String[] args) 
   {
     try {
		post2("https://psc.000webhostapp.com/testfinal.php");
    	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 //System.out.println(getCurrentTimeStamp());
      
   }
   
   public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
   
   public static void post2(String adress) throws IOException
   {
	   
	   LinkedList<String> keys=new LinkedList<String>();
	   LinkedList<String> values=new LinkedList<String>();
	   keys.add("date");
	   keys.add("datas");
	   values.add("2016-09-02 00:01:00");
	   values.add("01-02-2017"+"#"+"3.5"+"#"+"201.1"+"#"+Recup.envoi_internet()+System.lineSeparator());
	   String result = "";
	   OutputStreamWriter writer = null;
	   BufferedReader reader = null;
	   try {
		   //encodage des paramètres de la requête
		   String data="";
		   for(int i=0;i<keys.size();i++)
		   {
			   data +=URLEncoder.encode(keys.get(i), "UTF-8")+"="+URLEncoder.encode(values.get(i), "UTF-8")+"&";
		   }
		   if(data.length()>0){
               data = data.substring(0, data.length()-1);
           }
		   String s;
           System.out.println(data);
		   //création de la connection
	   InetSocketAddress proxyInet = new InetSocketAddress("kuzh.polytechnique.fr",8080);
	   
	   Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyInet);
	   String url="https://psc.000webhostapp.com/testfinal.php";
	   URL httpsUrl = new URL(url);
	   HttpsURLConnection httpsCon = (HttpsURLConnection) httpsUrl.openConnection(proxy);
	   
	   httpsCon.setDoOutput(true);
	   httpsCon.setDoInput(true);
	   httpsCon.setRequestMethod("POST");
	   OutputStream out = httpsCon.getOutputStream();
	   OutputStreamWriter owriter = new OutputStreamWriter(out);
	  
	   owriter.write(data);
	   owriter.flush();
	   reader = new BufferedReader(new InputStreamReader(httpsCon.getInputStream()));
	   
	   
	   String ligne;
	   String resulto="";
	   while ((ligne = reader.readLine()) != null) {
	   result+=ligne;
	   }
	   System.out.println(result);
	   owriter.close();
	   reader.close();
	   }catch (Exception e) {
	   e.printStackTrace();
	   
	   }
   }
}