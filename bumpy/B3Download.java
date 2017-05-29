package bumpy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.LinkedList;
import javax.net.ssl.HttpsURLConnection;

/**
 * #################A finir : gestionnaire de proxy########"""""""""""""""""
 * 
 * Classe permettant de récupérer des données depuis le site internet. CELA EFFACE LES DONNEES DU SITE
 * Une seule méthode à utiliser : la méthode statique download qui renvoie une LinkedList de strings
 * correspondant chacune à un enregistrement
 * 
 * @see B3Download#download()
 * @see B3Download#downloadThroughProxy(String, int)
 * @author Benoît Gallouëdec
 *
 */
public class B3Download 
{
	/**
	 * Fonction à appeler en l'absence de proxy
	 * @return la liste des mesures
	 */
	public static LinkedList<String> download(Request request)
	{
		return downloadThroughProxy(null,-1,request);
	}
	
	/**
	 * Fonction à appeler en présence d'un proxy
	 * @param proxyName le nom du proxy
	 * @param proxyPort le port du proxy
	 * @return la liste des mesures
	 */
	public static LinkedList<String> downloadThroughProxy(String proxyName,int proxyPort,Request request)  
	{
		LinkedList<String> reponse=new LinkedList<String>();
		String data="";
		
		try{
			URL httpsUrl = new URL("https://pscsaveall.000webhostapp.com/getUntreated.php");
			switch(request){
			case GETUNTREATED:
				httpsUrl = new URL("https://pscsaveall.000webhostapp.com/getUntreated.php");
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
    }catch (Exception e) {e.printStackTrace();}
		return reponse;
	}
	
	public static void main(String[] args)
	{
		Request request=Request.GETRECORDFROMBUMP;
		LinkedList<String> a=downloadThroughProxy("kuzh.polytechnique.fr",8080,request); 
		for(String s:a)
			System.out.println(s);
	}
}

enum Request{GETUNTREATED,GETBUMPS,GETNBBYTYPE,GETRECORDFROMBUMP};
