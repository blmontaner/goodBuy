package com.example.obligatorio.ui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.obligatorio.servicio.ListaResultado;
import com.example.obligatorio.sistema.Sistema;
import com.example.obligatorio.sistema.Util;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class ActivityHistorial extends Activity {

	private int indexAux;
	private AlertDialog dialog = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial);

		ArrayList<String> historial = new ArrayList<String>();

		final List<ListaResultado> histo = Sistema.getInstance().getHistorial();
		for (ListaResultado lres : histo) {
			historial.add(lres.getFecha() + "   $" + lres.getTotal());
		}

		ListView lstHistorial = (ListView) findViewById(R.id.listHistorial);

		lstHistorial.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, historial));
		// simple_list_item_multiple_choice

		lstHistorial.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View rowView,
					int index, long arg3) {

				indexAux = index;
				mostrarPedidoPrecios(histo.get(index));
			}
		});

	}

	public void mostrarPedidoPrecios(final ListaResultado lres) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// final Intent abrir = new Intent(this, ActivityMap.class);
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

		dialog = builder.create();
		dialog.show();

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// cuando giro guardo todo
		super.onSaveInstanceState(outState);
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			outState.putInt("LRESINDEX", indexAux);
		}else{
			outState.putInt("LRESINDEX", -1);
		}
		// outState.putString("TEXT", (String) text.getText());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// cuando "termina de girar" restablesco todo
		super.onRestoreInstanceState(savedInstanceState);
		
		indexAux = savedInstanceState.getInt("LRESINDEX");
		if (indexAux != -1) {
			mostrarPedidoPrecios(Sistema.getInstance().getHistorial()
					.get(indexAux));
		}
	}

}
