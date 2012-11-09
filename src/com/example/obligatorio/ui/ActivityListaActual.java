package com.example.obligatorio.ui;

import java.util.List;

import com.example.obligatorio.adapters.ProductosAdaptadorCantidad;
import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.servicio.ListaPedido;
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
		TextView valor = (TextView) vwParentRow.getChildAt(3);
		int suma = Integer.parseInt(valor.getText().toString()) + 1;
		valor.setText(suma + "");

		ProductoCantidad pro = (ProductoCantidad) valor.getTag();
		pro.setCantidad(suma);

		// http://androidforbeginners.blogspot.com/2010/03/clicking-buttons-in-listview-row.html

		// TextView child = (TextView)vwParentRow.getChildAt(2);
		// int c = Color.BLUE;

		// vwParentRow.setBackgroundColor(c);
		// vwParentRow.refreshDrawableState();

	}

	public void Resta(View v) {
		LinearLayout vwParentRow = (LinearLayout) v.getParent();
		TextView valor = (TextView) vwParentRow.getChildAt(3);
		int resta = Integer.parseInt(valor.getText().toString());
		if (resta > 0) {
			resta--;
		}
		valor.setText(resta + "");

		ProductoCantidad pro = (ProductoCantidad) valor.getTag();

		if (resta == 0) {
			pro.getProducto().setEnListaActual(false);
			Sistema.getInstance().getListaPedActual()
					.eliminarProducto(pro.getProducto());
			Toast.makeText(this, "Quitado", Toast.LENGTH_SHORT).show();
//			Animation animation = AnimationUtils.loadAnimation(
//					ActivityListaActual.this, android.R.anim.fade_in);
//			animation.setDuration(2500);
//			vwParentRow.startAnimation(animation);
			adaptador.notifyDataSetChanged();
		} else {
			pro.setCantidad(resta);
		}
	}

}