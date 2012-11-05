package com.example.obligatorio.ui;

import java.util.ArrayList;
import java.util.List;

import com.example.obligatorio.adapters.ProductosAdaptadorCantidad;
import com.example.obligatorio.dominio.Producto;
import com.example.obligatorio.sistema.Sistema;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.R.integer;
import android.app.Activity;

public class ActivityListaActual extends Activity {

	// private ArrayList<Producto> productosSeleccionados;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actual);

		List<Producto> productos = Sistema.getInstance().getListaPedActual()
				.getProds();
		// productos.add(new Producto("A", "Marca", "Especificacion", 2));
		// productos.add(new Producto("B", "Marca", "Especificacion", 1));
		// productos.add(new Producto("C", "Marca", "Especificacion", 5));
		// productos.add(new Producto("D", "Marca", "Especificacion", 3));

		ProductosAdaptadorCantidad adaptador = new ProductosAdaptadorCantidad(
				this, productos);

		ListView lstOpciones = (ListView) findViewById(R.id.listaPorCantidad);

		lstOpciones.setAdapter(adaptador);
		
		//////lstOpcioneslstOpcioneslstOpcioneslstOpcioneslstOpcioneslstOpciones
	}

	public void Suma(View v) {
		// LayoutInflater inflater = this.getLayoutInflater();
		// v = inflater.inflate(R.layout.activity_actual_items,
		// null);
		//
		// TextView valor= (TextView) v.findViewById(R.id.tvCantidad);
		TextView valor = (TextView) findViewById(R.id.tvCantidad);
		// Button btn = (Button) findViewById(v.getId());
		int suma = Integer.parseInt(valor.getText().toString()) + 1;
		valor.setText(suma + "");
	}

	public void Resta(View v) {
		TextView valor = (TextView) findViewById(R.id.tvCantidad);
		// Button btn = (Button) findViewById(v.getId());
		int resta = Integer.parseInt(valor.getText().toString());
		if (resta > 0) {
			resta--;
		}
		valor.setText(resta + "");
	}

}