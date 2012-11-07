package com.example.obligatorio.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.obligatorio.adapters.ProductosAdaptador;
import com.example.obligatorio.base_de_datos.BaseDeDatos;
import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.servicio.ListaPedido;
import com.example.obligatorio.servicio.ListaPedido.ProductoCantidad;
import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.servicio.WebServiceInteractionObtenerProductos;
import com.example.obligatorio.servicio.WebServiceInteractionObtenerResultado;
import com.example.obligatorio.sistema.Sistema;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

public class ActivityCrearLista extends Activity {

	private static final int MENU_TERMINAR = Menu.FIRST;
	private static final int MENU_VERLISTA = Menu.FIRST + 1;
	ListaPedido lp;
	private ArrayList<Producto> productos = new ArrayList<Producto>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productos);

		lp = new ListaPedido();

		BaseDeDatos base = Sistema.getInstance().getBaseDeDatos();
		productos = (ArrayList<Producto>) base.getAllProducts();

		final ProductosAdaptador adaptador = new ProductosAdaptador(this,
				productos);

		final ListView lstOpciones = (ListView) findViewById(R.id.listView1);

		lstOpciones.setAdapter(adaptador);

		lstOpciones.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View rowView,
					int index, long arg3) {

				if (productos.get(index).isEnListaActual()) {
					productos.get(index).setEnListaActual(false);

					lp.eliminarProducto(productos.get(index));
				} else {
					lp.getProductos().add(
							new ProductoCantidad(productos.get(index), 1));
					productos.get(index).setEnListaActual(true);
					// seleccionados.add(productos.get(index));

					Toast.makeText(rowView.getContext(), "Agregado",
							Toast.LENGTH_SHORT).show();

					// http://www.coderzheaven.com/2012/09/12/create-slide-left-animation-deleting-row-listview-android/
					Animation animation = AnimationUtils.loadAnimation(
							ActivityCrearLista.this,
							android.R.anim.slide_out_right);
					animation.setDuration(2500);
					rowView.startAnimation(animation);
				}
				adaptador.notifyDataSetChanged();
			}
		});

		EditText etFiltro = (EditText) this.findViewById(R.id.producto);
		etFiltro.addTextChangedListener(new TextWatcher() {
			// cuando cambia el // texot del // edittext se // ejecuta y filtro

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				lstOpciones.setTextFilterEnabled(true);

				adaptador.FiltrarProductos(s);
				// adaptador.getFilter().filter(s);

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() == 0) {
					lstOpciones.setTextFilterEnabled(false);

				}
			}
		});

	}

	// private Producto cursorToProducto(Cursor cursor) {
	// Producto pro = new Producto();
	// pro.setId(cursor.getInt(0));
	// pro.SetNombre(cursor.getString(1));
	// pro.SetMarca(cursor.getString(2));
	// pro.SetEspecificacion(cursor.getString(3));
	// return pro;
	//
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_VERLISTA, 0, "Ver Lista");
		menu.add(0, MENU_TERMINAR, 0, "Terminar");
		return true;
	}

	/* Handles item selections */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Sistema.getInstance().setListaPedActual(lp);
		final List<ListaResultado> ppp = new ArrayList<ListaResultado>();
		switch (item.getItemId()) {
		case MENU_VERLISTA:
			Intent abrir = new Intent(this, ActivityListaActual.class);
			// abrir.putExtra("direccion", et1.getText().toString());
			startActivity(abrir);

			// http://www.bogotobogo.com/Android/android10Menus.php
			return true;
		case MENU_TERMINAR:

			// final ProgressDialog pd = ProgressDialog.show(this, "Procesando",
			// "Se estan bucando los datos", true, false);
			// new Thread(new Runnable() {
			// public void run() {
			// try {
			// WebServiceInteractionObtenerResultado.work();
			// //
			// ().execute("https://kitchensink-nspace.rhcloud.com/rest/productos/catalogoProductos")).get();
			// } catch (Exception ex) {
			// Log.e("Ex", ex.getMessage());
			// }
			// pd.dismiss();
			// }
			// }).start();

			// WebServiceInteractionObtenerResultado.work(); //CON ESTA LINEA NO
			// FUNCIONA.
//			ProgressDialog dialog = new ProgressDialog(this);
//			dialog.setMessage("Se estan bucando los datos...");
//			dialog.setTitle("Procesando");
//			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//			dialog.setCancelable(false);

			WebServiceInteractionObtenerResultado thread = new WebServiceInteractionObtenerResultado();
//			thread.setDialog(dialog);
			thread.execute();

			try {
				Thread.sleep(1000 * 10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Intent res = new Intent(this, ActivityResultado.class);
			startActivity(res);

			return true;
		}
		return false;
	}

	public void Alfabeto(View v) {
		TextView textAlfabeto = (TextView) findViewById(v.getId());
		String letra = (String) textAlfabeto.getText();

		Toast toast = Toast.makeText(getApplicationContext(), letra,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);

		toast.show();

		ListView lstOpciones = (ListView) findViewById(R.id.listView1);
		lstOpciones.setFastScrollEnabled(true);

		int indexLetra = ((ProductosAdaptador) lstOpciones.getAdapter())
				.getIndiceLetra(letra);
		if (indexLetra != -1) {
			lstOpciones.setSelectionFromTop(indexLetra, 0);

		}

	}
}