package bumpy.ui;

import data.Bump;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import bumpy.Manager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.LinkedList;

public class Map implements MapComponentInitializedListener {

	public GoogleMapView mapView;
	private GoogleMap map;
	boolean initialized;
	Manager manager;
	Collection<Bump> bumps;
	private GeocodingService geocodingService;
	public Map(Collection<Bump> b,Manager m){
		//Create the JavaFX component and set this as a listener so we know when 
		//the map has been initialized, at which point we can then begin manipulating it.
		manager=m;
		mapView = new GoogleMapView();
		mapView.addMapInializedListener(this);
		System.out.println("Loading map...");
		bumps=b;
		initialized=false;
    }

	public void setMarkers(Collection<Bump> bumps)
	{
		if(bumps==null)
			return;
		for(Bump b:bumps)
			setBumpMarker(b);
	}
	@Override
	public void mapInitialized() {
		//Set the initial properties of the map.
		setGeocodingService(new GeocodingService());
		MapOptions mapOptions = new MapOptions();
		initialized=true;
		mapOptions.center(new LatLong(48.7080662,2.20942332))
            .mapType(MapTypeIdEnum.ROADMAP)
            .overviewMapControl(false)
            .panControl(false)
            .rotateControl(false)
            .scaleControl(false)
            .streetViewControl(false)
            .zoomControl(false)
            .zoom(12);
		setMap(mapView.createMap(mapOptions));
		Collection<Bump> bumps=manager.bumps.values();
		LinkedList<Bump> test=new LinkedList<Bump>();
		for(Bump bump:bumps){
			setBumpMarker(bump);
			test.add(bump);
		}
		findBumpsLocation( test);
		manager.graphicManager.tabPane.carte.setContent(mapView);
	}
	public void findBumpsLocation(LinkedList<Bump> test){
		if(test==null||test.size()==0){
			manager.graphicManager.tabPane.refreshAccelTab(null);
			return;
		}
		Bump b=test.pop();
		getGeocodingService().reverseGeocode(b.getLatitude(),b.getLongitude(),(GeocodingResult[] results, GeocoderStatus status) -> {
			if( status == GeocoderStatus.ZERO_RESULTS) {
				b.setLocation("Lat:"+b.getLatitude()+" Lon:"+b.getLongitude());
				findBumpsLocation(test);
            return;} 
        else {
        	b.setLocation(results[0].getFormattedAddress());
        	setBumpMarker(b);
        	System.out.println(test.size());
        	findBumpsLocation(test);
		}
			});}
	private void setBumpMarker(Bump bump)
	{	
		Marker marker;
		MarkerOptions markerOptions = new MarkerOptions();
		LatLong markerLatLong2 = new LatLong(bump.getLatitude(),bump.getLongitude());
		markerOptions.position(markerLatLong2)
	               .title(bump.getType()+System.lineSeparator()+"Nombre de passages : "+bump.getNbPassages()+System.lineSeparator()+bump.getLocation())
	               .visible(true);
		marker = new Marker(markerOptions);
		getMap().addMarker(marker);
		}
	public GeocodingService getGeocodingService() {
		return geocodingService;
	}

	public void setGeocodingService(GeocodingService geocodingService) {
		this.geocodingService = geocodingService;
	}

	public GoogleMap getMap() {
		return map;
	}

	public void setMap(GoogleMap map) {
		this.map = map;
	}
	/**
	 * Cette classe permet de vérifier si l'ordinateur est connecté à internet.
	 * @return true si l'ordinateur est connecté, false sinon.
	 */
	static boolean netIsAvailable() {
	    try {
	        final URL url = new URL("http://www.google.com");
	        final URLConnection conn = url.openConnection();
	        conn.connect();
	        return true;
	    } catch (MalformedURLException e) {
	        throw new RuntimeException(e);
	    } catch (IOException e) {
	        return false;
	    }
	}
}