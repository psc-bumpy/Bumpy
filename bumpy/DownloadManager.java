package bumpy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import javax.net.ssl.HttpsURLConnection;

/**
 * La classe DownloadManager gere l'interaction entre le logiciel et la base de donnees.
 * Elle permet de :
 * 		-> récupérer des données brutes depuis la table MESURES.
 * 		-> envoyer les donnees traitées à la table OBSTACLES.
 * 		-> récupérer les données de la table OBSTACLES pour les afficher.
 * 		-> gérer l'interaction avec le proxy éventuel.
 * 		
 * 	Chaque requête est envoyée grâce à la méthode download, prenant en paramètre la requête sous la forme d'une enum.
 * 	Le résultat est toujours une LinkedList<String>, dont la structure dépend de la requête.
 * 	Une requête consiste à interroger une page php du site, qui elle se charge de générer la requête SQL à la bdd. Il y a une page par requête.
 */
public class DownloadManager{
	public enum Request{GETUNTREATED,GETBUMPS,GETALL,GETSTATE,GETNBBYTYPE,GETRECORDFROMBUMP,GETDATESFORBUMP};
	private boolean isProxyActive;
	private int proxyPort;
	private String proxyHost;
	
	public DownloadManager(){
		isProxyActive=false;
		proxyHost="";
	}
	public DownloadManager(boolean b,int i,String s){
		isProxyActive=b;
		proxyHost=s;
		proxyPort=i;
	}
	
	public LinkedList<String> download(Request request) throws IOException{
		if(isProxyActive)
			return downloadThroughProxy(proxyHost,proxyPort,request);
		else
			return downloadThroughProxy(null,-1,request);
	}
	
	/**
	 * Fonction à appeler en présence d'un proxy
	 * @param proxyName le nom du proxy
	 * @param proxyPort le port du proxy
	 * @return la liste des mesures
	 */
	public static LinkedList<String> downloadThroughProxy(String proxyName,int proxyPort,Request request) throws IOException
	{
		LinkedList<String> reponse=new LinkedList<String>();
		String data="";
		try{
			URL httpsUrl = new URL("https://pscsaveall.000webhostapp.com/getUntreated.php");
			switch(request){
			case GETUNTREATED:
				httpsUrl = new URL("https://pscsaveall.000webhostapp.com/getUntreated.php");
				break;
			case GETSTATE:
				httpsUrl = new URL("https://pscsaveall.000webhostapp.com/getState.php");
				break;
			case GETALL:
				httpsUrl = new URL("https://pscsaveall.000webhostapp.com/getAll.php");
				break;
			case GETBUMPS:
				httpsUrl = new URL("https://pscsaveall.000webhostapp.com/getBumps.php");
				break;
			case GETNBBYTYPE:
				httpsUrl = new URL("https://pscsaveall.000webhostapp.com/getNbByType.php");
				break;
			case GETRECORDFROMBUMP:
				String latitude="k";
				String date="0000-00-00 00:00:00";
				data+="?latitude="+latitude+"&date="+date;
				httpsUrl = new URL("https://pscsaveall.000webhostapp.com/getRecordFromBump.php"+data);
				break;
			default:
				break;
			}
		//Envoi de la requête
			HttpsURLConnection httpsCon;
			if(proxyName!=null)
			{
				InetSocketAddress proxyInet = new InetSocketAddress(proxyName,proxyPort);

		        Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyInet);
		        httpsCon = (HttpsURLConnection) httpsUrl.openConnection(proxy);
			}
			else
				httpsCon = (HttpsURLConnection) httpsUrl.openConnection();
			
			httpsCon.setDoOutput(true);
			httpsCon.setDoInput(true);
			httpsCon.setRequestMethod("GET");
			OutputStream out = httpsCon.getOutputStream();
			OutputStreamWriter owriter = new OutputStreamWriter(out);
			owriter.flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpsCon.getInputStream()));

			//lecture de la réponse
			String ligne;
			while ((ligne = reader.readLine()) != null) {
				while(ligne!=null && ligne.length()>0 && ligne.charAt(0)==' ')					//Sur la première ligne d'une requête, il y a automatiquement des espaces, donc je les vire
					ligne=ligne.substring(1);
			if(ligne.length()>0)
				reponse.add(ligne);
        }
        owriter.close();
        reader.close();
    }catch (IOException e) {throw e;}
		return reponse;
	}
	public void setProxy(String s,int i){proxyHost=s;proxyPort=i;}
	public void setProxyActive(boolean b){isProxyActive=b;}
	public String getProxyHost(){return proxyHost;}
	public int getProxyPort(){return proxyPort;}
	public boolean isProxyActive(){return isProxyActive;}
	public void connect(){
		if(isProxyActive){
			System.setProperty("https.proxyHost",proxyHost);
			System.setProperty("https.proxyPort",String.valueOf(proxyPort));}
		else{
			System.setProperty("https.proxyHost","localhost");
			System.setProperty("https.proxyPort","8080");}
	}
}


