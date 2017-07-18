package com.psc.bumpy;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class GPS {
    String providerName;
    LocationManager locationManager;
    GPS(LocationManager lM)
    {
        providerName=getProviderName(lM);
        locationManager=lM;
        try {
            lM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }

                @Override
                public void onLocationChanged(Location location) {
                }
            });
        }catch(SecurityException e){}

    }

    float getSpeed() throws SecurityException
    {
        if(locationManager.getLastKnownLocation(providerName)!=null)
            return locationManager.getLastKnownLocation(providerName).getSpeed();
        else return 0;
    }
    public double getLongitude() throws SecurityException
    {
        if(locationManager.getLastKnownLocation(providerName)!=null)
            return locationManager.getLastKnownLocation(providerName).getLongitude();
        else return 0;
    }
    public double getLatitude() throws SecurityException
    {
        if(locationManager.getLastKnownLocation(providerName)!=null)
            return locationManager.getLastKnownLocation(providerName).getLatitude();
        else return 0;
    }
    String getProviderName(LocationManager lM) {
        Criteria criteria = new Criteria();
        //criteria.setPowerRequirement(Criteria.POWER_LOW);                                         // Chose your desired power consumption level.
        criteria.setAccuracy(Criteria.ACCURACY_FINE);                                               // Choose your accuracy requirement.
        criteria.setSpeedRequired(true);                                                            // Choose if speed for first location fix is required.
        criteria.setAltitudeRequired(false);                                                        // Choose if you use altitude.
        criteria.setBearingRequired(false);                                                         // Choose if you use bearing.
        criteria.setCostAllowed(false);                                                             // Choose if this provider can waste money

        // Provide your criteria and flag enabledOnly that tells
        // LocationManager only to return active providers.
        return lM.getBestProvider(criteria, false);
    }
}
