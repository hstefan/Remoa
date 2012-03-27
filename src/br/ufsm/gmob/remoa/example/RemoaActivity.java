package br.ufsm.gmob.remoa.example;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import br.ufsm.gmob.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class RemoaActivity extends MapActivity {
	private MapView mMapView;
	private MapController mMapCtrl;
	
	private LocationManager mLocationManager;
	private LocationListener mLocationListener;
	
	private Drawable mDrawable;
	private CustomItemizedOverlay mItemizedOverlay;
	
	public static final int DEFAULT_ZOOM_LEVEL = 7;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mMapView = (MapView) findViewById(R.id.mapview);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setSatellite(true);
        mMapCtrl = mMapView.getController();
        mMapCtrl.setZoom(DEFAULT_ZOOM_LEVEL);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		mDrawable =  this.getResources().getDrawable(R.drawable.ic_launcher);
		mItemizedOverlay = new CustomItemizedOverlay(mDrawable);
		
		mLocationListener = new SampleLocationListener(mMapCtrl, mMapView);
	
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
    }
    
    public void addOverlayItem(int lat, int lon) {
    	GeoPoint gp = new GeoPoint(lat, lon);
    	OverlayItem item = new OverlayItem(gp, "Foo", "Bar");
    	
    	mItemizedOverlay.addOverlay(item);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}