package com.psc.bumpy;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;



public class WebManager {

    static Context context;


    public static void sendDatas(double longitude, double latitude)
    {
        File F = new File (Environment.getExternalStorageDirectory() +
                File.separator + "Enregistrements");
        File[] files = F.listFiles();
        try{
            int compteur=0;
            if (files != null) {
                for (File f : files) {
                    String data = "";
                    InputStream flux = new FileInputStream(f);
                    InputStreamReader lecture = new InputStreamReader(flux);
                    BufferedReader buff = new BufferedReader(lecture);
                    String line;
                    while ((line = buff.readLine()) != null) {
                        data += line;
                    }
                    buff.close();
                    post(data,longitude, latitude);
                    compteur++;
                    Toast.makeText(context,"Envoi..."+(compteur/files.length*100)+"%", Toast.LENGTH_LONG).show();
                }
                for (File f : files)
                    f.delete();
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void post(String datas, double longitude, double latitude) throws IOException
    {

        LinkedList<String> keys=new LinkedList();
        LinkedList<String> values=new LinkedList();
        keys.add("date");
        keys.add("data");
        keys.add("longitude");
        keys.add("latitude");
        values.add(getCurrentTimeStamp());
        values.add(datas);
        values.add(""+longitude);
        values.add(""+latitude);
        String result = "";
        OutputStreamWriter writer = null;
        BufferedReader reader = null;
        try {
            //encodage des paramètres de la requête
            String data="";
            for(int i=0;i<keys.size();i++)
            {
                data += URLEncoder.encode(keys.get(i), "UTF-8")+"="+URLEncoder.encode(values.get(i), "UTF-8")+"&";
            }
            if(data.length()>0){
                data = data.substring(0, data.length()-1);
            }
            //création de la connection
            //InetSocketAddress proxyInet = new InetSocketAddress("kuzh.polytechnique.fr",8080);

            //Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyInet);
            URL httpsUrl = new URL("https://pscsaveall.000webhostapp.com/addData.php");
            HttpsURLConnection httpsCon = (HttpsURLConnection) httpsUrl.openConnection(/*proxy*/);

            httpsCon.setDoOutput(true);
            httpsCon.setDoInput(true);
            httpsCon.setRequestMethod("POST");
            OutputStream out = httpsCon.getOutputStream();
            OutputStreamWriter owriter = new OutputStreamWriter(out);

            owriter.write(data);
            owriter.flush();
            reader = new BufferedReader(new InputStreamReader(httpsCon.getInputStream()));


            String ligne;
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

    static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}


