package com.example.obligatorio.ui;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

import com.example.obligatorio.adapters.BalloonOverlayView;
import com.example.obligatorio.dominio.Establecimiento;
import com.example.obligatorio.maps.LocalizacionActualOverlay;
import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.sistema.Sistema;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class ActivityMap extends MapActivity {
	private static final int MENU_TERMINAR = Menu.FIRST;
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private MapView mapView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_direccion_actual);

		mapView = (MapView) findViewById(R.id.mapview);

		int[] currentDir = new int[2];
		Bundle bundle = getIntent().getExtras();

		if (bundle.getIntArray("latLong") != null) {
			currentDir = bundle.getIntArray("latLong");
		} else {
			currentDir = Sistema.getInstance().getCurrentLocation();
		}

		int lat = currentDir[0];
		int lon = currentDir[1];

		// 1 es todo el mapa , mas alto mas zoom
		mapView.getController().setZoom(16);

		// Setting Zoom Controls
		mapView.setBuiltInZoomControls(true);

		for (ListaResultado lr : Sistema.getInstance().getListaResultados()) {
			showEstablecimientoLocation(lr);
		}

		mapView.getController().animateTo(new GeoPoint(lat, lon));

	}

	private void showEstablecimientoLocation(ListaResultado lr) {
		// Setting Zoom Controls
		mapView.setBuiltInZoomControls(true);

		// Getting Overlays of the map
		List<Overlay> overlays = mapView.getOverlays();

		// Getting Drawable object corresponding to a resource image
		// para los iconos http://mapicons.nicolasmollet.com/
		Drawable drawable = getResources().getDrawable(R.drawable.home);

		BalloonOverlayView bov = new BalloonOverlayView(this, 55);
		bov.setData(lr);

		GeoPoint point = new GeoPoint(((int) (lr.getEst().getDireccion()
				.getLatitud() * 1E6)), ((int) (lr.getEst().getDireccion()
				.getLongitud() * 1E6)));
		MapView.LayoutParams params = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, point,
				MapView.LayoutParams.BOTTOM_CENTER);
		params.mode = MapView.LayoutParams.MODE_MAP;
		bov.setVisibility(View.VISIBLE);
		mapView.addView(bov, params);
		showLocation(lr.getEst());

	}

	Handler h = new Handler();

	private void showLocation(Establecimiento est) {

		// Getting Overlays of the map
		List<Overlay> overlays = mapView.getOverlays();

		// Getting Drawable object corresponding to a resource image
		// para los iconos http://mapicons.nicolasmollet.com/
		Drawable drawable = getResources().getDrawable(R.drawable.supermarket);

		// Creating an ItemizedOverlay
		LocalizacionActualOverlay locationOverlay = new LocalizacionActualOverlay(
				drawable, h, this);

		// Getting the MapController
		MapController mc = mapView.getController();

		// Creating an instance of GeoPoint, to display in Google Map
		GeoPoint p = new GeoPoint(
				(int) (est.getDireccion().getLatitud() * 1e6), (int) (est
						.getDireccion().getLongitud() * 1e6));

		// Creating an OverlayItem to mark the point
		OverlayItem overlayItem = new OverlayItem(p, est.getNombre(), est
				.getDireccion().getCalle());

		// Adding the OverlayItem in the LocationOverlay
		locationOverlay.agregarPuntos(overlayItem);

		// Adding locationOverlay to the overlay
		overlays.add(locationOverlay);

		// Redraws the map
		mapView.invalidate();

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_TERMINAR, 0, "Terminar");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case MENU_TERMINAR:
			System.out.println("terminar...");

			return true;
		}
		return false;
	}
}
