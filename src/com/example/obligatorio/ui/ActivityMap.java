package com.example.obligatorio.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

import com.example.obligatorio.adapters.BalloonOverlayView;
import com.example.obligatorio.dominio.Establecimiento;
import com.example.obligatorio.maps.LocalizacionActualOverlay;
import com.example.obligatorio.servicio.ListaPedido;
import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.sistema.Sistema;
import com.example.obligatorio.sistema.Util;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class ActivityMap extends MapActivity {
	private MapView mapView;
	private AlertDialog dialog = null;
	private Boolean yaGiro = Sistema.getInstance().getYaGiro();

	// private static ListaResultado lresSeleccionada;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_direccion_actual);

		yaGiro = !(yaGiro == null);
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
		mapView.getController().setZoom(17);

		// Setting Zoom Controls
		mapView.setBuiltInZoomControls(true);

		for (ListaResultado lr : Sistema.getInstance().getListaResultados()) {
			showEstablecimientoLocation(lr);
		}

		showHome();

		if (!yaGiro) {
			mapView.getController().animateTo(new GeoPoint(lat, lon));
		}

	}

	private void showHome() {
		Drawable drawable = getResources().getDrawable(R.drawable.home);
		LocalizacionActualOverlay locationOverlay = new LocalizacionActualOverlay(
				drawable, h, this);

		GeoPoint puntoCASA = new GeoPoint(Util.getIntDirFormDouble(Sistema
				.getInstance().getCurrentDir().getLatitud()),
				Util.getIntDirFormDouble(Sistema.getInstance().getCurrentDir()
						.getLongitud()));
		OverlayItem overlayItem = new OverlayItem(puntoCASA,
				"Dirección Actual", null);
		locationOverlay.agregarPuntos(overlayItem);
		mapView.getOverlays().add(locationOverlay);
		mapView.invalidate();
	}

	private void showEstablecimientoLocation(ListaResultado lr) {
		// Setting Zoom Controls
		mapView.setBuiltInZoomControls(true);

		// Getting Overlays of the map

		BalloonOverlayView bov = new BalloonOverlayView(this, 60);
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

	public void baloonClick(View v) {
		final ListaResultado lres = (ListaResultado) ((LinearLayout) v)
				.getTag();
		Sistema.getInstance().setListaResActual(lres);
		MostrarListaResultado(lres);
	}

	private void MostrarListaResultado(final ListaResultado lres) {
		final Intent abrir = new Intent(this, Principal.class);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String[] mensaje = new String[lres.getProductosPrecios().size() + 1];
		int i = 0;
		for (ListaResultado.ProductoCantidadPrecio pcp : lres
				.getProductosPrecios()) {
			mensaje[i] = pcp.getProdCantidad().getCantidad() + " "
					+ pcp.getProdCantidad().getProducto().GetNombre() + " $"
					+ pcp.getPrecioProducto();
			i++;
		}
		mensaje[i] = "Total: " + lres.getTotal();

		builder.setItems(mensaje, null).setTitle(lres.getEst().getNombre());
		builder.setPositiveButton("Terminar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"dd/MM/yyyy HH:mm:ss");
						lres.setFecha(dateFormat.format(
								Calendar.getInstance().getTime()).toString());
						//Sistema.getInstance().setListaResActual(lres);
						Sistema.getInstance().getBaseDeDatos()
								.addHistorialListaResultado(lres);
						Sistema.getInstance().setItemsChecked(new ArrayList<Integer>());
						Sistema.getInstance().setListaPedActual(new ListaPedido());
						startActivity(abrir);
					}
				});
		dialog = builder.create();
		dialog.show();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// cuando giro guardo todo
		super.onSaveInstanceState(outState);
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			outState.putBoolean("dialogMostrado", true);
		} else {
			outState.putBoolean("dialogMostrado", false);
		}
		outState.putInt("zoom", mapView.getZoomLevel());
		Sistema.getInstance().setYaGiro(true);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// cuando "termina de girar" restablesco todo
		super.onRestoreInstanceState(savedInstanceState);

		if (savedInstanceState.getBoolean("dialogMostrado")) {
			MostrarListaResultado(Sistema.getInstance().getListaResActual());
		}
		mapView.getController().setZoom(savedInstanceState.getInt("zoom"));
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Sistema.getInstance().setYaGiro(null);
	}

}
