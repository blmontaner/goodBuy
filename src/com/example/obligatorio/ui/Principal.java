package com.example.obligatorio.ui;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.example.obligatorio.base_de_datos.BaseDeDatos;
import com.example.obligatorio.dominio.Establecimiento;
import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.servicio.ListaPedido;
import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.servicio.WebServiceInteraction;
import com.example.obligatorio.servicio.WebServiceInteractionObtenerProductos;
import com.example.obligatorio.sistema.Sistema;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Principal extends Activity implements OnClickListener {

	// private SQLiteDatabase baseDatos;
	// //private static final String TAG = "bdGoodBuy";
	// private static final String nombreBD = "GoodBuy";
	// private static final String tablaProductos = "productos";
	//
	// private static final String crearTablaProductos =
	// "create table if not exists "
	// + " producto (id integer primary key"// autoincrement, "
	// +
	// " nombre text not null, marca text not null , especificacion text not null);";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_layout);
		final Button btn_crearlista = (Button) findViewById(R.id.btn_create_list);
		Button btn_chklista = (Button) findViewById(R.id.btn_check_list);
		Button btn_elegirestab = (Button) findViewById(R.id.btn_store);
		Button btn_historial = (Button) findViewById(R.id.btn_history);
		Button btn_map = (Button) findViewById(R.id.btn_map);
		btn_crearlista.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				// Launching News Feed Screen
				btn_crearlista
						.setBackgroundResource(R.drawable.create_list_hover);
				Intent i = new Intent(getApplicationContext(),
						ActivityCrearLista.class);
				startActivity(i);
			}
		});

		// Listening Friends button click
		btn_chklista.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(),
						ActivityListaActual.class);
				startActivity(i);
			}
		});

		// Listening Messages button click
		btn_elegirestab.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(),
						ActivityEstablecimiento.class);
				startActivity(i);
			}
		});

		// Listening to Places button click
		btn_map.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(),
						ActivityDireccionActual.class);
				startActivity(i);
			}
		});

		// Listening to Events button click
		btn_historial.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(),
						ActivityHistorial.class);
				startActivity(i);
			}
		});

		// ////////BASE DE DATOS
		Sistema.getInstance().setContextoBaseDeDatos(this); // esto solo lo hago
															// una vez en el
															// codigo.
		final BaseDeDatos base = Sistema.getInstance().getBaseDeDatos();
		ArrayList<Producto> productos = new ArrayList<Producto>();
		if (base.getProductCount() == 0) {
			try {
				productos = (new WebServiceInteractionObtenerProductos()
						.execute("https://kitchensink-nspace.rhcloud.com/rest/productos/catalogoProductos"))
						.get();

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			for (Producto pro : productos) {
				base.addProducto(pro);
				// insertarFila(pro);
			}
		}
//no borrar!!!
//		// lo de arriba lo voy a cambiar despues
//		// final ArrayList<Establecimiento> establecimientos = new
//		// ArrayList<Establecimiento>();
//		final ProgressDialog pd = ProgressDialog.show(this, "Procesando",
//				"Se estan bucando los datos...", true, false);
//		// final Intent in = new Intent(this, ActivityResultado.class);
//		if (base.getEstablecimientoCount() == 0) {
//			new Thread(new Runnable() {
//				public void run() {
//					for (Establecimiento est : WebServiceInteraction
//							.ObtenerEstablecimientos()) {
//						base.addEstablecimiento(est);
//					}
//					// startActivity(in);
//					pd.dismiss();
//				}
//
//			}).start();
//		}
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	}
}
