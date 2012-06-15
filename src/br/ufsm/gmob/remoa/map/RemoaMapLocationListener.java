package br.ufsm.gmob.remoa.map;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;

public class RemoaMapLocationListener implements LocationListener {

	private Location mLocation;
	
	public void RemoaMapLocationListener() {
		mLocation = null;
	}
	
	public void onLocationChanged(Location location) {
		mLocation = location;
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	public Location getCurrentLocation() {
		return mLocation;
	}
	public GeoPoint getCurrentGeoPoint() {
		double lat = 0;
		double lon = 0;
		if(mLocation != null) {
			lat = mLocation.getLatitude()*1e6;
			lon = mLocation.getLongitude()*1e6;
		}
		return new GeoPoint((int)lat,(int)lon);
	}
	
}
