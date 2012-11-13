package com.example.obligatorio.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.obligatorio.base_de_datos.BaseDeDatos;
import com.example.obligatorio.dominio.Direccion;
import com.example.obligatorio.dominio.Establecimiento;
import com.example.obligatorio.maps.LocalizacionActualOverlay;
import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.servicio.WebServiceInteraction;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityEstablecimiento extends MapActivity implements
		LocationListener {

	private MapView mapView;
	private Boolean yaGiro = Sistema.getInstance().getYaGiro();

	// Handles Taps on the Google Map
	Handler h = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_direccion_actual);

		yaGiro = !(yaGiro == null);

		mapView = (MapView) findViewById(R.id.mapview);

		int lon = 0;
		int lat = 0;

		List<Establecimiento> establecimientos = Sistema.getInstance()
				.getBaseDeDatos().getAllEstablecimientos();
		for (Establecimiento est : establecimientos) {

			showLocation(est);
		}

		int[] currentDir = Sistema.getInstance().getCurrentLocation();

		lat = currentDir[0];
		lon = currentDir[1];

		// 1 es todo el mapa , mas alto mas zoom
		mapView.getController().setZoom(16);

		// Setting Zoom Controls
		mapView.setBuiltInZoomControls(true);

		if (!yaGiro) {
			mapView.getController().animateTo(new GeoPoint(lat, lon));
		}

	}

	private void showLocation(Establecimiento est) {

		// Getting Overlays of the map
		List<Overlay> overlays = mapView.getOverlays();

		// Getting Drawable object corresponding to a resource image
		// para los iconos http://mapicons.nicolasmollet.com/
		Drawable drawable = getResources().getDrawable(R.drawable.supermarket);

		// Creating an ItemizedOverlay
		LocalizacionActualOverlay locationOverlay = new LocalizacionActualOverlay(
				drawable, h, this);

		// Creating an instance of GeoPoint, to display in Google Map
		GeoPoint p = new GeoPoint(
				(int) (est.getDireccion().getLatitud() * 1e6), (int) (est
						.getDireccion().getLongitud() * 1e6));

		// Creating an OverlayItem to mark the point
		OverlayItem overlayItem = new OverlayItem(p, est.getNombre(), est
				.getDireccion().getCalle());

		// Adding the OverlayItem in the LocationOverlay
		locationOverlay.agregarPuntos(overlayItem);

		// Clearing the overlays
		// overlays.clear();

		// Adding locationOverlay to the overlay
		overlays.add(locationOverlay);

		// Redraws the map
		mapView.invalidate();

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
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

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// cuando giro guardo todo
		super.onSaveInstanceState(outState);
		outState.putInt("zoom", mapView.getZoomLevel());
		Sistema.getInstance().setYaGiro(true);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// cuando "termina de girar" restablesco todo
		super.onRestoreInstanceState(savedInstanceState);
		mapView.getController().setZoom(savedInstanceState.getInt("zoom"));
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Sistema.getInstance().setYaGiro(null);
	}
}