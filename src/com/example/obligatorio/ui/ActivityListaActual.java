package com.example.obligatorio.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatorio.adapters.ProductosAdaptadorCantidad;
import com.example.obligatorio.servicio.ListaPedido.ProductoCantidad;
import com.example.obligatorio.servicio.WebServiceInteraction;
import com.example.obligatorio.sistema.Sistema;

public class ActivityListaActual extends Activity {

	// private ArrayList<Producto> productosSeleccionados;
	private ProductosAdaptadorCantidad adaptador;
	private static final int MENU_TERMINAR = Menu.FIRST;
	public ProgressDialog dialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actual);
		
		dialog = new ProgressDialog(this);
		
		adaptador = new ProductosAdaptadorCantidad(this, Sistema.getInstance()
				.getListaPedActual().getProductos(),R.layout.activity_actual_items);

		ListView lstOpciones = (ListView) findViewById(R.id.listaPorCantidad);

		lstOpciones.setAdapter(adaptador);

	}

	public void Suma(View v) {
		LinearLayout vwParentRow = (LinearLayout) v.getParent();
		TextView valor = (TextView) vwParentRow.getChildAt(1);
		int suma = Integer.parseInt(valor.getText().toString()) + 1;
		valor.setText(suma + "");

		ProductoCantidad pro = (ProductoCantidad) valor.getTag();
		pro.setCantidad(suma);

		// http://androidforbeginners.blogspot.com/2010/03/clicking-buttons-in-listview-row.html

	}

	public void Resta(View v) {
		LinearLayout vwParentRow = (LinearLayout) v.getParent();
		TextView valor = (TextView) vwParentRow.getChildAt(1);
		int resta = Integer.parseInt(valor.getText().toString());
		if (resta > 0) {
			resta--;
		}
		valor.setText(resta + "");

		ProductoCantidad pro = (ProductoCantidad) valor.getTag();

		if (resta == 0) {
			
			pro.getProducto().setEnListaActual(false);
			int pos = Sistema.getInstance().getListaPedActual()
					.eliminarProducto(pro.getProducto());
			//System.out.println(pos + "   posssss");
			Integer borreEL = Sistema.getInstance().getItemsChecked().remove(pos);
			//System.out.println(borreEL + "   borreEL");
			
			Toast.makeText(this, "Quitado", Toast.LENGTH_SHORT).show();
			adaptador.notifyDataSetChanged();
			
		} else {
			pro.setCantidad(resta);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_TERMINAR, 0, "Calcular");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_TERMINAR:
			if(Sistema.getInstance().getListaPedActual().getProductos().size()==0){
				Toast.makeText(this, "No tiene productos seleccionados en la lista", Toast.LENGTH_LONG).show();
			}else{
				final Intent in = new Intent(this, ActivityResultado.class);
				dialog.setMessage("Se estan bucando los datos...");
				dialog.setTitle("Procesando");
				dialog.setCancelable(false);
				dialog.show();
				final Thread thread = new Thread(new Runnable() {
					public void run() {
						WebServiceInteraction.buscarResultadosListaActual();
						dialog.dismiss();
						startActivity(in);
					}
				});

				thread.start();
			}
			return true;
		}
		return false;
	}

}