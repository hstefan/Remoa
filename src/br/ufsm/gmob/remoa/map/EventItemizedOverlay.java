package br.ufsm.gmob.remoa.map;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;

public class EventItemizedOverlay extends BalloonItemizedOverlay<OverlayItem> {
	private List<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	
	public EventItemizedOverlay(Drawable defaultMarker, MapView mapView) {
		super(boundCenterBottom(defaultMarker), mapView);
		mContext = mapView.getContext();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	@Override
	protected boolean onBalloonTap(int index, OverlayItem item) {
		if(index >= 0 && index < mOverlays.size()) {
			OverlayItem oi = mOverlays.get(index);
			Toast.makeText(mContext, oi.getTitle()+"\n\n"+oi.getSnippet(), 
					Toast.LENGTH_LONG).show();
		}
		return true;
	}
	
	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
}
