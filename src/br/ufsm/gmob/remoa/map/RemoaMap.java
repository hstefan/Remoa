package br.ufsm.gmob.remoa.map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import br.ufsm.gmob.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class RemoaMap extends MapActivity {

	private MapView mMapView;
	private MapController mMapController;
	private LocationManager mLocationManager;
	private RemoaMapLocationListener mLocationListener;
	
	private EventItemizedOverlay mItemizedOverlay;
	
	public static final int DEFAULT_ZOOM_LEVEL = 18;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_remoa_map);
				
		bindGUIElements();
		initGUIElements();
		
		mItemizedOverlay = new EventItemizedOverlay(getResources().getDrawable(R.drawable.pin),mMapView);
		
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mLocationListener = new RemoaMapLocationListener();
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
		
		
		
        if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        	showDialog(R.string.notification, R.string.warn_using_qrcode_location);
    		IntentIntegrator integrator = new IntentIntegrator(RemoaMap.this);
        	integrator.initiateScan();
        } else {
        	try {
        		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        	} catch (Exception e) {
        		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        	}
        }

        int lat = (int)(-29.703598 * 1e6);
        int lon = (int)(-53.841241 * 1e6);
        addOverlayItem(lat, lon);
        mMapView.getOverlays().add(mItemizedOverlay);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	
	private void bindGUIElements() {
		mMapView = (MapView) findViewById(R.id.mapview_remoa);
	}
	
	private void initGUIElements() {
        mMapView.setBuiltInZoomControls(true);
        mMapView.setSatellite(true);
        mMapController = mMapView.getController();
        mMapController.setZoom(DEFAULT_ZOOM_LEVEL);
        /* 
         * casa do bruno
         * (para teste) 
         */
        int lat = (int)(-29.703598 * 1e6);
        int lon = (int)(-53.841241 * 1e6);
        mMapController.setCenter(new GeoPoint(lat,lon));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if(scanResult != null) {
			String content = scanResult.getContents();
			if(content != null) {
				String[] geopoints = content.split(",",2);
				if(geopoints.length == 2) {	
					try {
						int lat = Integer.parseInt(geopoints[0]);
						int lon = Integer.parseInt(geopoints[1]);
						mMapController.setCenter(new GeoPoint(lat,lon));
					} catch (Exception e) {
						showDialog(R.string.error, R.string.no_barcode_error);
					}
				}
			} else {
				showDialog(R.string.error, R.string.invalid_qrcode_msg);				
			}
		}
	}
	
	private void showDialog(int title, int message) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(title);
	    builder.setMessage(message);
	    builder.setPositiveButton("OK", null);
	    builder.show();
	}
	
	public void addOverlayItem(int lat, int lon) {
    	GeoPoint gp = new GeoPoint(lat, lon);
    	OverlayItem item = new OverlayItem(gp, "Este aqui é apenas um teste", "Isso fica pequeno mesmo?");
    	
    	mItemizedOverlay.addOverlay(item);
    	mMapController.animateTo(gp);
    }
}
