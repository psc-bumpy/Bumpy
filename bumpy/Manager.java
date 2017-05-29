package bumpy;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import bumpy.DownloadManager.Request;
import bumpy.ui.GraphicManager;
import bumpy.ui.Map;
import data.Bump;
import javafx.stage.Stage;

public class Manager {	
	public GraphicManager graphicManager;
	private Map map;
	public DownloadManager downloadManager;
	public HashMap<Double,Bump> bumps;
	
	public Manager(Stage stage){
		boolean test=true;
		if(!test)
			Bump.loadStats();
	    downloadManager=new DownloadManager();
	    downloadManager.setProxy("kuzh.polytechnique.fr", 8080);
	    downloadManager.setProxyActive(true);
	    downloadManager.connect();
		//loadBumps(true);
	    bumps=Bump.loadBumps();
		Collection<Bump> b;
		if(bumps!=null) b=bumps.values();
		else b=null;
	    map=new Map(b, this);
		preLoading(stage);
	}

	void preLoading(Stage stage){
			graphicManager=new GraphicManager(stage,this);
	}

	void loadBumps(boolean offline){
		/*if(offline){
		bumps = new HashMap<Double,Bump>();
		Bump a=new Bump(48.717111, 2.218653,"Barre",7);
		Bump d=new Bump(48.710139, 2.218819,"Coussin",7);
		bumps.put(a.getLatitude(),a);
		bumps.put(d.getLatitude(), d);
		}
		else{
			LinkedList<String> data;
			String info;
			try{data=downloadManager.download(Request.GETBUMPS);}
			catch(IOException f){data=null;info="Téléchargement impossible";}
		}*/
		
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
}
