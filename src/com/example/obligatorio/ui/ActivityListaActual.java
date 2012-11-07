package com.example.obligatorio.ui;

import java.util.List;

import com.example.obligatorio.adapters.ProductosAdaptadorCantidad;
import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.servicio.ListaPedido.ProductoCantidad;
import com.example.obligatorio.sistema.Sistema;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.R.integer;
import android.app.Activity;

public class ActivityListaActual extends Activity {

	// private ArrayList<Producto> productosSeleccionados;
	private ProductosAdaptadorCantidad adaptador;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actual);

		adaptador = new ProductosAdaptadorCantidad(this, Sistema.getInstance()
				.getListaPedActual().getProductos());

		ListView lstOpciones = (ListView) findViewById(R.id.listaPorCantidad);

		lstOpciones.setAdapter(adaptador);

	}

	public void Suma(View v) {
		LinearLayout vwParentRow = (LinearLayout) v.getParent();
		TextView valor = (TextView) vwParentRow.getChildAt(2);
		int suma = Integer.parseInt(valor.getText().toString()) + 1;
		valor.setText(suma + "");
		// http://androidforbeginners.blogspot.com/2010/03/clicking-buttons-in-listview-row.html

		// TextView child = (TextView)vwParentRow.getChildAt(2);
		// int c = Color.BLUE;

		// vwParentRow.setBackgroundColor(c);
		// vwParentRow.refreshDrawableState();
	}

	public void Resta(View v) {
		LinearLayout vwParentRow = (LinearLayout) v.getParent();
		TextView valor = (TextView) vwParentRow.getChildAt(2);
		int resta = Integer.parseInt(valor.getText().toString());
		if (resta > 0) {
			resta--;
		}
		valor.setText(resta + "");
		if (resta == 0) {
			// forma chancha agregar el id en la activity lista actual y de ahi
			// buscarlo en sistema y despues borrarlo. =/
			Toast.makeText(
					this,
					"Quitado--Aun no puedo obtener el item seleccionado por lo que no lo estoy borrando...",
					Toast.LENGTH_SHORT).show();
			Animation animation = AnimationUtils.loadAnimation(
					ActivityListaActual.this, android.R.anim.fade_in);
			animation.setDuration(2500);
			vwParentRow.startAnimation(animation);
			vwParentRow.removeAllViews();
		}
	}

}