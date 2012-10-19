package com.example.obligatorio.maps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class LocalizacionActualOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> puntos = new ArrayList<OverlayItem>();
	private Handler handler;

	public LocalizacionActualOverlay(Drawable defaultMarker, Handler h) {
		super(boundCenterBottom(defaultMarker));
		// Handler object instantiated in the class MainActivity
		this.handler = h;
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

//		try {

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

//			Context contexto = map.getContext();
//
//			Geocoder geoCoder = new Geocoder(contexto, Locale.getDefault());
//			List<Address> direcciones = geoCoder.getFromLocation(
//					point.getLatitudeE6() / 1E6, point.getLongitudeE6() / 1E6,
//					1);
//
//			String dir = "";
//			if (direcciones.size() > 0) {
//				for (int i = 0; i < direcciones.get(0).getMaxAddressLineIndex(); i++)
//					dir += direcciones.get(0).getAddressLine(i) + "\n";
//			}
//
//			Toast.makeText(contexto, dir, Toast.LENGTH_SHORT).show();

//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		return super.onTap(point, map);
	}

}
