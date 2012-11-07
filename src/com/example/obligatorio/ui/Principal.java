package com.example.obligatorio.ui;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.obligatorio.base_de_datos.BaseDeDatos;
import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.servicio.WebServiceInteractionObtenerProductos;
import com.example.obligatorio.sistema.Sistema;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		Sistema.getInstance().setContextoBaseDeDatos(this); //esto solo lo hago una vez en el codigo.
		BaseDeDatos base = Sistema.getInstance().getBaseDeDatos();
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
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}
	// Procedimiento para abrir la base de datos
	// si no existe se creará, también se creará la tabla
	// private void abrirBasedatos()
	// {
	// try
	// {
	// baseDatos = openOrCreateDatabase(nombreBD, MODE_WORLD_WRITEABLE, null);
	// baseDatos.execSQL(crearTablaProductos);
	// }
	// catch (Exception e)
	// {
	// //Log.i(TAG, "Error al abrir o crear la base de datos" + e);
	// }
	// }
	// //Método que realiza la inserción de los datos en la tabla productos
	// private boolean insertarFila(Producto producto)
	// {
	// ContentValues values = new ContentValues();
	// values.put("id",producto.getId() );
	// values.put("nombre",producto.GetNombre() );
	// values.put("marca",producto.GetMarca());
	// values.put("especificacion",producto.GetEspecificacion());
	// return (baseDatos.insert(tablaProductos, null, values) > 0);
	// }

}
