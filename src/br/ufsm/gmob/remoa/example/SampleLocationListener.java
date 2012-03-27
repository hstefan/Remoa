package br.ufsm.gmob.remoa.example;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class SampleLocationListener implements LocationListener {
	private MapController mMapCtrl;
	private MapView mMapView;

	public SampleLocationListener(MapController mMapCtrl, MapView mMapView) {
		super();
		this.mMapCtrl = mMapCtrl;
		this.mMapView = mMapView;
	}

	public void onLocationChanged(Location location) {
		double lat = location.getLatitude();
		double lon = location.getLongitude();
		
		GeoPoint point = new GeoPoint((int)(lat * 1e6), (int)(lon * 1e6));
		mMapCtrl.setCenter(point);
	}

	public void onProviderDisabled(String provider) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
