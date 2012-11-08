package com.example.obligatorio.ui;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.example.obligatorio.adapters.ProductosAdaptador;
import com.example.obligatorio.base_de_datos.BaseDeDatos;
import com.example.obligatorio.dominio.Direccion;
import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.servicio.ListaPedido;
import com.example.obligatorio.servicio.ListaPedido.ProductoCantidad;
import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.servicio.WebServiceInteractionObtenerResultado;
import com.example.obligatorio.sistema.Sistema;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ActivityCrearLista extends Activity {

	private static final int MENU_TERMINAR = Menu.FIRST;
	private static final int MENU_VERLISTA = Menu.FIRST + 1;
	ListaPedido lp;
	private ArrayList<Producto> productos = new ArrayList<Producto>();
	public ProgressDialog dialog;

	public Handler responseHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productos);
		dialog = new ProgressDialog(this);
		lp = new ListaPedido();
		responseHandler = new Handler();

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

//			dialog.setMessage("Se estan bucando los datos...");
//			dialog.setTitle("Procesando");
//			dialog.setCancelable(false);
//			dialog.show();
//			final Thread thread = new Thread(new Runnable() {
//				public void run() {
//					WebServiceInteractionObtenerResultado.work();
//					responseHandler.sendEmptyMessage(0);
//				}
//			});
//
//			thread.start();
//
//			final Intent in = new Intent(this, ActivityResultado.class);
//			responseHandler = new Handler() {
//				public void handleMessage(Message msg) {
//					super.handleMessage(msg);
//					try {
//						dialog.dismiss();
//
//						startActivity(in);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			};
			final ProgressDialog pd = ProgressDialog.show(this,"Procesando","Se estan bucando los datos...",true, false);
			final Intent in = new Intent(this, ActivityResultado.class);
			new Thread(new Runnable(){
				public void run(){
					TraerEstablecimientos();
					startActivity(in);
				pd.dismiss();
				}

				private void TraerEstablecimientos() {
					ListaResultado listaRes;
					List<ListaResultado> res = null;

					try {
						HttpClient httpClient = new DefaultHttpClient();
						HttpPost post = new HttpPost(Sistema.URL_PEDIDO_RESULTADO);
						post.setHeader("Content-type", "application/json");
						Gson gson = new GsonBuilder()
								.excludeFieldsWithoutExposeAnnotation().create();

						ListaPedido lp = Sistema.getInstance().getListaPedActual();
						//Direccion dir = new Direccion();
						// /"latitud":,"longitud":
						//dir.setLatLong(-34.9079606, -56.157705);
						lp.setDir(Sistema.getInstance().getCurrentDir());
						StringEntity request = new StringEntity(new Gson().toJson(lp),
								HTTP.UTF_8);
						System.out.println("==================");
						System.out.println(new Gson().toJson(lp));
						System.out.println("==================");
						post.setEntity(request);

						HttpResponse resp = httpClient.execute(post);

						String respString = EntityUtils.toString(resp.getEntity());
						System.out.println("==================");
						System.out.println(respString);
						System.out.println("==================");
						Type type = new TypeToken<List<ListaResultado>>() {
						}.getType();
						res = new Gson().fromJson(respString, type);
						Sistema.getInstance().setListaResultados(res);
						System.out.println("==========>>>>Resutls Size "
								+ Sistema.getInstance().getListaResultados().size());
						System.out.println("==================END");
						
					} catch (Exception e) {
						Log.e("error", e.getMessage());
					}
				}
				}).start(); 
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