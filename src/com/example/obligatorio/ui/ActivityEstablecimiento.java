package com.example.obligatorio.ui;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.obligatorio.base_de_datos.BaseDeDatos;
import com.example.obligatorio.dominio.Direccion;
import com.example.obligatorio.maps.LocalizacionActualOverlay;
import com.example.obligatorio.sistema.Sistema;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;

public class ActivityEstablecimiento extends MapActivity implements
		LocationListener {

		private MapView mapView;


		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_direccion_actual);

			mapView = (MapView) findViewById(R.id.mapview);

			
			BaseDeDatos db = Sistema.getInstance().getBaseDeDatos();
			int lon = 0;
			int lat = 0;
			if (db.isDireccionActualSeted()) {
				Direccion dir = db.getDireccionActual();
				lat = (int)(dir.getLatitud()* 1e6);
						lon =  (int)(dir.getLongitud()* 1e6);
			} else {
				lat = -34903819;//ORT
				lon = -56190463;
			}
			
			mapView.getController().setCenter(new GeoPoint(lat,lon));
			mapView.getController().setZoom(13);//1 es todo el mapa , mas alto mas zoom
			// map.setBuiltInZoomControls(true);

			
		}

	

		@Override
		protected boolean isRouteDisplayed() {
			return false;
		}

		public void onLocationChanged(Location localizacion) {//es para el gps
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

	

}