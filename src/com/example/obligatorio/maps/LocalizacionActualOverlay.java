package com.example.obligatorio.maps;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class LocalizacionActualOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> puntos = new ArrayList<OverlayItem>();
	private Handler handler;
	private Context mContext;
	public LocalizacionActualOverlay(Drawable defaultMarker, Handler h,Context contexto) {
		super(boundCenterBottom(defaultMarker));
		// Handler object instantiated in the class MainActivity
		this.handler = h;
		this.mContext = contexto;
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return puntos.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return puntos.size();
	}

	public void agregarPuntos(OverlayItem punto) {
		puntos.add(punto);
		populate();// Invokes the method createItem()
	}

	// tap = click en el mapa
	@Override
	public boolean onTap(GeoPoint point, MapView map) {

		// try {

		// Creating a Message object to send to Handler
		Message message = new Message();

		// Creating a Bundle object to set in Message object
		Bundle data = new Bundle();

		// Setting latitude in Bundle object
		data.putInt("latitude", point.getLatitudeE6());

		// Setting longitude in the Bundle object
		data.putInt("longitude", point.getLongitudeE6());

		// Setting the Bundle object in the Message object
		message.setData(data);

		// Sending Message object to handler
		handler.sendMessage(message);

		return super.onTap(point, map);
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = puntos.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}

}
