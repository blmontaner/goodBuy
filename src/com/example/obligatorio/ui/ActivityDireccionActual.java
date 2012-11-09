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
import android.content.Context;
import android.graphics.drawable.Drawable;

public class ActivityDireccionActual extends MapActivity implements
		LocationListener {
	// http://wptrafficanalyzer.in/blog/add-marker-on-touched-location-using-google-map-in-android-example/
	private MapView mapView;
	// private TextView tvLocation;

	// Handles Taps on the Google Map
	Handler h = new Handler() {

		// Invoked by the method onTap()
		// in the class CurrentLocationOverlay
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();

			// Getting the Latitude of the location
			int latitude = data.getInt("latitude");

			// Getting the Longitude of the location
			int longitude = data.getInt("longitude");

			// Show the location in the Google Map
			showLocation(latitude, longitude);
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_direccion_actual);

		mapView = (MapView) findViewById(R.id.mapview);

		// map.getController().setCenter(getPoint(-34.903819, -56.190463));
		mapView.getController().setZoom(17);
		// map.setBuiltInZoomControls(true);

		// Getting LocationManager object from System Service LOCATION_SERVICE
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		// Creating a criteria object to retrieve provider
		Criteria criteria = new Criteria();

		// List<String> provedores = locationManager.getAllProviders();

		// Getting the name of the best provider
		String provider = locationManager.getBestProvider(criteria, true);

		

		if (provider != null) {// tengo gps
			// Getting Current Location
			Location location = locationManager.getLastKnownLocation(provider);

			if (location != null) {
				onLocationChanged(location); // muestra dir del gps
			} else {
				LoadLocation();
			}
			locationManager.requestLocationUpdates(provider, 20000, 0, this);
		} else {
			LoadLocation();
		}

	}

	private void LoadLocation() {
		BaseDeDatos db = Sistema.getInstance().getBaseDeDatos();
		if (db.isDireccionActualSeted()) {
			Direccion dir = db.getDireccionActual();
			showLocation( (int)(dir.getLatitud()* 1e6), (int)(dir.getLongitud()* 1e6));
		} else {
			int latitudeORT = -34903819;
			int longitudeORT = -56190463;
			showLocation(latitudeORT, longitudeORT);
		}
	}

	private void showLocation(int latitude, int longitude) {
		// Setting Zoom Controls
		mapView.setBuiltInZoomControls(true);

		// Getting Overlays of the map
		List<Overlay> overlays = mapView.getOverlays();

		// Getting Drawable object corresponding to a resource image
		//para los iconos http://mapicons.nicolasmollet.com/
		Drawable drawable = getResources().getDrawable(
				R.drawable.home);

		// Creating an ItemizedOverlay
		LocalizacionActualOverlay locationOverlay = new LocalizacionActualOverlay(
				drawable, h,this);

		// Getting the MapController
		MapController mc = mapView.getController();

		// Creating an instance of GeoPoint, to display in Google Map
		GeoPoint p = new GeoPoint(latitude, longitude);

		// Locating the point in the Google Map
		mc.animateTo(p);

		// Creating an OverlayItem to mark the point
		OverlayItem overlayItem = new OverlayItem(p, "Dirección Actual", null);

		// Adding the OverlayItem in the LocationOverlay
		locationOverlay.agregarPuntos(overlayItem);

		// Clearing the overlays
		overlays.clear();

		// Adding locationOverlay to the overlay
		overlays.add(locationOverlay);

		// Redraws the map
		mapView.invalidate();

		// esto se puede sacar porque demora mucho sino mepa...
		Context contexto = mapView.getContext();

		Geocoder geoCoder = new Geocoder(contexto, Locale.getDefault());
		try {
			List<Address> direcciones = geoCoder.getFromLocation(
					latitude / 1e6, longitude / 1e6, 1);

			String dir = "";
			if (direcciones.size() > 0) {
				for (int i = 0; i < direcciones.get(0).getMaxAddressLineIndex(); i++)
					dir += direcciones.get(0).getAddressLine(i) + "\n";

			}

			Toast.makeText(contexto, dir, Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Direccion dir = new Direccion();
		dir.setLatLong(latitude / 1e6, longitude / 1e6);
		Sistema.getInstance().setCurrentDir(dir);

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onLocationChanged(Location localizacion) {//es para el gps
		// TODO Auto-generated method stub
		showLocation((int) (localizacion.getLatitude() * 1E6),
				(int) (localizacion.getLongitude() * 1E6));
	//	Direccion dir = new Direccion();
	//	dir.setLatLong(localizacion.getLongitude(), localizacion.getLatitude());
	//	Sistema.getInstance().setCurrentDir(dir);
		// (int)(latitude * 1E6), (int)(longitude*1E6));
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
