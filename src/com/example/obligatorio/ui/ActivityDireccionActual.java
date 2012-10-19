package com.example.obligatorio.ui;

import com.example.obligatorio.maps.DireccionItemizedOverlay;
import com.example.obligatorio.maps.DireccionOverlaySelect;
import com.example.obligatorio.maps.DireccionOverlaySelect.OnSelectPOIListener;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

import android.os.Bundle;
import android.view.MotionEvent;
import android.app.Activity;
import android.graphics.drawable.Drawable;

public class ActivityDireccionActual extends MapActivity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_direccion_actual);

		MapView map = (MapView) findViewById(R.id.mapview);

		map.getController().setCenter(getPoint(-34.903819, -56.190463));
		// -34.903819,-56.190463  ORT
		map.getController().setZoom(17);
		map.setBuiltInZoomControls(true);
	
		final Drawable drawable = this.getResources().getDrawable(R.drawable.ic_action_search);
		
		DireccionOverlaySelect direccionActual = new DireccionOverlaySelect();
		direccionActual.setOnSelectPOIListener(new OnSelectPOIListener() {   
			 public void onSelectPOI(int latitud, int longitud) {
			  //Crear marcador, guardar en base de datos ...
				 
				 DireccionItemizedOverlay itemzedOverlay = new DireccionItemizedOverlay(drawable);
				 OverlayItem punto = new OverlayItem(getPoint(latitud, longitud), "comentario1", "comentario2");
				 itemzedOverlay.agregarPuntos(punto);
			 }
			});
//		
	}

	private GeoPoint getPoint(double lat, double lon) {
		return (new GeoPoint((int) (lat * 1000000.0), (int) (lon * 1000000.0)));
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
